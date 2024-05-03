package com.example.cse364project.databasetest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.cse364project.LoadDatabase;
import com.example.cse364project.repository.ActorRepository;
import com.example.cse364project.repository.MovieRepository;
import com.example.cse364project.repository.RatingRepository;
import com.example.cse364project.repository.UserRepository;

public class LoadDatabaseTest {
    
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ActorRepository actorRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("testMovieLoad")
    @Test
    void movieLoadTest() throws Exception {
        
        LoadDatabase loadDatabase = new LoadDatabase(movieRepository, ratingRepository, userRepository, actorRepository);

        assertDoesNotThrow(() -> {
            loadDatabase.run("args");
        });
    }
}