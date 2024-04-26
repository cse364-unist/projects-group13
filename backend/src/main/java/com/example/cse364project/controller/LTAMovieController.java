package com.example.cse364project.controller;

import com.example.cse364project.dto.GenreRate;
import com.example.cse364project.service.LTAMovieService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/ltamovie")
public class LTAMovieController {

    private static final Logger log = LoggerFactory.getLogger(LTAMovieController.class);

    private final LTAMovieService ltaMovieService;

    public LTAMovieController(LTAMovieService ltaMovieService) {
        this.ltaMovieService = ltaMovieService;
    }

    @GetMapping("/{year}")
    public ResponseEntity<CollectionModel<GenreRate>> getGenreCombinationsWithAverageRatings(@PathVariable int year) {
        List<GenreRate> genreRates = ltaMovieService.getGenreCombinationsWithAverageRatings(year);

        log.info("size: " + genreRates.size());
        if (genreRates.size() != 0) {
            for(GenreRate genre: genreRates) {
                log.info(genre.toString());
            }
        }
    
        for (GenreRate genreRate : genreRates) {
            Link selfLink = linkTo(methodOn(LTAMovieController.class)
                    .getGenreCombinationsWithAverageRatings(year))
                    .withSelfRel();
            genreRate.add(selfLink);
        }
    
        Link link = linkTo(methodOn(LTAMovieController.class)
                .getGenreCombinationsWithAverageRatings(year))
                .withSelfRel();
    
        CollectionModel<GenreRate> collectionModel = CollectionModel.of(genreRates, link);
    
        return ResponseEntity.ok(collectionModel);
    }
}