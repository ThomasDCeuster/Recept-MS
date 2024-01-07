package fact.it.ratingservice.repository;

import fact.it.ratingservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, String> {
    List<Rating> findByNameIn(Collection<String> names);
}