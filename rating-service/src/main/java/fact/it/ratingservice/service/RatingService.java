package fact.it.ratingservice.service;

import fact.it.ratingservice.dto.*;
import fact.it.ratingservice.repository.RatingRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

//    @PostConstruct
//    public void loadData() {
//        if(ratingRepository.count() <= 0){
//            Rating rating = new Rating();
//            rating.setName("tube6in");
//            stockItem.setQuantity(100);
//
//            StockItem stockItem1 = new StockItem();
//            stockItem1.setSkuCode("beam10ft");
//            stockItem1.setQuantity(0);
//
//            inventoryRepository.save(stockItem);
//            inventoryRepository.save(stockItem1);
//        }
//    }

    public List<RatingResponse> getRatingByName(String name) {
        List<Rating> ratings = ratingRepository.findByNameIn(name);

        return ratings.stream().map(this::mapToRatingResponse).toList();
    }

    private RatingResponse mapToRatingResponse(Rating rating) {
        return RatingResponse.builder()
                .name(rating.getName())
                .rating(rating.getRating())
                .build();
    }
}
