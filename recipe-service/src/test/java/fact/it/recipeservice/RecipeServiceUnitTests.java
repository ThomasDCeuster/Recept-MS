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
        RecipeRequest recipeRequest = new RecipeRequest();
        recipeRequest.setName("Test Recipe");
        recipeRequest.setRecipeNumber("1");
        RecipeLineItemDto recipeLineItemRequest1 = new RecipeLineItemDto();
        recipeLineItemRequest1.setName("Test Ingredient 1");
        recipeLineItemRequest1.setQuantity(2.0);
        recipeLineItemRequest1.setUnit("kg");
        recipeRequest.setRecipeLineItemsDtoList(Arrays.asList(recipeLineItemRequest1));


        // Act
        recipeService.createRecipe(recipeRequest);

        // Assert
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

        when(recipeRepository.findByName("Test Recipe")).thenReturn(Arrays.asList(recipe));

        // Act
        List<RecipeResponse> recipes = recipeService.getRecipeByName("Test Recipe");

        // Assert
        assertEquals(1, recipes.size());
        assertEquals("Test Recipe", recipes.get(0).getName());
        assertEquals("1", recipes.get(0).getRecipeNumber());

        verify(recipeRepository, times(1)).findByName(recipe.getName());
    }
}