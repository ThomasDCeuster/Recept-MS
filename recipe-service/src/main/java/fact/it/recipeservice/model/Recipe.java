package fact.it.recipeservice.model;

import fact.it.ingredientservice.model.Ingredient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipeNumber;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RecipeLineItem> recipeLineItemsList;
}
