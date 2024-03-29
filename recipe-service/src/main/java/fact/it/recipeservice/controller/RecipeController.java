package fact.it.recipeservice.controller;

import fact.it.recipeservice.dto.RecipeRequest;
import fact.it.recipeservice.dto.RecipeResponse;
import fact.it.recipeservice.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String createRecipe(@RequestBody RecipeRequest recipeRequest) {
        boolean result = recipeService.createRecipe(recipeRequest);
        return (result ? "Recipe created successfully" : "Recipe creation failed");
    }

    @CrossOrigin(origins = "https://659b0868480483cbb0fabc9e--magnificent-caramel-73ce1b.netlify.app/")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<RecipeResponse> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateRecipe(@PathVariable Long id, @RequestBody RecipeRequest updatedRecipe) {
        boolean result = recipeService.updateRecipe(id, updatedRecipe);
        return (result ? "Recipe updated successfully" : "Recipe update failed");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteRecipe(@PathVariable Long id) {
        try {
            recipeService.deleteRecipe(id);
            return "Recipe deleted successfully";
        } catch (Exception e) {
            return "Recipe deletion failed: " + e.getMessage();
        }
    }
}
