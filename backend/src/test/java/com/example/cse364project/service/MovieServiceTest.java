package com.example.cse364project.service;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.domain.Rating;
import com.example.cse364project.exception.MovieNotFoundException;
import com.example.cse364project.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    private MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieService = new MovieService(movieRepository);
    }

    @Test
    void getAllMovies() {
        Movie movie1 = new Movie("1", "Title 1", 2000, Arrays.asList("Genre1", "Genre2"));
        Movie movie2 = new Movie("2", "Title 2", 2005, Arrays.asList("Genre2", "Genre3"));

        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie1, movie2));

        List<Movie> result = movieService.getAllMovies();

        assertEquals(2, result.size());
        assertTrue(result.contains(movie1));
        assertTrue(result.contains(movie2));
    }

    @Test
    void getMovieById_existingId_returnsMovie() {
        String id = "1";
        Movie movie = new Movie(id, "Title", 2000, Arrays.asList("Genre1", "Genre2"));

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        Movie result = movieService.getMovieById(id);

        assertNotNull(result);
        assertEquals(movie, result);
    }

    @Test
    void getMovieById_nonExistingId_throwsMovieNotFoundException() {
        String id = "1";

        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.getMovieById(id));
    }

    @Test
    void addMovie_NewMovie_ReturnsSavedMovie() {
        // Arrange
        Movie movie = new Movie("1", "Title 1", 2000, Arrays.asList("Genre1", "Genre2"));

        when(movieRepository.existsById("1")).thenReturn(false);
        when(movieRepository.save(movie)).thenReturn(movie);

        // Act
        Movie result = movieService.addMovie(movie);

        // Assert
        assertNotNull(result);
        assertEquals(movie, result);
        verify(movieRepository, times(1)).existsById("1");
        verify(movieRepository, times(1)).save(movie);
    }
