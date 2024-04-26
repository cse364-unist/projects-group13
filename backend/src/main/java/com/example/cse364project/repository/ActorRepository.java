package com.example.cse364project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.cse364project.domain.Actor;

public interface ActorRepository extends MongoRepository<Actor, String> {

}
