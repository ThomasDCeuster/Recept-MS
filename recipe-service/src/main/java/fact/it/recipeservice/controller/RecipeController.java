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

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<RecipeResponse> getAllRecipes() {
        return recipeService.getAllRecipes();
    }
}
