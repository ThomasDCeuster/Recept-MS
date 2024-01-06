package fact.it.recipeservice.repository;

import fact.it.recipeservice.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
