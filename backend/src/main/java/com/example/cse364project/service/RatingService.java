package com.example.cse364project.service;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.domain.Rating;
import com.example.cse364project.exception.RatingNotFoundException;
import com.example.cse364project.repository.MovieRatingRepository;
import com.example.cse364project.repository.RatingRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final MovieRatingRepository movieratingRepository;

    public RatingService(RatingRepository ratingRepository, MovieRatingRepository movieratingRepository) {
        this.ratingRepository = ratingRepository;
        this.movieratingRepository = movieratingRepository;
    }

    public Rating getRatingById(String id) {
        Optional<Rating> rating = ratingRepository.findById(id);
        return rating.orElseThrow(() -> new RatingNotFoundException("Cannot find any with id " + id + "."));
    }

    public List<Rating> getRatingsByMovieId(String movieId) {
        return ratingRepository.findByMovieId(movieId)
                .orElseThrow(() -> new RatingNotFoundException(movieId + " is not found in the document."));
    }

    public List<Rating> getRatingsByUserId(String userId) {
        return ratingRepository.findByUserId(userId)
                .orElseThrow(() -> new RatingNotFoundException(userId + " is not found in the document."));
    }

    public Rating getRatingByMovieIdAndUserId(String movieId, String userId) {
        return ratingRepository.findByMovieIdAndUserId(movieId, userId)
                .orElseThrow(() -> new RatingNotFoundException(
                        "The entry that has " + movieId + " & " + userId + " is not exist."));
    }

    public Rating addRating(Rating rating) {
        if (ratingRepository.existsById(rating.getId()))
            return updateRating(rating.getId(), rating);
        return ratingRepository.save(rating);
    }

    public Rating updateRating(String id, Rating newRating) {
        Rating updatedRating = ratingRepository.findById(id)
                .map(rating -> {
                    rating.setMovieId(newRating.getMovieId());
                    rating.setUserId(newRating.getUserId());
                    rating.setRate(newRating.getRate());
                    rating.setTimestamp(newRating.getTimestamp());
                    return ratingRepository.save(rating);
                })
                .orElseGet(() -> {
                    newRating.setId(id);
                    return ratingRepository.save(newRating);
                });
        return updatedRating;
    }

    public Rating patchRating(String id, Rating newRating) {
        Rating updatedRating = ratingRepository.findById(id)
                .map(rating -> {
                    if (newRating.getMovieId() != null) rating.setMovieId(newRating.getMovieId());
                    if (newRating.getUserId() != null) rating.setUserId(newRating.getUserId());
                    if (newRating.getRate() != 0) rating.setRate(newRating.getRate());
                    if (newRating.getTimestamp() != null) rating.setTimestamp(newRating.getTimestamp());
                    return ratingRepository.save(rating);
                })
                .orElseGet(() -> {
                    newRating.setId(id);
                    return ratingRepository.save(newRating);
                });
        return updatedRating;
    }

    public void deleteRating(String id) {
        ratingRepository.deleteById(id);
    }

    public List<Movie> getMoviesWithHighAverageRating(int rating) {
        List<Movie> highRatedMovies = movieratingRepository.findMoviesWithGTEAverageRating(rating);
        return highRatedMovies;
    }
}
