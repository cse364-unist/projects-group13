package com.example.cse364project.service;

import com.example.cse364project.domain.Rating;
import com.example.cse364project.exception.RatingNotFoundException;
import com.example.cse364project.repository.RatingRepository;
import com.example.cse364project.repository.MovieRatingRepository;
import com.example.cse364project.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.*;

public class RatingServiceTest {

    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

    private MovieRatingRepository movieratingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ratingService = new RatingService(ratingRepository, movieratingRepository);
    }

    @Test
    void testGetRatingById_ExistingId_ReturnsRating() {
        // Given
        String id = "1";
        Rating rating = new Rating("123", "456", 5, "2022-01-01");
        rating.setId(id);
        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));

        // When
        Rating result = ratingService.getRatingById(id);

        // Then
        assertEquals(id, result.getId());
    }

    @Test
    void testGetRatingById_NonExistingId_ThrowsRatingNotFoundException() {
        // Given
        String id = "1";
        when(ratingRepository.findById(id)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(RatingNotFoundException.class, () -> ratingService.getRatingById(id));
    }
     
    @Test
    void testGetRatingsByMovieId_ExistingMovieId_ReturnsRating() {
        //Given
        String MovieId = "123";

        List<Rating> rating_list = Arrays.asList(
            new Rating("123", "456", 5, "2022-01-01"),
            new Rating("789", "012", 4, "2022-02-01")
        );

        when(ratingRepository.findByMovieId(MovieId)).thenReturn(Optional.of(rating_list));

        // When
        List<Rating> result_list = ratingService.getRatingsByMovieId(MovieId);

        // Then
        assertEquals(rating_list, result_list);
    }
    
    @Test
    void testGetRatingsByMovieId_NonExistingMovieId_ThrowsRatingNotFoundException() {
        // Given
        String movieId = "123";
        when(ratingRepository.findByMovieId(movieId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(RatingNotFoundException.class, () -> ratingService.getRatingsByMovieId(movieId));
    }

    @Test
    void testGetRatingsByUserId_ExistingMovieId_ReturnsRating() {
        //Given
        String UserId = "456";

        List<Rating> rating_list = Arrays.asList(
            new Rating("123", "456", 5, "2022-01-01"),
            new Rating("789", "012", 4, "2022-02-01")
        );

        when(ratingRepository.findByUserId(UserId)).thenReturn(Optional.of(rating_list));

        // When
        List<Rating> result_list = ratingService.getRatingsByUserId(UserId);

        // Then
        assertEquals(rating_list, result_list);
    }
    
    @Test
    void testGetRatingsByUserId_NonExistingUserId_ThrowsRatingNotFoundException() {
        // Given
        String UserId = "456";
        when(ratingRepository.findByUserId(UserId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(RatingNotFoundException.class, () -> ratingService.getRatingsByUserId(UserId));
    }

    @Test
    void testGetRatingByMovieIdAndUserId_ExistingMovieId_ReturnsRating() {
        //Given
        String MovieId = "123";
        String UserId = "456";

        Rating rating = new Rating("123", "456", 5, "2022-01-01");
        when(ratingRepository.findByMovieIdAndUserId(MovieId, UserId)).thenReturn(Optional.of(rating));

        // When
        Rating result = ratingService.getRatingByMovieIdAndUserId(MovieId, UserId);

        // Then
        assertEquals(rating, result);
    }
    
    @Test
    void testGetRatingByMovieIdAndUserId_NonExistingMovieIdAndUserId_ThrowsRatingNotFoundException() {
        // Given
        String MovieId = "123";
        String UserId = "456";
        when(ratingRepository.findByMovieIdAndUserId(MovieId, UserId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(RatingNotFoundException.class, () -> ratingService.getRatingByMovieIdAndUserId(MovieId, UserId));
    }

    @Test
    void testAddRating_RatingExists_ReturnsUpdatedRating() {
        // Given
        Rating existingRating = new Rating("movieId", "userId", 5, "time");
        existingRating.setId("1");
        when(ratingRepository.existsById(existingRating.getId())).thenReturn(true);
        when(ratingRepository.save(existingRating)).thenReturn(existingRating);

        // When
        Rating result = ratingService.addRating(existingRating);

        // Then
        assertEquals(existingRating, result);
        verify(ratingRepository).existsById(existingRating.getId());
        verify(ratingRepository).save(existingRating);
    }

    @Test
    void testAddRating_RatingDoesNotExist_ReturnsNewRating() {
        // Given
        Rating newRating = new Rating("movieId", "userId", 5, "time");
        newRating.setId("1");        
        when(ratingRepository.existsById(newRating.getId())).thenReturn(false);
        when(ratingRepository.save(newRating)).thenReturn(newRating);

        // When
        Rating result = ratingService.addRating(newRating);

        // Then
        assertEquals(newRating, result);
        verify(ratingRepository).existsById(newRating.getId());
        verify(ratingRepository).save(newRating);
    }

    @Test
    void testUpdateRating_RatingExists_ReturnsUpdatedRating() {
        // Given
        Rating existingRating = new Rating("movieId", "userId", 5, "time");
        existingRating.setId("1");
        Rating updatedRating = new Rating("new_movieId", "new_userId", 4, "new_time");
        when(ratingRepository.save(existingRating)).thenReturn(updatedRating);

        // When
        Rating result = ratingService.updateRating("1", updatedRating);

        // Then
        assertEquals(updatedRating, result);
        assertEquals(updatedRating.getMovieId(), result.getMovieId());
        assertEquals(updatedRating.getUserId(), result.getUserId());
        assertEquals(updatedRating.getRate(), result.getRate());
        assertEquals(updatedRating.getTimestamp(), result.getTimestamp());
        verify(ratingRepository).findById("1");
        verify(ratingRepository).save(existingRating);
    }

    @Test
    void testUpdateRating_RatingDoesNotExist_ReturnsNewRating() {
        // Given
        Rating newRating = new Rating("movieId", "userId", 5, "time");
        newRating.setId("1");        
        when(ratingRepository.existsById(newRating.getId())).thenReturn(false);
        when(ratingRepository.save(newRating)).thenReturn(newRating);

        // When
        Rating result = ratingService.addRating(newRating);

        // Then
        assertEquals(newRating, result);
        verify(ratingRepository).existsById(newRating.getId());
        verify(ratingRepository).save(newRating);
    }

    @Test
    void testPatchRating_RatingExists_ReturnsUpdatedRating(){
        // Given
        Rating existingRating = new Rating("movieId", "userId", 5, "time");
        existingRating.setId("1");
        when(ratingRepository.existsById(existingRating.getId())).thenReturn(true);
        when(ratingRepository.save(existingRating)).thenReturn(existingRating);

        // When
        Rating result = ratingService.addRating(existingRating);

        // Then
        assertEquals(existingRating, result);
        //verify(ratingRepository, never()).findById(existingRating.getId());
        verify(ratingRepository).existsById(existingRating.getId());
        verify(ratingRepository).save(existingRating);
    }
   // Similarly, write tests for other methods like getRatingsByUserId and getRatingByMovieIdAndUserId
}
