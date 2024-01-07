package fact.it.ingredientservice.controller;

import fact.it.ingredientservice.dto.*;
import fact.it.ingredientservice.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createIngredient(@RequestBody IngredientRequest ingredientRequest) {
        {
            ingredientService.createIngredient(ingredientRequest);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<IngredientResponse> getIngredientById(@RequestParam String id) {
        return ingredientService.getIngredientById(id);
    }
    @CrossOrigin(origins = "https://659b0868480483cbb0fabc9e--magnificent-caramel-73ce1b.netlify.app/")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<IngredientResponse> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteIngredient(@PathVariable String id) {
        try {
            ingredientService.deleteIngredient(id);
            return "Ingredient deleted successfully";
        } catch (Exception e) {
            return "Ingredient deletion failed: " + e.getMessage();
        }
    }
}
