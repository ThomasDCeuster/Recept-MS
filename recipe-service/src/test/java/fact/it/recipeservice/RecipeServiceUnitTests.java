package fact.it.recipeservice;

import fact.it.recipeservice.dto.*;
import fact.it.recipeservice.model.Recipe;
import fact.it.recipeservice.model.RecipeLineItem;
import fact.it.recipeservice.repository.RecipeRepository;
import fact.it.recipeservice.service.RecipeService;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceUnitTests {

    @InjectMocks
    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(recipeService, "ingredientServiceBaseUrl", "http://localhost:8080");
        ReflectionTestUtils.setField(recipeService, "ratingServiceBaseUrl", "http://localhost:8082");
    }

    @Test
    public void testCreateRecipe_Success() {
        // Arrange
        String recipeNumber = "1";
        String name = "Test Name";

        RecipeRequest recipeRequest = new RecipeRequest();
        // populate recipeRequest with test data
        RecipeLineItemDto recipeLineItemDto = new RecipeLineItemDto();
        recipeLineItemDto.setId(1L);
        recipeLineItemDto.setUnit("liter");
        recipeLineItemDto.setName("Test Name");
        recipeLineItemDto.setQuantity(1.0);
        recipeRequest.setRecipeLineItemsDtoList(Arrays.asList(recipeLineItemDto));

        RatingResponse ratingResponse = new RatingResponse();
        // populate recipeResponse with test data
        ratingResponse.setName("Test Rating");
        ratingResponse.setRating(3.5);

        IngredientResponse ingredientResponse = new IngredientResponse();
        // populate productResponse with test data
        ingredientResponse.setName("Test Ingredient");
        ingredientResponse.setDescription("Test Description");
        ingredientResponse.setPrice(BigDecimal.valueOf(5));
        ingredientResponse.setAmount(1.0);
        ingredientResponse.setMeasurementUnit("liter");

        Recipe recipe = new Recipe();
        recipe.setRecipeNumber(recipeNumber);
        RecipeLineItem recipeLineItem = new RecipeLineItem();
        recipeLineItem.setId(1L);
        recipeLineItem.setName("Test Name");
        recipeLineItem.setUnit("liter");
        recipeLineItem.setQuantity(1.0);
        recipe.setRecipeLineItemsList(Arrays.asList(recipeLineItem));

        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(),  any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(RatingResponse[].class)).thenReturn(Mono.just(new RatingResponse[]{ratingResponse}));
        when(responseSpec.bodyToMono(IngredientResponse[].class)).thenReturn(Mono.just(new IngredientResponse[]{ingredientResponse}));

        // Act
        boolean result = recipeService.createRecipe(recipeRequest);

        // Assert
        assertTrue(result);

        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testGetAllRecipes() {
        // Arrange
        RecipeLineItem recipeLineItem1 = new RecipeLineItem(1L, "Item 1", "kg", 2.0);
        RecipeLineItem recipeLineItem2 = new RecipeLineItem(2L, "Item 2", "ml", 500.0);

        Recipe recipe1 = new Recipe(1L, "1", "order1", Arrays.asList(recipeLineItem1, recipeLineItem2));

        RecipeLineItem recipeLineItem3 = new RecipeLineItem(1L, "Item 3", "g", 400.0);
        RecipeLineItem recipeLineItem4 = new RecipeLineItem(1L, "Item 4", "l", 2.0);

        Recipe recipe2 = new Recipe(2L, "2", "order2", Arrays.asList(recipeLineItem3, recipeLineItem4));

        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2));

        // Act
        List<RecipeResponse> result = recipeService.getAllRecipes();

        // Assert
        assertEquals(2, result.size());

        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void testGetRecipesByName() {
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Test Recipe");
        recipe.setRecipeNumber("1");
        RecipeLineItem recipeLineItem1 = new RecipeLineItem(1L, "Item 1", "kg", 2.0);
        recipe.setRecipeLineItemsList(Arrays.asList(recipeLineItem1));

        when(recipeRepository.findByNameIn(List.of("Test Recipe"))).thenReturn(Arrays.asList(recipe));

        // Act
        List<RecipeResponse> recipes = recipeService.getRecipeByName("Test Recipe");

        // Assert
        assertEquals(1, recipes.size());
        assertEquals("Test Recipe", recipes.get(0).getName());
        assertEquals("1", recipes.get(0).getRecipeNumber());

        verify(recipeRepository, times(1)).findByNameIn(List.of(recipe.getName()));
    }
}