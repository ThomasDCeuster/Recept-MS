package fact.it.recipeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeResponse {
    private String recipeNumber;
    private String name;
    private List<RecipeLineItemDto> recipeLineItemsList;
}
