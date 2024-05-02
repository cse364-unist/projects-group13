package com.example.cse364project;

import com.example.cse364project.domain.Actor;
import com.example.cse364project.domain.Movie;
import com.example.cse364project.domain.Rating;
import com.example.cse364project.domain.User;
import com.example.cse364project.repository.ActorRepository;
import com.example.cse364project.repository.MovieRepository;
import com.example.cse364project.repository.RatingRepository;
import com.example.cse364project.repository.UserRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

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
    private final ActorRepository actorRepository;

    public LoadDatabase(MovieRepository movieRepository, RatingRepository ratingRepository,
            UserRepository userRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.actorRepository = actorRepository;
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

        //feature 3
        List<Actor> actors = readActors("backend/data/feature2/movies.csv"); //"data/feature2/movies.csv"
        actorRepository.saveAll(actors);

        log.info("Actors Database has been loaded.");
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
    // this function reads movie.csv

    private List<Actor> readActors(String filename) throws CsvValidationException, IOException {
        List<Actor> actors = new ArrayList<>();

        //CSV reader
        CSVReader csvReader = new CSVReader(new FileReader(filename));
        String[] parts;

        // all genre in movie.csv file
        String[] genreArray = {"Drama", "Adventure", "Action", "Comedy", "Horror", "Biography", "Crime", "Fantasy", "Family", "Sci-Fi", "Animation", "Romance", "Music", "Western", "Thriller", "History", "Mystery", "Sport", "Musical"};

        csvReader.readNext(); //read one line first : explanation line
        while ((parts = csvReader.readNext()) != null) {
            
            int existIndex = -1; // used to determine is the actor aleady in List
            Actor existActor = null;

            int genreIndex = -1;
            for (int i = 0; i < genreArray.length; i++) {
                if (parts[2].equals(genreArray[i])) {
                    genreIndex = i;
                    break;
                }
            }

            if (genreIndex == -1) {
                log.info("cannot find genre : " + parts.toString());
                continue;
            }

            for (int i = 0; i < actors.size(); i++) {
                 // parts[9] : stars
                if (parts[9].equals(actors.get(i).getName())) {
                    existIndex = i;
                    existActor = actors.get(i);
                    break;
                }
            }

            // actor that has name is aleady exist
            if (existIndex >= 0 && existActor != null) {

                existActor.getTitles().add(parts[0]); // add movie title in Actor
                existActor.getCount()[genreIndex] += 1; // count up
                existActor.getGenre()[genreIndex] += Double.parseDouble(parts[5]); // make a genre vector : add score to vector

                Actor newActor = new Actor(existActor.getName(), existActor.getTitles(), existActor.getCount(), existActor.getGenre());
                actors.set(existIndex, newActor); // change Actor in List
            }
            // actor that has name not exist yet
            else {
                List<String> newTitles = new ArrayList<>();
                newTitles.add(parts[0]); // make a title list and add first title
                int[] newCount = new int[genreArray.length];
                newCount[genreIndex] = 1; // make a count list and add first count
                double[] newGenre = new double[genreArray.length];
                newGenre[genreIndex] = Double.parseDouble(parts[5]); // make a genreScore list and add first score

                Actor newActor = new Actor(parts[9], newTitles, newCount, newGenre);
                
                actors.add(newActor); // add in Actor List
            }
        
        }
        csvReader.close();
        return actors;
    }
}
