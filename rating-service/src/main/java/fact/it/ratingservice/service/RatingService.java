package fact.it.ratingservice.service;

import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.model.Rating;
import fact.it.ratingservice.repository.RatingRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    @PostConstruct
    public void loadData() {
        if(ratingRepository.count() <= 0){
            Rating rating1 = new Rating();
            rating1.setName("Spaghetti");
            rating1.setRating(4.0);

            Rating rating2 = new Rating();
            rating2.setName("Chicken Broccoli");
            rating2.setRating(3.5);

            ratingRepository.save(rating1);
            ratingRepository.save(rating2);
        }
    }

    public List<RatingResponse> getRatingByName(String name) {
        List<Rating> ratings = ratingRepository.findByNameIn(Arrays.asList(name));

        return ratings.stream().map(this::mapToRatingResponse).toList();
    }

    private RatingResponse mapToRatingResponse(Rating rating) {
        return RatingResponse.builder()
                .name(rating.getName())
                .rating(rating.getRating())
                .build();
    }
}
