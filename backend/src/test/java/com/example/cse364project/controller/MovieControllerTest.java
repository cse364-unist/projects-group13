package com.example.cse364project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.service.MovieService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new MovieController(movieService)).build();
    }

    @Test
    void testGetAllMovies() throws Exception {
        // Mock movie data
        Movie movie1 = new Movie("1", "Movie 1", 2021, Arrays.asList("Action", "Thriller"));
        Movie movie2 = new Movie("2", "Movie 2", 2021, Arrays.asList("Action", "Thriller"));
        List<Movie> movies = Arrays.asList(movie1, movie2);

        // Mock movie service
        when(movieService.getAllMovies()).thenReturn(movies);

        // Perform GET request
        mockMvc.perform(get("/movies")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].title").value("Movie 1"))
                .andExpect(jsonPath("$.content[0].year").value(2021))
                .andExpect(jsonPath("$.content[0].genres[0]").value("Action"))
                .andExpect(jsonPath("$.content[0].genres[1]").value("Thriller"))
                .andExpect(jsonPath("$.content[1].id").value("2"))
                .andExpect(jsonPath("$.content[1].title").value("Movie 2"))
                .andExpect(jsonPath("$.content[1].year").value(2021))
                .andExpect(jsonPath("$.content[1].genres[0]").value("Action"))
                .andExpect(jsonPath("$.content[1].genres[1]").value("Thriller"));

        // Verify movie service method is called
        verify(movieService, times(1)).getAllMovies();
    }

    @Test
    void testMissGetRequest() throws Exception {
        // Mock movie data
        Movie movie1 = new Movie("1", "Movie 1", 2021, Arrays.asList("Action", "Thriller"));
        Movie movie2 = new Movie("2", "Movie 2", 2021, Arrays.asList("Action", "Thriller"));
        List<Movie> movies = Arrays.asList(movie1, movie2);

        // Mock movie service
        when(movieService.getMoviesByGenre(anyList())).thenReturn(movies);

        // Perform GET request
        mockMvc.perform(get("/movies")
                .param("genre", "Action,Thriller")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].title").value("Movie 1"))
                .andExpect(jsonPath("$.content[0].year").value(2021))
                .andExpect(jsonPath("$.content[0].genres[0]").value("Action"))
                .andExpect(jsonPath("$.content[0].genres[1]").value("Thriller"))
                .andExpect(jsonPath("$.content[1].id").value("2"))
                .andExpect(jsonPath("$.content[1].title").value("Movie 2"))
                .andExpect(jsonPath("$.content[1].year").value(2021))
                .andExpect(jsonPath("$.content[1].genres[0]").value("Action"))
                .andExpect(jsonPath("$.content[1].genres[1]").value("Thriller"));

        // Verify movie service method is called
        verify(movieService, times(1)).getMoviesByGenre(eq(Arrays.asList("Action", "Thriller")));
    }

    @Test
    void testMissGetRequest2() throws Exception {
        // Mock movie data
        Movie movie1 = new Movie("1", "Movie 1", 2021, Arrays.asList("Action", "Thriller"));
        Movie movie2 = new Movie("2", "Movie 2", 2021, Arrays.asList("Action", "Thriller"));
        List<Movie> movies = Arrays.asList(movie1, movie2);

        // Mock movie service
        when(movieService.getAllMovies()).thenReturn(movies);

        // Perform GET request
        mockMvc.perform(get("/movies")
                .param("year", "")
                .param("genre", "")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].title").value("Movie 1"))
                .andExpect(jsonPath("$.content[0].year").value(2021))
                .andExpect(jsonPath("$.content[0].genres[0]").value("Action"))
                .andExpect(jsonPath("$.content[0].genres[1]").value("Thriller"))
                .andExpect(jsonPath("$.content[1].id").value("2"))
                .andExpect(jsonPath("$.content[1].title").value("Movie 2"))
                .andExpect(jsonPath("$.content[1].year").value(2021))
                .andExpect(jsonPath("$.content[1].genres[0]").value("Action"))
                .andExpect(jsonPath("$.content[1].genres[1]").value("Thriller"));

        // Verify movie service method is called
        verify(movieService, times(1)).getAllMovies();
    }

    @Test
    void testMissGetRequest3() throws Exception {
        // Mock movie data
        Movie movie1 = new Movie("1", "Movie 1", 2021, Arrays.asList("Action", "Thriller"));
        Movie movie2 = new Movie("2", "Movie 2", 2021, Arrays.asList("Action", "Thriller"));
        List<Movie> movies = Arrays.asList(movie1, movie2);

        // Mock movie service
        when(movieService.getAllMovies()).thenReturn(movies);

        // Perform GET request
        mockMvc.perform(get("/movies")
                .param("genre", "")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].title").value("Movie 1"))
                .andExpect(jsonPath("$.content[0].year").value(2021))
                .andExpect(jsonPath("$.content[0].genres[0]").value("Action"))
                .andExpect(jsonPath("$.content[0].genres[1]").value("Thriller"))
                .andExpect(jsonPath("$.content[1].id").value("2"))
                .andExpect(jsonPath("$.content[1].title").value("Movie 2"))
                .andExpect(jsonPath("$.content[1].year").value(2021))
                .andExpect(jsonPath("$.content[1].genres[0]").value("Action"))
                .andExpect(jsonPath("$.content[1].genres[1]").value("Thriller"));

        // Verify movie service method is called
        verify(movieService, times(1)).getAllMovies();
    }

    @Test
    void testGetMovies() throws Exception {
        // Mock movie data
        Movie movie1 = new Movie("1", "Movie 1", 2021, Arrays.asList("Action", "Thriller"));
        Movie movie2 = new Movie("2", "Movie 2", 2021, Arrays.asList("Action", "Thriller"));
        List<Movie> movies = Arrays.asList(movie1, movie2);

        // Mock movie service
        when(movieService.getMoviesByYearAndGenre(anyInt(), anyList())).thenReturn(movies);

        // Perform GET request
        mockMvc.perform(get("/movies")
                .param("year", "2021")
                .param("genre", "Action,Thriller")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].title").value("Movie 1"))
                .andExpect(jsonPath("$.content[0].year").value(2021))
                .andExpect(jsonPath("$.content[0].genres[0]").value("Action"))
                .andExpect(jsonPath("$.content[0].genres[1]").value("Thriller"))
                .andExpect(jsonPath("$.content[1].id").value("2"))
                .andExpect(jsonPath("$.content[1].title").value("Movie 2"))
                .andExpect(jsonPath("$.content[1].year").value(2021))
                .andExpect(jsonPath("$.content[1].genres[0]").value("Action"))
                .andExpect(jsonPath("$.content[1].genres[1]").value("Thriller"));

        // Verify movie service method is called
        verify(movieService, times(1)).getMoviesByYearAndGenre(eq(2021), eq(Arrays.asList("Action", "Thriller")));
    }

    @Test
    void testGetMovies2() throws Exception {
        // Mock movie data
        Movie movie1 = new Movie("1", "Movie 1", 2022, Arrays.asList("Action", "Thriller"));
        Movie movie2 = new Movie("2", "Movie 2", 2022, Arrays.asList("Action", "Thriller"));
        List<Movie> movies = Arrays.asList(movie1, movie2);

        // Mock movie service
        when(movieService.getMoviesByYear(anyInt())).thenReturn(movies);

        // Perform GET request
        mockMvc.perform(get("/movies")
                .param("year", "2022")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].title").value("Movie 1"))
                .andExpect(jsonPath("$.content[0].year").value(2022))
                .andExpect(jsonPath("$.content[0].genres[0]").value("Action"))
                .andExpect(jsonPath("$.content[0].genres[1]").value("Thriller"))
                .andExpect(jsonPath("$.content[1].id").value("2"))
                .andExpect(jsonPath("$.content[1].title").value("Movie 2"))
                .andExpect(jsonPath("$.content[1].year").value(2022))
                .andExpect(jsonPath("$.content[1].genres[0]").value("Action"))
                .andExpect(jsonPath("$.content[1].genres[1]").value("Thriller"));

        // Verify movie service method is called
        verify(movieService, times(1)).getMoviesByYear(eq(2022));
    }

    @Test
    void testGetMovies3() throws Exception {
        // Mock movie data
        Movie movie1 = new Movie("1", "Movie 1", 2021, Arrays.asList("Action", "Thriller"));
        Movie movie2 = new Movie("2", "Movie 2", 2021, Arrays.asList("Action", "Thriller"));
        List<Movie> movies = Arrays.asList(movie1, movie2);

        // Mock movie service
        when(movieService.getMoviesByGenre(anyList())).thenReturn(movies);

        // Perform GET request
        mockMvc.perform(get("/movies")
                .param("genre", "Action,Thriller")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].title").value("Movie 1"))
                .andExpect(jsonPath("$.content[0].year").value(2021))
                .andExpect(jsonPath("$.content[0].genres[0]").value("Action"))
                .andExpect(jsonPath("$.content[0].genres[1]").value("Thriller"))
                .andExpect(jsonPath("$.content[1].id").value("2"))
                .andExpect(jsonPath("$.content[1].title").value("Movie 2"))
                .andExpect(jsonPath("$.content[1].year").value(2021))
                .andExpect(jsonPath("$.content[1].genres[0]").value("Action"))
                .andExpect(jsonPath("$.content[1].genres[1]").value("Thriller"));

        // Verify movie service method is called
        verify(movieService, times(1)).getMoviesByGenre(eq(Arrays.asList("Action", "Thriller")));
    }

    @Test
    void testGetMovieById() throws Exception {
        // Mock movie data
        Movie movie = new Movie("1", "Movie 1", 2021, Arrays.asList("Action", "Thriller"));

        // Mock movie service
        when(movieService.getMovieById(anyString())).thenReturn(movie);

        // Perform GET request
        mockMvc.perform(get("/movies/{id}", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Movie 1"))
                .andExpect(jsonPath("$.year").value(2021))
                .andExpect(jsonPath("$.genres[0]").value("Action"))
                .andExpect(jsonPath("$.genres[1]").value("Thriller"));

        // Verify movie service method is called
        verify(movieService, times(1)).getMovieById(eq("1"));
    }

    @Test
    void testAddMovie() throws Exception {
        // Mock movie data
        Movie movie = new Movie("1", "Movie 1", 2021, Arrays.asList("Action", "Thriller"));

        // Mock movie service
        when(movieService.addMovie(any(Movie.class))).thenReturn(movie);

        // Perform POST request
        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"title\":\"Movie 1\",\"year\":2021,\"genre\":[\"Action\",\"Thriller\"]}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Movie 1"))
                .andExpect(jsonPath("$.year").value(2021))
                .andExpect(jsonPath("$.genres[0]").value("Action"))
                .andExpect(jsonPath("$.genres[1]").value("Thriller"));

        // Verify movie service method is called
        verify(movieService, times(1)).addMovie(any(Movie.class));
    }

    @Test
    void testUpdateMovie() throws Exception {
        // Mock movie data
        Movie movie = new Movie("1", "Movie 1", 2021, Arrays.asList("Action", "Thriller"));

        // Mock movie service
        when(movieService.updateMovie(anyString(), any(Movie.class))).thenReturn(movie);

        // Perform PUT request
        mockMvc.perform(put("/movies/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"title\":\"Movie 1\",\"year\":2021,\"genre\":[\"Action\",\"Thriller\"]}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Movie 1"))
                .andExpect(jsonPath("$.year").value(2021))
                .andExpect(jsonPath("$.genres[0]").value("Action"))
                .andExpect(jsonPath("$.genres[1]").value("Thriller"));

        // Verify movie service method is called
        verify(movieService, times(1)).updateMovie(eq("1"), any(Movie.class));
    }

    @Test
    void testDeleteMovie() throws Exception {
        // Perform DELETE request
        mockMvc.perform(delete("/movies/{id}", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify movie service method is called
        verify(movieService, times(1)).deleteMovie(eq("1"));
    }

    @Test
    void testPatchMovie() throws Exception {
        // Mock movie data
        Movie movie = new Movie("1", "Movie 1", 2021, Arrays.asList("Action", "Thriller"));

        // Mock movie service
        when(movieService.patchMovie(anyString(), any(Movie.class))).thenReturn(movie);

        // Perform PATCH request
        mockMvc.perform(patch("/movies/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"title\":\"Movie 1\",\"year\":2021,\"genre\":[\"Action\",\"Thriller\"]}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Movie 1"))
                .andExpect(jsonPath("$.year").value(2021))
                .andExpect(jsonPath("$.genres[0]").value("Action"))
                .andExpect(jsonPath("$.genres[1]").value("Thriller"));

        // Verify movie service method is called
        verify(movieService, times(1)).patchMovie(eq("1"), any(Movie.class));
    }
}