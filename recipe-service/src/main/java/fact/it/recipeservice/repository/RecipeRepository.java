package fact.it.recipeservice.repository;

import fact.it.recipeservice.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, String> {
    List<Recipe> findByNameIn(Collection<String> name);
}
