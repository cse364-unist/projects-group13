package com.example.cse364project.controller;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.domain.User;
import com.example.cse364project.service.PUAService.MovieDetail;
import com.example.cse364project.service.PUAService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PUAControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PUAService puaService;

    @InjectMocks
    private PUAController puaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(puaController).build();
    }

    @Test
    public void testGetTopRatedMoviesByGenre() throws Exception {
        List<Movie> movies = Arrays.asList(
                new Movie("1", "Movie 1", 2002, Arrays.asList("Genre 1")),
                new Movie("2", "Movie 2", 2003, Arrays.asList("Genre 2")),
                new Movie("3", "Movie 3", 2004, Arrays.asList("Genre 1")),
                new Movie("4", "Movie 4", 2005, Arrays.asList("Genre 3")),
                new Movie("5", "Movie 5", 2006, Arrays.asList("Genre 2"))
        );

        List<User> users = Arrays.asList(
                new User("1", 'M', 30, 1, "12345"),
                new User("2", 'F', 40, 2, "67890")
        );

        List<MovieDetail> movieDetails = Arrays.asList(
                new MovieDetail(movies.get(0), 4.2, users),
                new MovieDetail(movies.get(1), 4.7, users),
                new MovieDetail(movies.get(2), 4.8, users),
                new MovieDetail(movies.get(3), 4.1, users),
                new MovieDetail(movies.get(4), 4.4, users)
        );

        when(puaService.findTopRatedMoviesByGenre(anyList())).thenReturn(movieDetails);

        mockMvc.perform(get("/pua/top-rated")
                .param("genres", "Genre 1,Genre 2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetLowestRatedMoviesByGenre() throws Exception {
        List<Movie> movies = Arrays.asList(
                new Movie("1", "Movie 1", 2002, Arrays.asList("Genre 1")),
                new Movie("2", "Movie 2", 2003, Arrays.asList("Genre 2")),
                new Movie("3", "Movie 3", 2004, Arrays.asList("Genre 1")),
                new Movie("4", "Movie 4", 2005, Arrays.asList("Genre 3")),
                new Movie("5", "Movie 5", 2006, Arrays.asList("Genre 2"))
        );

        List<User> users = Arrays.asList(
                new User("1", 'M', 30, 1, "12345"),
                new User("2", 'F', 40, 2, "67890")
        );

        List<MovieDetail> movieDetails = Arrays.asList(
                new MovieDetail(movies.get(0), 1.2, users),
                new MovieDetail(movies.get(1), 1.7, users),
                new MovieDetail(movies.get(2), 1.8, users),
                new MovieDetail(movies.get(3), 1.1, users),
                new MovieDetail(movies.get(4), 1.4, users)
        );

        when(puaService.findLowestRatedMoviesByGenre(anyList())).thenReturn(movieDetails);

        mockMvc.perform(get("/pua/lowest-rated")
                .param("genres", "Genre 1,Genre 2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}