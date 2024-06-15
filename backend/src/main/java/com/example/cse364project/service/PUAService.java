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
public class PUAService {
    private static final Logger log = LoggerFactory.getLogger(PUAService.class);


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    public MovieRatingAnalysis getMovieRatingAnalysis(List<String> genres) {
        log.info("Analyzing movies with genres: {}", genres);

        // Step 1: Find movies that match the genre criteria
        Criteria criteria = Criteria.where("genres").all(genres);
        Aggregation findMovies = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.lookup("ratings", "id", "movieId", "ratings")
        );

        AggregationResults<Movie> movies = mongoTemplate.aggregate(findMovies, "movies", Movie.class);
        List<String> movieIds = movies.getMappedResults().stream()
                .map(Movie::getId)
                .collect(Collectors.toList());

        log.info("Movies found: {}", movies.getMappedResults());

        // Step 2: Calculate average ratings for these movies
        Criteria ratingCriteria = Criteria.where("movieId").in(movieIds);
        Aggregation ratingAggregation = Aggregation.newAggregation(
                Aggregation.match(ratingCriteria),
                Aggregation.group("movieId")
                        .avg("rate").as("averageRating")
        );
        AggregationResults<MovieAverage> averageRatings = mongoTemplate.aggregate(ratingAggregation, "ratings", MovieAverage.class);

        double totalAverageRating = averageRatings.getMappedResults().stream()
                .mapToDouble(MovieAverage::getAverageRating)
                .average()
                .orElse(0.0);

        log.info("Average rating: {}", totalAverageRating);

        // Step 3: Collect user demographics based on rating scores
        List<Rating> ratings = ratingRepository.findByMovieIdIn(movieIds);

        UserDemographics highRatings = new UserDemographics();
        UserDemographics lowRatings = new UserDemographics();

        for (Rating rating : ratings) {
            User user = userRepository.findById(rating.getUserId()).orElse(null);
            if (user == null) continue;

            if (rating.getRate() >= 4) {
                highRatings.addUser(user);
            } else if (rating.getRate() <= 3) {
                lowRatings.addUser(user);
            }
        }

