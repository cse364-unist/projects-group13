package com.example.cse364project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.cse364project.domain.Actor;
import com.example.cse364project.exception.ActorNotFoundException;
import com.example.cse364project.repository.ActorRepository;


@Service
public class GBARService {
    private final ActorRepository actorRepository;

    public GBARService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public Actor getActorByName(String name) {
        Optional<Actor> actor = actorRepository.findById(name);
        return actor.orElseThrow(() -> new ActorNotFoundException("Cannot find any with name " + name + "."));
    }

    public List<Actor> getAllActor() {
        
        return actorRepository.findAll(); 
    }
}
