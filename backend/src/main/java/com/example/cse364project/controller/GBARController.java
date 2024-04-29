package com.example.cse364project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cse364project.domain.Actor;
import com.example.cse364project.domain.ActorRequest;
import com.example.cse364project.feature2.Recommendator;
import com.example.cse364project.service.GBARService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/gbar")
public class GBARController {
    
    private final GBARService GBARService;

    public GBARController(GBARService GBARService) {
        this.GBARService = GBARService;
    }

    /**
     * An API endpoint that returns one actor that name is input
     *
     * @param name name of actor
     * @return return one actor that has same name
     */
    @GetMapping("/find")
    public ResponseEntity<Actor> getOneActor(@RequestParam("name") String name) {

        return ResponseEntity.ok(GBARService.getActorByName(name));
    }

    /**
     * An API endpoint that returns some actor recommendation
     * Receive a Request domain that has vectorized genre and ratang, list of supporting actors and movie plot
     * We will consider supporting actors and movie plot to make a better performance later.
     * 
     * POST request example 
     * curl -X POST http://localhost:8080/gbar/recommend -H ‘Content-type:application/json’ -d '{ 
     * "genre":[ 27.6, 7.4, 0, 52, 0, 6.6, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ],
     * "synergy" : 20
     * "supporter" : ["Robert Hays", "John Belushi"],
     * "plot": "plot is here"
     * }''
     *
     * @param `request` vectorized genre and ratang : used to calculate cosine similarity
     *                  list of supporting actors : actors that has similar performance can have sinergy, used to implement vector
     *                  movie plot : used to determine more specefic information of genre
     * @return listup some recommendations of actors that has high similarity with input vector
     */
    @PostMapping("/recommend")
    public ResponseEntity<Set<Actor>> recommendActor(@RequestBody ActorRequest request) {

        List<Actor> supporters = new ArrayList<>();
        for (String name : request.getSupporter()) {
            supporters.add(GBARService.getActorByName(name));
        }

        Recommendator recommedator = new Recommendator();
        Set<Actor> result = recommedator.recommend(
                                                request.getGenre(),
                                                supporters,
                                                request.getSynergy(),
                                                request.getPlot(),
                                                GBARService.getAllActor(), 5);
        
        return ResponseEntity.ok(result);
    } 

}