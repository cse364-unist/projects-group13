package com.example.cse364project;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.domain.Rating;
import com.example.cse364project.domain.User;
import com.example.cse364project.repository.MovieRepository;
import com.example.cse364project.repository.RatingRepository;
import com.example.cse364project.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class LoadDatabase implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    public LoadDatabase(MovieRepository movieRepository, RatingRepository ratingRepository,
            UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Rating> ratings = readRatings("data/ratings.dat");
        ratingRepository.saveAll(ratings);

        log.info("Rating Database has been loaded.");

        List<User> users = readUsers("data/users.dat");
        userRepository.saveAll(users);

        log.info("Users Database has been loaded.");

        List<Movie> movies = readMovies("data/movies.dat");
        movieRepository.saveAll(movies);

        log.info("Movies Database has been loaded.");
    }

    private List<Rating> readRatings(String filename) throws IOException {
        List<Rating> ratings = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("::");
            Rating rating = new Rating(parts[0], parts[1],
                    Integer.parseInt(parts[2]), parts[3]);
        
            ratings.add(rating);
        }
        reader.close();
        return ratings;
    }

    private List<User> readUsers(String filename) throws IOException {
        List<User> users = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("::");
            User user = new User(parts[0], parts[1].charAt(0), Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[3]), parts[4]);
        
            users.add(user);
        }
        reader.close();
        return users;
    }

    private List<Movie> readMovies(String filename) throws IOException {
        List<Movie> movies = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("::");
            String[] titleYear = parts[1].split("\\(");
            String title = titleYear[0].trim();

            if (titleYear.length >= 3) {
                title += " ";
                for(int i = 1; i < titleYear.length - 1; i++) {
                    title += "(" + titleYear[i];
                }
            }

            String[] yearAndNumber = titleYear[titleYear.length - 1].split("\\)");
            int year = Integer.parseInt(yearAndNumber[0].trim());
            List<String> genreList = new ArrayList<>(Arrays.asList(parts[2].split("\\|")));
            Collections.sort(genreList);
            Movie movie = new Movie(parts[0], title, year, genreList);
            movies.add(movie);
        }
        reader.close();
        return movies;
    }
}
