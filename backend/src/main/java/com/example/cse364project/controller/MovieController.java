package com.example.cse364project.controller;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.service.MovieService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Movie>>> getMovies(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) List<String> genre) {
        List<Movie> movies;

        if (year != null && genre != null && !genre.isEmpty())
            movies = movieService.getMoviesByYearAndGenre(year, genre);
        else if (year != null)
            movies = movieService.getMoviesByYear(year);
        else if (genre != null && !genre.isEmpty())
            movies = movieService.getMoviesByGenre(genre);
        else
            movies = movieService.getAllMovies();

        List<EntityModel<Movie>> movieModels = new ArrayList<>();
        for (Movie movie : movies) {
            movieModels.add(EntityModel.of(movie,
                linkTo(methodOn(MovieController.class).getMovieById(movie.getId())).withSelfRel()));
        }

        CollectionModel<EntityModel<Movie>> movieCollectionModel = CollectionModel.of(movieModels, 
            linkTo(methodOn(MovieController.class).getMovies(year, genre)).withSelfRel());

        return ResponseEntity.ok(movieCollectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Movie>> getMovieById(@PathVariable String id) {
        Movie movie = movieService.getMovieById(id);
        EntityModel<Movie> movieModel = EntityModel.of(movie);
        Link selfLink = Link.of("/movies/" + movieModel.getContent().getId()).withSelfRel();
        movieModel.add(selfLink);

        return ResponseEntity.ok(movieModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Movie>> addMovie(@RequestBody Movie movie) {
        Movie newMovie = movieService.addMovie(movie);
        EntityModel<Movie> movieModel = EntityModel.of(newMovie);
        Link selfLink = Link.of("/movies/" + movieModel.getContent().getId()).withSelfRel();
        movieModel.add(selfLink);

        return ResponseEntity.ok(movieModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Movie>> updateMovie(@PathVariable String id, @RequestBody Movie movie) {
        Movie updatedMovie = movieService.updateMovie(id, movie);
        EntityModel<Movie> movieModel = EntityModel.of(updatedMovie);
        Link selfLink = Link.of("/movies/" + movieModel.getContent().getId()).withSelfRel();
        movieModel.add(selfLink);

        return ResponseEntity.ok(movieModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<Movie>> patchMovie(@PathVariable String id, @RequestBody Movie movie) {
        Movie patchedMovie = movieService.patchMovie(id, movie);
        EntityModel<Movie> movieModel = EntityModel.of(patchedMovie);
        Link selfLink = Link.of("/movies/" + movieModel.getContent().getId()).withSelfRel();
        movieModel.add(selfLink);

        return ResponseEntity.ok(movieModel);
    }
}
