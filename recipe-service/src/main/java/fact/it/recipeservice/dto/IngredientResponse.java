package fact.it.recipeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientResponse {
    private String id;
    private String name;
    private String description;
    private String measurementUnit;
    private BigDecimal price;
    private Double amount;
}