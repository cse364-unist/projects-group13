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

    public List<Movie> findMoviesWithGTEAverageRatingAndYear(int rating, int year) {
        List<Movie> movies = this.findMoviesWithGTEAverageRating(rating);
        List<Movie> movies_with_year = new ArrayList<>();
        for(Movie m: movies) {
            if (m.getYear() == year) movies_with_year.add(m);
        }
        return movies_with_year;
    }

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

    public double findAverageRatingByMovieId(String movieId) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("movieId").is(movieId)),
            Aggregation.group().avg("rate").as("averageRating")
        );
    
        AggregationResults<MovieRate> result = mongoTemplate.aggregate(aggregation, "ratings", MovieRate.class);
        MovieRate movieRate = result.getUniqueMappedResult();
        return movieRate != null ? movieRate.getAverageRating() : 0.0;
    }

    public List<MovieRate> findMoviesWithAverageRatingByYear(int year) {
        // 연도에 해당하는 영화들의 ID를 가져옵니다.
        List<String> movieIds = movieRepository.findByYear(year).stream()
                                .map(Movie::getId)
                                .collect(Collectors.toList());

        // 연도에 해당하는 영화들의 평균 평점을 한 번에 가져옵니다.
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("movieId").in(movieIds)),
            Aggregation.group("movieId").avg("rate").as("averageRating")
        );
    
        AggregationResults<MovieRate> result = mongoTemplate.aggregate(aggregation, "ratings", MovieRate.class);
    
        return result.getMappedResults();
    }
    
}
