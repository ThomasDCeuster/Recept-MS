package fact.it.recipeservice.controller;

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

//    @PostMapping
//    @ResponseStatus(HttpStatus.OK)
//    public String placeOrder(@RequestBody OrderRequest orderRequest) {
//        boolean result = orderService.placeOrder(orderRequest);
//        return (result ? "Order placed successfully" : "Order placement failed");
//    }

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<RecipeResponse> getAllIngredients() {
//        return recipeService.getAllRecipes();
//    }
}
