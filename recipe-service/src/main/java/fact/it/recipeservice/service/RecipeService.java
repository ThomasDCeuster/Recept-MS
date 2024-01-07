package fact.it.recipeservice.service;

import fact.it.recipeservice.dto.*;
import fact.it.recipeservice.model.Recipe;
import fact.it.recipeservice.model.RecipeLineItem;
import fact.it.recipeservice.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final WebClient webClient;

    @Value("${ingredientservice.baseurl}")
    private String ingredientServiceBaseUrl;

    @Value("${ratingservice.baseurl}")
    private String ratingServiceBaseUrl;

    @Value("${userservice.baseurl}")
    private String userServiceBaseUrl;

    public boolean createRecipe(RecipeRequest recipeRequest) {
        Recipe recipe = new Recipe();
        recipe.setRecipeNumber(UUID.randomUUID().toString());

        List<RecipeLineItem> recipeLineItems = recipeRequest.getRecipeLineItemsDtoList()
                .stream()
                .map(this::mapToRecipeLineItem)
                .toList();

        recipe.setRecipeLineItemsList(recipeLineItems);

        List<String> names = recipe.getRecipeLineItemsList().stream()
                .map(RecipeLineItem::getName)
                .toList();

        RatingResponse[] ratingResponseArray = webClient.get()
                .uri("http://" + ratingServiceBaseUrl + "/api/rating",
                        uriBuilder -> uriBuilder.queryParam("name", names).build())
                .retrieve()
                .bodyToMono(RatingResponse[].class)
                .block();


        IngredientResponse[] ingredientResponseArray = webClient.get()
                .uri("http://" + ingredientServiceBaseUrl + "/api/ingredient",
                        uriBuilder -> uriBuilder.queryParam("name", names).build())
                .retrieve()
                .bodyToMono(IngredientResponse[].class)
                .block();

        recipe.getRecipeLineItemsList().stream()
                .map(recipeItem -> {
                    IngredientResponse ingredient = Arrays.stream(ingredientResponseArray)
                            .filter(i -> i.getName().equals(recipeItem.getName()))
                            .findFirst()
                            .orElse(null);
                    if (ingredient != null) {
                        recipeItem.setQuantity(ingredient.getAmount());
                        recipeItem.setUnit(ingredient.getMeasurementUnit());
                    }
                    return recipeItem;
                })
                .collect(Collectors.toList());

        recipeRepository.save(recipe);
        return true;
    }

    public List<RecipeResponse> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();

        return recipes.stream()
                .map(recipe -> new RecipeResponse(
                        recipe.getRecipeNumber(),
                        recipe.getName(),
                        mapToRecipeLineItemsDto(recipe.getRecipeLineItemsList())
                ))
                .collect(Collectors.toList());
    }

    public List<RecipeResponse> getRecipeByName(String name) {
        List<Recipe> recipes = recipeRepository.findByNameIn(name);

        return recipes.stream().map(this::mapToRecipeResponse).toList();
    }

    private RecipeResponse mapToRecipeResponse(Recipe recipe) {
        return RecipeResponse.builder()
                .recipeNumber(recipe.getRecipeNumber())
                .name(recipe.getName())
                .recipeLineItemsList(mapToRecipeLineItemsDto(recipe.getRecipeLineItemsList()))
                .build();
    }

    private RecipeLineItem mapToRecipeLineItem(RecipeLineItemDto recipeLineItemDto) {
        RecipeLineItem recipeLineItem = new RecipeLineItem();
        recipeLineItem.setName(recipeLineItemDto.getName());
        recipeLineItem.setQuantity(recipeLineItemDto.getQuantity());
        recipeLineItem.setUnit(recipeLineItemDto.getUnit());
        return recipeLineItem;
    }

    private List<RecipeLineItemDto> mapToRecipeLineItemsDto(List<RecipeLineItem> recipeLineItems) {
        return recipeLineItems.stream()
                .map(orderLineItem -> new RecipeLineItemDto(
                        orderLineItem.getId(),
                        orderLineItem.getName(),
                        orderLineItem.getUnit(),
                        orderLineItem.getQuantity()
                ))
                .collect(Collectors.toList());
    }

    public void deleteRecipe(Long id) {
        recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + id));

        recipeRepository.deleteById(id);
    }

    public boolean updateRecipe(Long id, RecipeRequest updatedRecipe) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + id));

        // Update existing recipe with new data
        existingRecipe.setName(updatedRecipe.getName());
        existingRecipe.setRecipeNumber(updatedRecipe.getRecipeNumber());

        // Update recipe line items if needed (example: assuming they are a list of RecipeLineItemDto)
        List<RecipeLineItem> updatedRecipeLineItems = updatedRecipe.getRecipeLineItemsDtoList()
                .stream()
                .map(this::mapToRecipeLineItem)
                .toList();
        existingRecipe.setRecipeLineItemsList(updatedRecipeLineItems);

        // Save the updated recipe
        recipeRepository.save(existingRecipe);

        return true;
    }
}
