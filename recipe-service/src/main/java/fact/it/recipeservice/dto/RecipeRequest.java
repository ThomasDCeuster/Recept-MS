package fact.it.recipeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequest {
    private String recipeNumber;
    private String name;
    private List<RecipeLineItemDto> recipeLineItemsDtoList;
}
