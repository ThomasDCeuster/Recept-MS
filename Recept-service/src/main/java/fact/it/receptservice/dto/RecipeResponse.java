package fact.it.receptservice.dto;

import fact.it.receptservice.model.RecipeIngredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponse {
    private String recipeNumber;
    private List<RecipeIngredient> recipeIngredientsList;
}
