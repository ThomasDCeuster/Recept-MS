package fact.it.ratingservice.repository;

import fact.it.ratingservice.model.Rating;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByIdIn(List<String> id);
    List<Rating> findByNameIn(List<String> name);
}





