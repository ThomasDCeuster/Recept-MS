package fact.it.ratingservice;

import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.model.Rating;
import fact.it.ratingservice.repository.RatingRepository;
import fact.it.ratingservice.service.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceUnitTests {

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Test
    public void testGetRatingsByName() {
        // Arrange
        Rating rating = new Rating();
        rating.setId(1L);
        rating.setName("Test Rating");
        rating.setRating(4.5);

        when(ratingRepository.findByName("Test Rating")).thenReturn(Arrays.asList(rating));

        // Act
        List<RatingResponse> ratings = ratingService.getRatingByName("Test Rating");

        // Assert
        assertEquals(1, ratings.size());
        assertEquals("Test Rating", ratings.get(0).getName());
        assertEquals(4.5, ratings.get(0).getRating());

        verify(ratingRepository, times(1)).findByName(rating.getName());
    }
}