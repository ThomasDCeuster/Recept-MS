package fact.it.recipeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeLineItemDto {
    private Long id;
    private String name;
    private String unit;
    private Double quantity;
}
