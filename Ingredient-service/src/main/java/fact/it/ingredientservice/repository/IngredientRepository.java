package fact.it.ingredientservice.repository;

import fact.it.ingredientservice.model.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {
    List<Ingredient> findBySkuCodeIn(List<String> skuCode);
}
