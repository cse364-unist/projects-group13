package com.example.cse364project.part3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.cse364project.part3.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{$and: [" +
            "{$or: [{'gender': ?0}, {'gender': null}]}, " +
            "{$or: [{'age': ?1}, {'age': null}]}, " +
            "{$or: [{'occupation': ?2}, {'occupation': null}]}, " +
            "{$or: [{'postal': ?3}, {'postal': null}]} " +
            "]}")
    Optional<List<User>> findByDynamicQuery(Character gender, Integer age, Integer occupation, String postal);

    Optional<List<User>> findByGenderAndAgeAndOccupationAndPostal(Character gender, Integer age, Integer occupation, String postal);
    
}
