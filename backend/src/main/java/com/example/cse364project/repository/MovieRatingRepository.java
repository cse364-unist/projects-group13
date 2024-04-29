package com.example.cse364project.repository;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.dto.MovieRate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MovieRatingRepository {

    //private static final Logger log = LoggerFactory.getLogger(MovieRatingRepository.class);
    private final MongoTemplate mongoTemplate;

    @Autowired
    private MovieRepository movieRepository;

    public MovieRatingRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * This Java function finds movies with an average rating greater than or equal to a specified
     * value.
     * 
     * @param rating The method `findMoviesWithGTEAverageRating` is designed to find movies with an
     * average rating greater than or equal to the specified rating. The `rating` parameter is the
     * threshold value that you want to use as a filter to retrieve movies with an average rating
     * greater than or equal to this value
     * @return This method returns a list of movies that have an average rating greater than or equal
     * to the specified rating. The method first calculates the average rating for each movie using an
     * aggregation query in MongoDB. Then, it filters out movies with an average rating less than the
     * specified rating and retrieves the corresponding movie details from the movie repository.
     * Finally, it sorts the list of movies based on their IDs and returns the sorted
     */
    public List<Movie> findMoviesWithGTEAverageRating(int rating) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.group("movieId")
                       .avg("rate").as("averageRating"),
            Aggregation.match(Criteria.where("averageRating").gte(rating))
        );

        AggregationResults<MovieRate> result = mongoTemplate.aggregate(aggregation, "ratings", MovieRate.class);
        List<Movie> movies = new ArrayList<>();
        for (MovieRate ratingAverage : result.getMappedResults()) {
            String movieId = ratingAverage.getMovieId();
            Optional<Movie> opMovie = movieRepository.findById(movieId);
            if (opMovie.isPresent()) movies.add(opMovie.get());
        }

        movies.sort(Comparator.comparingInt(movie -> Integer.parseInt(movie.getId())));

        return movies;
    }

    /**
     * This Java function finds movies with an average rating greater than or equal to a specified
     * value and belonging to specified genres.
     * 
     * @param rating The `rating` parameter in the `findMoviesWithGTEAverageRatingAndGenres` method
     * represents the minimum average rating that a movie must have in order to be included in the
     * final list of movies. This method filters out movies that have an average rating less than or
     * equal to the specified `rating
     * @param genres Genres is a list of strings representing the genres that you want to filter the
     * movies by. For example, if you pass in ["Action", "Adventure"], the method will return a list of
     * movies that have both "Action" and "Adventure" genres.
     * @return This method returns a list of movies that have an average rating greater than or equal
     * to the specified rating and belong to all of the specified genres.
     */
    public List<Movie> findMoviesWithGTEAverageRatingAndGenres(int rating, List<String> genres) {
        List<Movie> movies = this.findMoviesWithGTEAverageRating(rating);
        List<Movie> movies_with_genres = new ArrayList<>();
        boolean flag;
        for(Movie m: movies) {
            flag = true;
            for(String genre: genres) {
                if (!m.getGenres().contains(genre)) {
                    flag = false;
                    break;
                }
            }
            if (flag) movies_with_genres.add(m);
        }
        return movies_with_genres;
    }

    /**
     * The function `findMoviesWithGTEAverageRatingAndYear` returns a list of movies with an average
     * rating greater than or equal to a specified rating and released in a specified year.
     * 
     * @param rating The `rating` parameter represents the minimum average rating that a movie must
     * have in order to be included in the list of movies returned by the
     * `findMoviesWithGTEAverageRatingAndYear` method.
     * @param year The `year` parameter in the `findMoviesWithGTEAverageRatingAndYear` method
     * represents the year in which the movies were released. This method filters and returns a list of
     * movies that have an average rating greater than or equal to the specified rating and were
     * released in the specified year.
     * @return This method returns a list of movies that have an average rating greater than or equal
     * to the specified rating and were released in the specified year.
     */
    public List<Movie> findMoviesWithGTEAverageRatingAndYear(int rating, int year) {
        List<Movie> movies = this.findMoviesWithGTEAverageRating(rating);
        List<Movie> movies_with_year = new ArrayList<>();
        for(Movie m: movies) {
            if (m.getYear() == year) movies_with_year.add(m);
        }
        return movies_with_year;
    }

    /**
     * This Java function finds movies with an average rating greater than or equal to a specified
     * value, belonging to specific genres, and released in a particular year.
     * 
     * @param rating The `findMoviesWithGTEAverageRatingAndGenreAndYear` method takes in three
     * parameters:
     * @param genres Genres is a list of strings representing the genres of movies. For example, genres
     * could include "Action", "Comedy", "Drama", "Sci-Fi", etc.
     * @param year The `year` parameter in the method `findMoviesWithGTEAverageRatingAndGenreAndYear`
     * is used to filter movies based on the year they were released. The method searches for movies
     * with an average rating greater than or equal to a specified rating, belonging to certain genres,
     * and released in
     * @return This method returns a list of movies that have an average rating greater than or equal
     * to the specified rating, belong to all the specified genres, and were released in the specified
     * year.
     */
    public List<Movie> findMoviesWithGTEAverageRatingAndGenreAndYear(int rating, List<String> genres, int year) {
        List<Movie> movies = this.findMoviesWithGTEAverageRating(rating);
        List<Movie> movies_with_genres_and_year = new ArrayList<>();
        boolean flag;
        for(Movie movie : movies) {
            if (movie.getYear() == year) {
                flag = true;
                for(String genre: genres) {
                    if (!movie.getGenres().contains(genre)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) movies_with_genres_and_year.add(movie);
            }
        }
        return movies_with_genres_and_year;
    }

    /**
     * The function `findAverageRatingByMovieId` calculates the average rating for a movie based on its
     * ID using MongoDB aggregation.
     * 
     * @param movieId The `findAverageRatingByMovieId` method is used to calculate the average rating
     * for a specific movie based on its `movieId`. The method performs an aggregation operation in
     * MongoDB to calculate the average rating from the `ratings` collection for the specified
     * `movieId`.
     * @return The method `findAverageRatingByMovieId` returns the average rating for a movie specified
     * by its `movieId`. If the movie rating information is found in the database, it returns the
     * average rating value. If the movie rating information is not found or if there are no ratings
     * for the movie, it returns 0.0 as the default value.
     */
    public double findAverageRatingByMovieId(String movieId) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("movieId").is(movieId)),
            Aggregation.group().avg("rate").as("averageRating")
        );
    
        AggregationResults<MovieRate> result = mongoTemplate.aggregate(aggregation, "ratings", MovieRate.class);
        MovieRate movieRate = result.getUniqueMappedResult();
        return movieRate != null ? movieRate.getAverageRating() : 0.0;
    }

    /**
     * This Java function finds movies from a specific year and calculates their average rating using
     * MongoDB aggregation.
     * 
     * @param year The method `findMoviesWithAverageRatingByYear` takes an integer parameter `year`
     * which represents the year for which you want to find movies with their average ratings. The
     * method first retrieves the movie IDs for movies released in the specified year from the
     * `movieRepository`, then uses these IDs to query
     * @return This method returns a list of `MovieRate` objects that represent the average rating for
     * movies released in a specific year. The method first retrieves the movie IDs for movies released
     * in the given year from the movie repository. Then, it performs an aggregation query on the
     * "ratings" collection in the MongoDB database to calculate the average rating for each movie ID
     * in the list. Finally, it returns a list of
     */
    public List<MovieRate> findMoviesWithAverageRatingByYear(int year) {
        List<String> movieIds = movieRepository.findByYear(year).stream()
                                .map(Movie::getId)
                                .collect(Collectors.toList());

        // Simple call for query
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("movieId").in(movieIds)),
            Aggregation.group("movieId").avg("rate").as("averageRating")
        );
    
        AggregationResults<MovieRate> result = mongoTemplate.aggregate(aggregation, "ratings", MovieRate.class);
    
        return result.getMappedResults();
    }
    
}
