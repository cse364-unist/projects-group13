package com.example.cse364project.controller;

import com.example.cse364project.dto.GenreRate;
import com.example.cse364project.service.GFAMovieService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/ltamovie")
/**
 * Controller class for G.F.A. (Genre Frequency Analysis) for Movie.
 */
public class GFAMovieController {

    //private static final Logger log = LoggerFactory.getLogger(GFAMovieController.class);

    private final GFAMovieService ltaMovieService;

    public GFAMovieController(GFAMovieService ltaMovieService) {
        this.ltaMovieService = ltaMovieService;
    }

    @GetMapping("/{year}")
    public ResponseEntity<CollectionModel<GenreRate>> getGenreCombinationsWithAverageRatings(@PathVariable int year) {
        List<GenreRate> genreRates = ltaMovieService.getGenreCombinationsWithAverageRatings(year);

        Link link = linkTo(methodOn(GFAMovieController.class)
                .getGenreCombinationsWithAverageRatings(year))
                .withSelfRel();
    
        CollectionModel<GenreRate> collectionModel = CollectionModel.of(genreRates, link);
    
        return ResponseEntity.ok(collectionModel);
    }
}