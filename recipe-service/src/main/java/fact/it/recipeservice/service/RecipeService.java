package fact.it.recipeservice.service;

import fact.it.recipeservice.dto.*;
import fact.it.recipeservice.model.Recipe;
import fact.it.recipeservice.model.RecipeLineItem;
import fact.it.recipeservice.repository.RecipeRepository;
import jakarta.annotation.PostConstruct;
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


    @PostConstruct
    public void loadData() {
        if(recipeRepository.count() <= 0){
            Recipe recipe1 = new Recipe();
            recipe1.setRecipeNumber(UUID.randomUUID().toString());
            recipe1.setName("Spaghetti Bolognese");
            RecipeLineItem recipeLineItem1 = new RecipeLineItem();
            recipeLineItem1.setName("Spaghetti pasta");
            recipeLineItem1.setQuantity(200.0);
            recipeLineItem1.setUnit("g");
            RecipeLineItem recipeLineItem2 = new RecipeLineItem();
            recipeLineItem2.setName("Tomato sauce");
            recipeLineItem2.setQuantity(100.0);
            recipeLineItem2.setUnit("g");
            RecipeLineItem recipeLineItem3 = new RecipeLineItem();
            recipeLineItem3.setName("Minced meat");
            recipeLineItem3.setQuantity(100.0);
            recipeLineItem3.setUnit("g");
            recipe1.setRecipeLineItemsList(List.of(recipeLineItem1, recipeLineItem2, recipeLineItem3));
            recipeRepository.save(recipe1);

            Recipe recipe2 = new Recipe();
            recipe2.setRecipeNumber(UUID.randomUUID().toString());
            recipe2.setName("Spaghetti Carbonara");
            RecipeLineItem recipeLineItem4 = new RecipeLineItem();
            recipeLineItem4.setName("Spaghetti pasta");
            recipeLineItem4.setQuantity(200.0);
            recipeLineItem4.setUnit("g");
            RecipeLineItem recipeLineItem5 = new RecipeLineItem();
            recipeLineItem5.setName("Cream");
            recipeLineItem5.setQuantity(100.0);
            recipeLineItem5.setUnit("g");
            RecipeLineItem recipeLineItem6 = new RecipeLineItem();
            recipeLineItem6.setName("Bacon");
            recipeLineItem6.setQuantity(100.0);
            recipeLineItem6.setUnit("g");
            recipe2.setRecipeLineItemsList(List.of(recipeLineItem4, recipeLineItem5, recipeLineItem6));
            recipeRepository.save(recipe2);
        }
    }

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
        List<Recipe> recipes = recipeRepository.findByNameIn(List.of(name));

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
}
