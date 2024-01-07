package fact.it.recipeservice.repository;

import fact.it.recipeservice.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByNameIn(String name);
}
