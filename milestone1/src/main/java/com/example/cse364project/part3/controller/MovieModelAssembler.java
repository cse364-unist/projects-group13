package com.example.cse364project.part3.controller;

import com.example.cse364project.part3.domain.Movie;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MovieModelAssembler implements RepresentationModelAssembler<Movie, EntityModel<Movie>> {

    @Override
    @SuppressWarnings("null")
    public EntityModel<Movie> toModel(Movie movie) {
        return EntityModel.of(movie,
                linkTo(methodOn(MovieController.class).getMovieById(movie.getId())).withSelfRel(),
                linkTo(methodOn(MovieController.class).getMovies(null, null)).withRel("all")
        );
    }
}
