package fact.it.ingredientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientRequest {
    private String id;
    private String skuCode;
    private String name;
    private String description;
    private String measurementUnit;
    private BigDecimal price;
    private String amount;
}