/* 
    @Test
    void addMovie_ExistingMovie_ReturnsUpdatedMovie() {
        // Arrange
        Movie existingMovie = new Movie("1", "Title 1", 2000, Arrays.asList("Genre1", "Genre2"));
        Movie updatedMovie = new Movie("1", "Updated Title", 2005, Arrays.asList("Genre3"));

        when(movieRepository.existsById("1")).thenReturn(true);
        when(movieRepository.save(updatedMovie)).thenReturn(updatedMovie);

        // Act
        Movie result = movieService.addMovie(updatedMovie);

        // Assert
        assertNotNull(result);
        assertEquals(updatedMovie, result);
        verify(movieRepository, times(1)).existsById("1");
        verify(movieRepository, times(1)).save(updatedMovie);
    }
*/
    @Test
    void updateMovie_ExistingMovie_ReturnsUpdatedMovie() {
        // Arrange
        String id = "2"; // Use a different ID for testing
        Movie movie = new Movie(id, "Title 1", 2000, Arrays.asList("Genre1", "Genre2"));
        Movie updatedMovie = new Movie(id, "Updated Title", 2005, Arrays.asList("Genre3"));

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(movieRepository.save(updatedMovie)).thenReturn(updatedMovie);

        // Act
        Movie result = movieService.updateMovie(id, updatedMovie);

        // Assert
        assertNotNull(result);
        assertEquals(updatedMovie, result);
        verify(movieRepository, times(1)).findById(id);
        verify(movieRepository, times(1)).save(updatedMovie);
    }

    @Test
    void updateMovie_NonExistingMovie_ThrowsMovieNotFoundException() {
        // Arrange
        String id = "3"; // Use a different ID for testing
        Movie updatedMovie = new Movie(id, "Updated Title", 2005, Arrays.asList("Genre3"));

        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MovieNotFoundException.class, () -> movieService.updateMovie(id, updatedMovie));
        verify(movieRepository, times(1)).findById(id);
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    void deleteMovie_ExistingMovie_DeletesMovie() {
        // Arrange
        String id = "4"; // Use a different ID for testing

        when(movieRepository.existsById(id)).thenReturn(true);

        // Act
        movieService.deleteMovie(id);

        // Assert
        verify(movieRepository, times(1)).existsById(id);
        verify(movieRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteMovie_NonExistingMovie_ThrowsMovieNotFoundException() {
        // Arrange
        String id = "5"; // Use a different ID for testing

        when(movieRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(MovieNotFoundException.class, () -> movieService.deleteMovie(id));
        verify(movieRepository, times(1)).existsById(id);
        verify(movieRepository, never()).deleteById(id);
    }

    @Test
    void getMoviesByYearAndGenre_WithValidYearAndGenres_ReturnsMovies() {
        // Arrange
        int year = 2000;
        List<String> genres = Arrays.asList("Genre1", "Genre2");
        List<Movie> expectedMovies = Arrays.asList(
            new Movie("1", "Movie 1", year, Arrays.asList("Genre1", "Genre2")),
            new Movie("2", "Movie 2", year, Arrays.asList("Genre1"))
        );

        when(movieRepository.findByYearAndGenresContaining(year, genres)).thenReturn(expectedMovies);

        // Act
        List<Movie> result = movieService.getMoviesByYearAndGenre(year, genres);

        // Assert
        assertEquals(expectedMovies, result);
        verify(movieRepository, times(1)).findByYearAndGenresContaining(year, genres);
    }

    @Test
    void getMoviesByYearAndGenre_WithInvalidYearOrEmptyGenres_ReturnsEmptyList() {
        // Arrange
        int year = 0; // Invalid year
        List<String> genres = Arrays.asList("Genre1", "Genre2");

        // Act
        List<Movie> result = movieService.getMoviesByYearAndGenre(year, genres);

        // Assert
        assertTrue(result.isEmpty());
        verify(movieRepository, never()).findByYearAndGenresContaining(anyInt(), anyList());
    }

    @Test
    void getMoviesByYear_WithValidYear_ReturnsMovies() {
        // Arrange
        int year = 2000;
        List<Movie> expectedMovies = Arrays.asList(
            new Movie("1", "Movie 1", year, Arrays.asList("Genre1", "Genre2")),
            new Movie("2", "Movie 2", year, Arrays.asList("Genre1"))
        );

        when(movieRepository.findByYear(year)).thenReturn(expectedMovies);

        // Act
        List<Movie> result = movieService.getMoviesByYear(year);

        // Assert
        assertEquals(expectedMovies, result);
        verify(movieRepository, times(1)).findByYear(year);
    }

    @Test
    void getMoviesByYear_WithInvalidYear_ReturnsEmptyList() {
        // Arrange
        int invalidYear = -1; // Invalid year
    
        // Act
        List<Movie> result = movieService.getMoviesByYear(invalidYear);
    
        // Assert
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void getMoviesByGenre_WithValidGenres_ReturnsMovies() {
        // Arrange
        List<String> genres = Arrays.asList("Genre1", "Genre2");
        List<Movie> expectedMovies = Arrays.asList(
            new Movie("1", "Movie 1", 2000, Arrays.asList("Genre1", "Genre2")),
            new Movie("2", "Movie 2", 2005, Arrays.asList("Genre1"))
        );

        when(movieRepository.findByGenresContaining(genres)).thenReturn(expectedMovies);

        // Act
        List<Movie> result = movieService.getMoviesByGenre(genres);

        // Assert
        assertEquals(expectedMovies, result);
        verify(movieRepository, times(1)).findByGenresContaining(genres);
    }

    @Test
    void getMoviesByGenre_WithEmptyGenres_ReturnsEmptyList() {
        // Arrange
        List<String> genres = Collections.emptyList();

        // Act
        List<Movie> result = movieService.getMoviesByGenre(genres);

        // Assert
        assertTrue(result.isEmpty());
        verify(movieRepository, never()).findByGenresContaining(genres);
    }

    @Test
    void getMoviesByGenre_WithNullGenres_ReturnsEmptyList() {
        // Arrange
        List<String> genres = null;

        // Act
        List<Movie> result = movieService.getMoviesByGenre(genres);

        // Assert
        assertTrue(result.isEmpty());
        verify(movieRepository, never()).findByGenresContaining(genres);
    }

    @Test
    void patchMovie_ValidIdAndValidMovie_ReturnsPatchedMovie() {
        // Arrange
        String id = "1"; // Existing movie ID
        Movie existingMovie = new Movie("1", "Movie 1", 2000, Arrays.asList("Genre1", "Genre2"));
        existingMovie.setId(id);
        existingMovie.setTitle("Existing Movie");
        existingMovie.setYear(2022);
        existingMovie.setGenres(Arrays.asList("Action", "Adventure"));
        Movie patchedMovie = new Movie("2", "Movie 2", 2005, Arrays.asList("Genre1"));
        patchedMovie.setTitle("Patched Movie");
        patchedMovie.setYear(2023);
        patchedMovie.setGenres(Arrays.asList("Action", "Adventure", "Drama"));
        
        when(movieRepository.findById(id)).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));
    
        // Act
        Movie result = movieService.patchMovie(id, patchedMovie);
    
        // Assert
        assertEquals("Patched Movie", result.getTitle());
        assertEquals(2023, result.getYear());
        assertEquals(Arrays.asList("Action", "Adventure", "Drama"), result.getGenres());
        verify(movieRepository).findById(id);
        verify(movieRepository).save(existingMovie);
    }    
     
    @Test
    void testPatchMovie_WhenNotNull() {
        // Given
        String id = "1";
        String originaltitle = "original_title";
        int originalyear = 2000;
        List<String> originalgenres = Arrays.asList("Genre1", "Genre2");
        
        Movie existingMovie = new Movie(id, originaltitle, originalyear, originalgenres);
        
        Movie newMovie = new Movie(null, null, null, null);
        when(movieRepository.findById(id)).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(existingMovie)).thenReturn(existingMovie);
    
        // When
        Movie result = movieService.patchMovie(id, newMovie);
    
        // Then
        assertEquals(id, result.getId());
        assertEquals(originaltitle, result.getTitle());
        assertEquals(originalyear, result.getYear());
        assertEquals(originalgenres, result.getGenres());
        verify(movieRepository).findById(id);
        verify(movieRepository).save(existingMovie);
    }

    @Test
    void addMovie_WhenMovieExists_ShouldUpdateMovie() {
        // Arrange
        Movie movie = new Movie("1", "Inception", 2010, List.of("Action", "Thriller"));
        when(movieRepository.existsById("1")).thenReturn(true);
        when(movieRepository.findById("1")).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);

        // Act
        Movie result = movieService.addMovie(movie);

        // Assert
        verify(movieRepository, times(1)).existsById("1");
        verify(movieRepository, times(1)).save(movie);
        assertNotNull(result);
        assertEquals("Inception", result.getTitle());
    }



    @Test
    void addMovie_WhenMovieDoesNotExist_ShouldSaveNewMovie() {
        // Arrange
        Movie movie = new Movie("2", "Avatar", 2009, List.of("Action", "Adventure"));
        when(movieRepository.existsById("2")).thenReturn(false);
        when(movieRepository.save(movie)).thenReturn(movie);

        // Act
        Movie savedMovie = movieService.addMovie(movie);

        // Assert
        verify(movieRepository, times(1)).existsById("2");
        verify(movieRepository, times(1)).save(movie);
        assertNotNull(savedMovie);
        assertEquals("Avatar", savedMovie.getTitle());
    }
}
