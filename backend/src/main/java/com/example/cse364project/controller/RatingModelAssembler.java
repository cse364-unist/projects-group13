package com.example.cse364project.controller;

import com.example.cse364project.domain.Rating;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RatingModelAssembler implements RepresentationModelAssembler<Rating, EntityModel<Rating>> {

    @Override
    public EntityModel<Rating> toModel(Rating rating) {
        return EntityModel.of(rating,
                linkTo(methodOn(RatingController.class).getRatingById(rating.getId())).withSelfRel(),
                linkTo(methodOn(RatingController.class).getRatingsByMovieId(rating.getMovieId())).withRel("ratingsByMovie"),
                linkTo(methodOn(RatingController.class).getRatingsByUserId(rating.getUserId())).withRel("ratingsByUser")
        );
    }
}
