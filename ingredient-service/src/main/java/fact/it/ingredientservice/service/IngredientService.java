package fact.it.ingredientservice.service;

import fact.it.ingredientservice.dto.IngredientRequest;
import fact.it.ingredientservice.dto.IngredientResponse;
import fact.it.ingredientservice.model.Ingredient;
import fact.it.ingredientservice.repository.IngredientRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public void createIngredient(IngredientRequest ingredientRequest) {
        Ingredient ingredient = Ingredient.builder()
                .name(ingredientRequest.getName())
                .description(ingredientRequest.getDescription())
                .measurementUnit(ingredientRequest.getMeasurementUnit())
                .price(ingredientRequest.getPrice())
                .amount(ingredientRequest.getAmount())
                .build();

        ingredientRepository.save(ingredient);
    }

    public List<IngredientResponse> getIngredientById(String id) {
        List<Ingredient> ingredients = ingredientRepository.findByIdIn(id);

        return ingredients.stream().map(this::mapToIngredientResponse).toList();
    }

    private IngredientResponse mapToIngredientResponse(Ingredient ingredient) {
        return IngredientResponse.builder()
                .name(ingredient.getName())
                .description(ingredient.getDescription())
                .measurementUnit(ingredient.getMeasurementUnit())
                .price(ingredient.getPrice())
                .amount(ingredient.getAmount())
                .build();
    }

    public List<IngredientResponse> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();

        return ingredients.stream().map(this::mapToIngredientResponse).toList();
    }

}
