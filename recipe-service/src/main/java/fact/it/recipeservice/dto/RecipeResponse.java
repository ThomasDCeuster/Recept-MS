package fact.it.recipeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponse {
    private String recipeNumber;
    private List<RecipeLineItemDto> recipeLineItemsList;
}
