package com.example.cse364project.part3.service;

import com.example.cse364project.part3.domain.Movie;
import com.example.cse364project.part3.exception.MovieNotFoundException;
import com.example.cse364project.part3.repository.MovieRepository;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(String id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Could not find ID " + id + "."));
    }

    public Movie addMovie(Movie movie) {
        if (movieRepository.existsById(movie.getId())) return updateMovie(movie.getId(), movie);
        return movieRepository.save(movie);
    }

    public Movie updateMovie(String id, Movie movie) {
        Movie updatedMovie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Could not find ID " + id + "."));
        updatedMovie.setGenres(movie.getGenres());
        updatedMovie.setTitle(movie.getTitle());
        updatedMovie.setYear(movie.getYear());
        return movieRepository.save(updatedMovie);
    }

    public void deleteMovie(String id) {
        if (!movieRepository.existsById(id))
            throw new MovieNotFoundException("Could not find ID " + id + ".");
        movieRepository.deleteById(id);
    }

    public List<Movie> getMoviesByYearAndGenre(int year, List<String> genres) {
        if (year != 0 && genres != null && !genres.isEmpty()) {
            return movieRepository.findByYearAndGenresContaining(year, genres);
        }
        return Collections.emptyList();
    }

    public List<Movie> getMoviesByYear(int year) {
        return movieRepository.findByYear(year);
    }

    public List<Movie> getMoviesByGenre(List<String> genres) {
        if (genres != null && !genres.isEmpty()) {
            return movieRepository.findByGenresContaining(genres);
        }
        return Collections.emptyList();
    }
}