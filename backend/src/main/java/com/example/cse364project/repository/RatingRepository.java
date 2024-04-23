package com.example.cse364project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.cse364project.domain.Rating;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    Optional<List<Rating>> findByMovieId(String movieId);

    Optional<List<Rating>> findByUserId(String userId);

    Optional<Rating> findByMovieIdAndUserId(String movieId, String userId);

    boolean existsByMovieIdAndUserId(String string, String string2);

}
