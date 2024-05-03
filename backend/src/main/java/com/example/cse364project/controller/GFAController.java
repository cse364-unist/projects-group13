package com.example.cse364project.controller;

import com.example.cse364project.dto.GenreRate;
import com.example.cse364project.service.GFAService;
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
@RequestMapping("/gfa")
/**
 * Controller class for G.F.A. (Genre Frequency Analysis) for Movie.
 */
public class GFAController {

    //private static final Logger log = LoggerFactory.getLogger(GFAMovieController.class);

    private final GFAService gfaService;

    public GFAController(GFAService gfaService) {
        this.gfaService = gfaService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<GenreRate>> getGenreFrequencyWithRatings() {
        List<GenreRate> genreRates = gfaService.getGenreFrequencyWithRatings();

        Link link = linkTo(methodOn(GFAController.class)
                .getGenreFrequencyWithRatings())
                .withSelfRel();

        CollectionModel<GenreRate> collectionModel = CollectionModel.of(genreRates, link);

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("genre/{genre}")
    public ResponseEntity<CollectionModel<GenreRate>> getGenreFrequencyWithRatingsIncludingGenre(@PathVariable String genre) {
        List<GenreRate> genreRates = gfaService.getGenreFrequencyWithRatingsIncludingGenre(genre);

        Link link = linkTo(methodOn(GFAController.class)
                .getGenreFrequencyWithRatingsIncludingGenre(genre))
                .withSelfRel();

        CollectionModel<GenreRate> collectionModel = CollectionModel.of(genreRates, link);

        return ResponseEntity.ok(collectionModel);
    }
    
    @GetMapping("/{year}")
    public ResponseEntity<CollectionModel<GenreRate>> getGenreFrequencyWithRatingsIncludingYear(@PathVariable int year) {
        List<GenreRate> genreRates = gfaService.getGenreFrequencyWithRatingsIncludingYear(year);

        Link link = linkTo(methodOn(GFAController.class)
                .getGenreFrequencyWithRatingsIncludingYear(year))
                .withSelfRel();
    
        CollectionModel<GenreRate> collectionModel = CollectionModel.of(genreRates, link);
    
        return ResponseEntity.ok(collectionModel);
    }
}