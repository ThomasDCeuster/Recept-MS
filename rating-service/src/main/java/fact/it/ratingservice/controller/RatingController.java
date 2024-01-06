package fact.it.ratingservice.controller;

import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController{

    private final RatingService ratingService;

    // http://localhost:8082/api/inventory?skuCode=tube6in&skuCode=beam10ft
   /* @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RatingResponse> getRatingById(@RequestParam List<String> id) {
        return ratingService.isInStock(id);
    }*/
}