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


    @PostConstruct
    public void loadData() {
        if(ingredientRepository.count() <= 0){
            Ingredient ingredient = Ingredient.builder()
                    .id("0")
                    .name("Tomato")
                    .description("A red vegetable")
                    .measurementUnit("kg")
                    .price(new BigDecimal(1.5))
                    .amount(1)
                    .build();

            Ingredient ingredient2 = Ingredient.builder()
                    .id("1")
                    .name("Cheese")
                    .description("A yellow dairy product")
                    .measurementUnit("kg")
                    .price(new BigDecimal(2.5))
                    .build();

            Ingredient ingredient3 = Ingredient.builder()
                    .id("2")
                    .name("Ham")
                    .description("A pink meat product")
                    .measurementUnit("kg")
                    .price(new BigDecimal(3.5))
                    .amount(1)
                    .build();

            Ingredient ingredient4 = Ingredient.builder()
                    .id("3")
                    .name("Mushrooms")
                    .description("A brown vegetable")
                    .measurementUnit("kg")
                    .price(new BigDecimal(1.5))
                    .amount(1)
                    .build();

            Ingredient ingredient5 = Ingredient.builder()
                    .id("4")
                    .name("Bread")
                    .description("A brown bread")
                    .measurementUnit("kg")
                    .price(new BigDecimal(1.5))
                    .amount(1)
                    .build();

            Ingredient ingredient6 = Ingredient.builder()
                    .id("5")
                    .name("Bacon")
                    .description("A pink meat product")
                    .measurementUnit("kg")
                    .price(new BigDecimal(1.5))
                    .amount(1)
                    .build();

            Ingredient ingredient7 = Ingredient.builder()
                    .id("6")
                    .name("Lettuce")
                    .description("A green vegetable")
                    .measurementUnit("kg")
                    .price(new BigDecimal(1.5))
                    .amount(1)
                    .build();

            ingredientRepository.save(ingredient);
            ingredientRepository.save(ingredient2);
            ingredientRepository.save(ingredient3);
            ingredientRepository.save(ingredient4);
            ingredientRepository.save(ingredient5);
            ingredientRepository.save(ingredient6);
            ingredientRepository.save(ingredient7);

        }
    }

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

    public void deleteIngredient(String id) {
        ingredientRepository.deleteById(id);
    }

}
