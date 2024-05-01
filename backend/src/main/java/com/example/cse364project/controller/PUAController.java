package com.example.cse364project.controller;

import com.example.cse364project.service.PUAService;
import com.example.cse364project.service.PUAService.MovieDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pua")
public class PUAController {

    @Autowired
    private PUAService fpuaService;

    /**
     * An API endpoint that returns detailed information about the top five highest-rated movies in the specified genres.
     * Receive a genre list from the client as a request parameter and search for movies that meet the conditions.
     *
     * @param `genres` List of genres of the movie you want to search for; The genre is passed through the URL query string.
     * @return Returns a list containing detailed information of the top 5 movies with the highest ratings in JSON format.
     */
    @GetMapping("/top-rated")
    public List<MovieDetail> getTopRatedMoviesByGenre(@RequestParam List<String> genres) {
        return fpuaService.findTopRatedMoviesByGenre(genres);
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
        return fpuaService.findLowestRatedMoviesByGenre(genres);
    }
}
