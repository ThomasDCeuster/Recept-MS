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
        ingredientRequest.setAmount(1.0);

        // Act
        ingredientService.createIngredient(ingredientRequest);

        // Assert
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }

//    @Test
//    public void testGetAllProducts() {
//        // Arrange
//        Product product = new Product();
//        product.setId("1");
//        product.setSkuCode("SKU123");
//        product.setName("Test Product");
//        product.setDescription("Test Description");
//        product.setPrice(BigDecimal.valueOf(100));
//
//        when(productRepository.findAll()).thenReturn(Arrays.asList(product));
//
//        // Act
//        List<ProductResponse> products = productService.getAllProducts();
//
//        // Assert
//        assertEquals(1, products.size());
//        assertEquals("SKU123", products.get(0).getSkuCode());
//        assertEquals("Test Product", products.get(0).getName());
//        assertEquals("Test Description", products.get(0).getDescription());
//        assertEquals(BigDecimal.valueOf(100), products.get(0).getPrice());
//
//        verify(productRepository, times(1)).findAll();
//    }
//
//    @Test
//    public void testGetAllProductsBySkuCode() {
//        // Arrange
//        Product product = new Product();
//        product.setId("1");
//        product.setSkuCode("SKU123");
//        product.setName("Test Product");
//        product.setDescription("Test Description");
//        product.setPrice(BigDecimal.valueOf(100));
//
//        when(productRepository.findBySkuCodeIn(Arrays.asList("SKU123"))).thenReturn(Arrays.asList(product));
//
//        // Act
//        List<ProductResponse> products = productService.getAllProductsBySkuCode(Arrays.asList("SKU123"));
//
//        // Assert
//        assertEquals(1, products.size());
//        assertEquals("1", products.get(0).getId());
//        assertEquals("SKU123", products.get(0).getSkuCode());
//        assertEquals("Test Product", products.get(0).getName());
//        assertEquals("Test Description", products.get(0).getDescription());
//        assertEquals(BigDecimal.valueOf(100), products.get(0).getPrice());
//
//        verify(productRepository, times(1)).findBySkuCodeIn(Arrays.asList(product.getSkuCode()));
//    }
}