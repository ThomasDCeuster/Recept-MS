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
//
//    @Test
//    public void testGetAllOrders() {
//        // Arrange
//        OrderLineItem orderLineItem1 = new OrderLineItem(1L, "sku1", new BigDecimal("10.00"), 2);
//        OrderLineItem orderLineItem2 = new OrderLineItem(2L, "sku2", new BigDecimal("20.00"), 3);
//
//        Order order1 = new Order(1L, "order1", Arrays.asList(orderLineItem1, orderLineItem2));
//
//        OrderLineItem orderLineItem3 = new OrderLineItem(3L, "sku3", new BigDecimal("30.00"), 4);
//        OrderLineItem orderLineItem4 = new OrderLineItem(4L, "sku4", new BigDecimal("40.00"), 5);
//
//        Order order2 = new Order(2L, "order2", Arrays.asList(orderLineItem3, orderLineItem4));
//
//        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
//
//        // Act
//        List<OrderResponse> result = orderService.getAllOrders();
//
//        // Assert
//        assertEquals(2, result.size());
//
//        verify(orderRepository, times(1)).findAll();
//    }
}