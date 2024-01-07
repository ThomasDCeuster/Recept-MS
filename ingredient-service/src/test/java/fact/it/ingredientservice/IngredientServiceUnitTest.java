package fact.it.ingredientservice;

import fact.it.ingredientservice.dto.IngredientRequest;
import fact.it.ingredientservice.dto.IngredientResponse;
import fact.it.ingredientservice.model.Ingredient;
import fact.it.ingredientservice.repository.IngredientRepository;
import fact.it.ingredientservice.service.IngredientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceUnitTest {

    @InjectMocks
    private IngredientService ingredientService;

    @Mock
    private IngredientRepository ingredientRepository;

    @Test
    public void testCreateIngredient() {
        // Arrange
        IngredientRequest ingredientRequest = new IngredientRequest();
        ingredientRequest.setName("Test Ingredient");
        ingredientRequest.setDescription("Test Description");
        ingredientRequest.setMeasurementUnit("liter");
        ingredientRequest.setPrice(BigDecimal.valueOf(5));
        ingredientRequest.setAmount(1);

        // Act
        ingredientService.createIngredient(ingredientRequest);

        // Assert
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }

    @Test
    public void testGetAllIngredients() {
        // Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setId("1");
        ingredient.setName("Test Ingredient");
        ingredient.setDescription("Test Description");
        ingredient.setMeasurementUnit("liter");
        ingredient.setPrice(BigDecimal.valueOf(5));
        ingredient.setAmount(1);

        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(ingredient));

        // Act
        List<IngredientResponse> ingredients = ingredientService.getAllIngredients();

        // Assert
        assertEquals(1, ingredients.size());
        assertEquals("Test Ingredient", ingredients.get(0).getName());
        assertEquals("Test Description", ingredients.get(0).getDescription());
        assertEquals("liter", ingredients.get(0).getMeasurementUnit());
        assertEquals(BigDecimal.valueOf(5), ingredients.get(0).getPrice());
        assertEquals(1, ingredients.get(0).getAmount());

        verify(ingredientRepository, times(1)).findAll();
    }

    @Test
    public void testGetIngredientsById() {
        // Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setId("1");
        ingredient.setName("Test Ingredient");
        ingredient.setDescription("Test Description");
        ingredient.setMeasurementUnit("liter");
        ingredient.setPrice(BigDecimal.valueOf(5));
        ingredient.setAmount(1);

        when(ingredientRepository.findByIdIn("1")).thenReturn(Arrays.asList(ingredient));

        // Act
        List<IngredientResponse> ingredients = ingredientService.getIngredientById("1");

        // Assert
        assertEquals(1, ingredients.size());
        assertEquals("Test Ingredient", ingredients.get(0).getName());
        assertEquals("Test Description", ingredients.get(0).getDescription());
        assertEquals("liter", ingredients.get(0).getMeasurementUnit());
        assertEquals(BigDecimal.valueOf(5), ingredients.get(0).getPrice());
        assertEquals(1, ingredients.get(0).getAmount());

        verify(ingredientRepository, times(1)).findByIdIn(ingredient.getId());
    }
}