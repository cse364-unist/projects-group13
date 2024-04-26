package com.example.cse364project.controller;

import com.example.cse364project.service.FPUAService;
import com.example.cse364project.service.FPUAService.MovieDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fpua")
public class FPUAController {

    @Autowired
    private FPUAService fPUAService;

    @GetMapping("/top-rated")
    public List<MovieDetail> getTopRatedMoviesByGenre(@RequestParam List<String> genres) {
        return fPUAService.findTopRatedMoviesByGenre(genres);
    }

    @GetMapping("/lowest-rated")
    public List<MovieDetail> getLowestRatedMoviesByGenre(@RequestParam List<String> genres) {
        return fPUAService.findLowestRatedMoviesByGenre(genres);
    }
}
