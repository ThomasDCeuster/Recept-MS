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
                        mapToRecipeLineItemsDto(recipe.getRecipeLineItemsList())
                ))
                .collect(Collectors.toList());
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
}