        return new MovieRatingAnalysis(totalAverageRating, highRatings, lowRatings);
    }

    public static class UserDemographics {
        private Map<Character, Integer> genderCount = new HashMap<>();
        private Map<Integer, Integer> ageCount = new HashMap<>();
        private Map<Integer, Integer> occupationCount = new HashMap<>();

        public void addUser(User user) {
            genderCount.put(user.getGender(), genderCount.getOrDefault(user.getGender(), 0) + 1);
            ageCount.put(user.getAge(), ageCount.getOrDefault(user.getAge(), 0) + 1);
            occupationCount.put(user.getOccupation(), occupationCount.getOrDefault(user.getOccupation(), 0) + 1);
        }

        public Map<Character, Integer> getGenderCount() {
            return genderCount;
        }

        public Map<Integer, Integer> getAgeCount() {
            return ageCount;
        }

        public Map<Integer, Integer> getOccupationCount() {
            return occupationCount;
        }

        @Override
        public String toString() {
            return "UserDemographics{" +
                    "genderCount=" + genderCount +
                    ", ageCount=" + ageCount +
                    ", occupationCount=" + occupationCount +
                    '}';
        }
    }

    public static class MovieRatingAnalysis {
        private double averageRating;
        private UserDemographics highRatingUsers;
        private UserDemographics lowRatingUsers;

        public MovieRatingAnalysis(double averageRating, UserDemographics highRatingUsers, UserDemographics lowRatingUsers) {
            this.averageRating = averageRating;
            this.highRatingUsers = highRatingUsers;
            this.lowRatingUsers = lowRatingUsers;
        }

        public double getAverageRating() {
            return averageRating;
        }

        public UserDemographics getHighRatingUsers() {
            return highRatingUsers;
        }

        public UserDemographics getLowRatingUsers() {
            return lowRatingUsers;
        }

        @Override
        public String toString() {
            return "MovieRatingAnalysis{" +
                    "averageRating=" + averageRating +
                    ", highRatingUsers=" + highRatingUsers +
                    ", lowRatingUsers=" + lowRatingUsers +
                    '}';
        }
    }


    /**
     * Finds and returns the top five "highest rated movies" in the specified genres.
     * After searching the database for movies that fit the genre,
     * we average the ratings for each movie and select the movies with the highest ratings.
     *
     * @param `genres` List of genres of the movie you want to search
     * @return `details` A list containing detailed information(includes "users") of the top 5 movies with the highest ratings
     */
    public List<MovieDetail> findTopRatedMoviesByGenre(List<String> genres) {
        log.info("Finding movies with genres: {}", genres);
        // 1. Find movies that match the genre criteria
        Criteria criteria = Criteria.where("genres").all(genres);
        Aggregation findMovies = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.lookup("ratings", "id", "movieId", "ratings")
        );

        AggregationResults<Movie> movies = mongoTemplate.aggregate(findMovies, "movies", Movie.class);
        List<String> movieIds = movies.getMappedResults().stream()
                .map(Movie::getId)
                .collect(Collectors.toList());

        log.info("Movies found: {}", movies.getMappedResults());

        // 2. Calculate average ratings for these movies
        Criteria ratingCriteria = Criteria.where("movieId").in(movieIds);
        Aggregation ratingAggregation = Aggregation.newAggregation(
                Aggregation.match(ratingCriteria),
                Aggregation.group("movieId")
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
            List<Rating> ratings = optionalRatings.orElse(Collections.emptyList());

            List<User> users = ratings.stream()
                    .map(rating -> userRepository.findById(rating.getUserId()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            details.add(new MovieDetail(movie, ma.getAverageRating(), users));
        }

        log.info("Final movie details: {}", details);


        return details;
    }

    /**
     * Finds and returns the top five "lowest rated movies" in the specified genres.
     * After searching the database for movies that fit the genre,
     * we average the ratings for each movie and select the movies with the lowest ratings.
     *
     * @param `genres` List of genres of the movie you want to search
     * @return `details` A list containing detailed information(includes "users") of the lowest 5 movies with the highest ratings
     */
    public List<MovieDetail> findLowestRatedMoviesByGenre(List<String> genres) {
        log.info("Finding movies with genres: {}", genres);
        // 1. Find movies that match the genre criteria
        Criteria criteria = Criteria.where("genres").all(genres);
        Aggregation findMovies = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.lookup("ratings", "id", "movieId", "ratings")
        );

        AggregationResults<Movie> movies = mongoTemplate.aggregate(findMovies, "movies", Movie.class);
        List<String> movieIds = movies.getMappedResults().stream()
                .map(Movie::getId)
                .collect(Collectors.toList());

        log.info("Movies found: {}", movies.getMappedResults());

        // 2. Calculate average ratings for these movies
        Criteria ratingCriteria = Criteria.where("movieId").in(movieIds);
        Aggregation ratingAggregation = Aggregation.newAggregation(
                Aggregation.match(ratingCriteria),
                Aggregation.group("movieId")
                        .avg("rate").as("averageRating"),
                Aggregation.sort(Sort.by(Sort.Direction.ASC, "averageRating")),
                Aggregation.limit(5)
        );
        AggregationResults<MovieAverage> lowestMovies = mongoTemplate.aggregate(ratingAggregation, "ratings", MovieAverage.class);

        log.info("Lowest movies: {}", lowestMovies.getMappedResults());

        // 3. Collect lowest 5 movies with details
        List<MovieDetail> details = new ArrayList<>();
        for (MovieAverage ma : lowestMovies.getMappedResults()) {
            Movie movie = movieRepository.findById(ma.getMovieId()).orElse(null);
            Optional<List<Rating>> optionalRatings = ratingRepository.findByMovieId(ma.getMovieId());
            List<Rating> ratings = optionalRatings.orElse(Collections.emptyList());

            List<User> users = ratings.stream()
                    .map(rating -> userRepository.findById(rating.getUserId()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            details.add(new MovieDetail(movie, ma.getAverageRating(), users));
        }

        log.info("Final movie details: {}", details);


        return details;
    }

    /**
     * This class contains average rating information for movies.
     * Stores each movie ID and the average rating for that movie.
     *
     * @param `movieId` Unique ID of the movie
     * @param `averageRating` Average rating of the movie
     */
    public static class MovieAverage {

        @Field("_id")
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

    /**
     * This class contains detailed information about the movie.
     * Includes basic information about the movie, average rating, and a list of users who rated the movie.
     *
     * @param `movie` movie information
     * @param `averageRating` Average rating of the movie
     * @param `users` List of users who rated the movie
     */
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
