package com.example.cse364project.controller;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.domain.Rating;
import com.example.cse364project.exception.RatingNotFoundException;
import com.example.cse364project.repository.MovieRatingRepository;
import com.example.cse364project.service.RatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private MovieRatingRepository movieRatingRepository;

    @Autowired
    private MovieModelAssembler movieModelAssembler;

    private final RatingService ratingService;
    private final RatingModelAssembler ratingModelAssembler;

    public RatingController(RatingService ratingService, RatingModelAssembler ratingModelAssembler) {
        this.ratingService = ratingService;
        this.ratingModelAssembler = ratingModelAssembler;
    }

    // No all method: Responsing time problem
    @GetMapping("/{rating}")
    public ResponseEntity<CollectionModel<EntityModel<Movie>>> getMoviesWithRating(@PathVariable int rating,
            @RequestParam(required = false) List<String> genre,
            @RequestParam(required = false) Integer year) {
        List<Movie> movies;

        if (rating <= 0 || rating >= 6)
            throw new RatingNotFoundException("Rating values should be in between 1~5.");

        if (genre != null && year != null)
            movies = movieRatingRepository.findMoviesWithGTEAverageRatingAndGenreAndYear(rating, genre, year);
        else if (genre != null)
            movies = movieRatingRepository.findMoviesWithGTEAverageRatingAndGenres(rating, genre);
        else if (year != null)
            movies = movieRatingRepository.findMoviesWithGTEAverageRatingAndYear(rating, year);
        else
            movies = movieRatingRepository.findMoviesWithGTEAverageRating(rating);

        List<EntityModel<Movie>> movieEntity = movies.stream().map(movieModelAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Movie>> collectionModel = CollectionModel.of(movieEntity,
                linkTo(methodOn(RatingController.class).getMoviesWithRating(rating, genre, year)).withSelfRel());
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable String id) {
        Rating rating = ratingService.getRatingById(id);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addRating(@RequestBody Rating rating) {
        EntityModel<Rating> ratingModel = ratingModelAssembler.toModel(ratingService.addRating(rating));
        return ResponseEntity
                .created(ratingModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(ratingModel);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateRating(@PathVariable String id, @RequestBody Rating rating) {
        EntityModel<Rating> ratingModel = ratingModelAssembler.toModel(ratingService.updateRating(id, rating));
        return ResponseEntity
                .created(ratingModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(ratingModel);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable String id) {
        ratingService.deleteRating(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Rating>> getRatingsByMovieId(@PathVariable String movieId) {
        List<Rating> ratings = ratingService.getRatingsByMovieId(movieId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userId) {
        List<Rating> ratings = ratingService.getRatingsByUserId(userId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
}
