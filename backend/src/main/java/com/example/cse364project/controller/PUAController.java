package com.example.cse364project.controller;

import com.example.cse364project.service.PUAService;
import com.example.cse364project.service.PUAService.MovieDetail;
import com.example.cse364project.service.PUAService.MovieRatingAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pua")
@CrossOrigin(origins = "http://localhost:3000")
public class PUAController {

    @Autowired
    private PUAService puaService;

    /**
     * An API endpoint that returns detailed information about the top five highest-rated movies in the specified genres.
     * Receive a genre list from the client as a request parameter and search for movies that meet the conditions.
     *
     * @param `genres` List of genres of the movie you want to search for; The genre is passed through the URL query string.
     * @return Returns a list containing detailed information of the top 5 movies with the highest ratings in JSON format.
     */
    @GetMapping("/top-rated")
    public List<MovieDetail> getTopRatedMoviesByGenre(@RequestParam List<String> genres) {
        return puaService.findTopRatedMoviesByGenre(genres);
    }

    /**
     * An API endpoint that returns detailed information about the top five lowest-rated movies in the specified genres.
     * Receive a genre list from the client as a request parameter and search for movies that meet the conditions.
     *
     * @param `genres` List of genres of the movie you want to search for; The genre is passed through the URL query string.
     * @return Returns a list containing detailed information of the top 5 movies with the lowest ratings in JSON format.
     */
    @GetMapping("/lowest-rated")
    public List<MovieDetail> getLowestRatedMoviesByGenre(@RequestParam List<String> genres) {
        return puaService.findLowestRatedMoviesByGenre(genres);
    }

    /**
     * An API endpoint that returns the average rating and user demographics based on rating scores
     * for movies in the specified genres.
     *
     * @param genres List of genres of the movies you want to analyze; The genres are passed through the URL query string.
     * @return Returns the average rating and user demographics for the specified genres in JSON format.
     */
    @GetMapping("/rating-analysis")
    public MovieRatingAnalysis getMovieRatingAnalysis(@RequestParam List<String> genres) {
        return puaService.getMovieRatingAnalysis(genres);
    }
}

