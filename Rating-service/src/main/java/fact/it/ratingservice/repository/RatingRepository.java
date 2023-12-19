package fact.it.ratingservice.repository;

import fact.it.ratingservice.model.RatingItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface RatingRepository extends JpaRepository<RatingItem, Long> {
}
