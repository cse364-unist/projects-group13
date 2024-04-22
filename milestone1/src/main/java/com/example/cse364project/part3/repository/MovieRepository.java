package com.example.cse364project.part3.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.cse364project.part3.domain.Movie;

public interface MovieRepository extends MongoRepository<Movie, String> {

    List<Movie> findByYearAndGenresContaining(int year, List<String> genres);

    List<Movie> findByYear(int year);

    List<Movie> findByGenresContaining(List<String> genres);
    
}
