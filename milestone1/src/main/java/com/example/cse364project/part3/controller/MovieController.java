package com.example.cse364project.part3.controller;

import com.example.cse364project.part3.domain.Movie;
import com.example.cse364project.part3.service.MovieService;
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

    @SuppressWarnings("null")
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

    @SuppressWarnings("null")
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
}
