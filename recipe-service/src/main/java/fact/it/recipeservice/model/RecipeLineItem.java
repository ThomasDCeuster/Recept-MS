package fact.it.recipeservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "recipelineitem")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String unit;
    private Double quantity;
}
