package fact.it.ratingservice.controller;

import fact.it.ratingservice.dto.RatingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {
    private final fact.it.ratingservice.service.RatingService ratingService;
}