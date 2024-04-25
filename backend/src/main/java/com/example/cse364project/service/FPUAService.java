package com.example.cse364project.service;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.domain.Rating;
import com.example.cse364project.domain.User;
import com.example.cse364project.repository.MovieRepository;
import com.example.cse364project.repository.RatingRepository;
import com.example.cse364project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FPUAService {
    private static final Logger log = LoggerFactory.getLogger(FPUAService.class);


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    public List<MovieDetail> findTopRatedMoviesByGenre(List<String> genres) {
        log.info("Finding movies with genres: {}", genres);
        // 1. Find movies that match the genre criteria
        Criteria criteria = Criteria.where("genres").all(genres);
        Aggregation findMovies = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.lookup("ratings", "id", "movieId", "ratings")
        );

        // Extract movie IDs
        AggregationResults<Movie> movies = mongoTemplate.aggregate(findMovies, "movies", Movie.class);
        List<String> movieIds = movies.getMappedResults().stream()
                .map(Movie::getId)
                .collect(Collectors.toList());

        log.info("Movies found: {}", movies.getMappedResults());

        // 2. Calculate average ratings for these movies
        Criteria ratingCriteria = Criteria.where("movieId").in(movieIds);
        Aggregation ratingAggregation = Aggregation.newAggregation(
                Aggregation.match(ratingCriteria),
                Aggregation.group("movieId")  // 이 부분에서 "movieId"를 그룹화의 키로 사용
                        .avg("rate").as("averageRating"),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "averageRating")),
                Aggregation.limit(5)
        );
        AggregationResults<MovieAverage> topMovies = mongoTemplate.aggregate(ratingAggregation, "ratings", MovieAverage.class);

        log.info("Top movies: {}", topMovies.getMappedResults());

        // 3. Collect top 5 movies with details
        List<MovieDetail> details = new ArrayList<>();
        for (MovieAverage ma : topMovies.getMappedResults()) {
            Movie movie = movieRepository.findById(ma.getMovieId()).orElse(null);
            Optional<List<Rating>> optionalRatings = ratingRepository.findByMovieId(ma.getMovieId());
            List<Rating> ratings = optionalRatings.orElse(Collections.emptyList());  // Optional에서 List<Rating> 추출

            List<User> users = ratings.stream()
                    .map(rating -> userRepository.findById(rating.getUserId()).orElse(null))
                    .filter(Objects::nonNull) // null인 사용자를 제거
                    .collect(Collectors.toList());
            details.add(new MovieDetail(movie, ma.getAverageRating(), users));
        }

        log.info("Final movie details: {}", details);


        return details;
    }

    public static class MovieAverage {

        @Field("_id")  // MongoDB 결과의 _id 필드를 movieId 필드에 매핑
        private String movieId;
        private double averageRating;


        public MovieAverage(String movieId, double averageRating) {
            this.movieId = movieId;
            this.averageRating = averageRating;
        }

        public String getMovieId() {
            return movieId;
        }

        public void setMovieId(String movieId) {
            this.movieId = movieId;
        }

        public double getAverageRating() {
            return averageRating;
        }

        @Override
        public String toString() {
            return "MovieAverage{" +
                    "movieId='" + movieId + '\'' +
                    ", averageRating=" + averageRating +
                    '}';
        }
    }

    public static class MovieDetail {
        private Movie movie;
        private double averageRating;
        private List<User> users;

        public MovieDetail(Movie movie, double averageRating, List<User> users) {
            this.movie = movie;
            this.averageRating = averageRating;
            this.users = users;
        }

        public Movie getMovie() {
            return movie;
        }

        public double getAverageRating() {
            return averageRating;
        }

        public List<User> getUsers() {
            return users;
        }

        public void setMovie(Movie movie) {
            this.movie = movie;
        }

        @Override
        public String toString() {
            return "MovieDetail{" +
                    "movie=" + movie +
                    ", averageRating=" + averageRating +
                    ", users=" + users.stream().map(User::toString).collect(Collectors.joining(", ")) +
                    '}';
        }
    }

}
