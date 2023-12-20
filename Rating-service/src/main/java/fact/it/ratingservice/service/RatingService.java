package fact.it.ratingservice.service;
import fact.it.ratingservice.repository.RatingRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final fact.it.ratingservice.repository.RatingRepository ratingRepository;

    @PostConstruct
    public void loadData() {
    }
}
