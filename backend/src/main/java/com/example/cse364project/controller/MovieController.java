package com.example.cse364project.controller;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.service.MovieService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieModelAssembler movieModelAssembler;

    public MovieController(MovieService movieService, MovieModelAssembler movieModelAssembler) {
        this.movieService = movieService;
        this.movieModelAssembler = movieModelAssembler;
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

        return ResponseEntity.ok(movieModelAssembler.toCollectionModel(movies));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Movie>> getMovieById(@PathVariable String id) {
        Movie movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movieModelAssembler.toModel(movie));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Movie>> addMovie(@RequestBody Movie movie) {
        Movie newMovie = movieService.addMovie(movie);
        return ResponseEntity
                .created(linkTo(methodOn(MovieController.class).getMovieById(newMovie.getId())).toUri())
                .body(movieModelAssembler.toModel(newMovie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Movie>> updateMovie(@PathVariable String id, @RequestBody Movie movie) {
        Movie updatedMovie = movieService.updateMovie(id, movie);
        return ResponseEntity.ok(movieModelAssembler.toModel(updatedMovie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<Movie>> patchMovie(@PathVariable String id, @RequestBody Movie movie) {
        Movie patchedMovie = movieService.patchMovie(id, movie);
        return ResponseEntity.ok(movieModelAssembler.toModel(patchedMovie));
    }
}
