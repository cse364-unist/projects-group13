package com.example.cse364project.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.cse364project.dto.GenreRate;
import com.example.cse364project.service.GFAService;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(GFAController.class)
public class GFAControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GFAService gfaService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new GFAController(gfaService)).build();
    }

    @Test
    void testGetGenreFrequencyWithRatings() throws Exception {
        // Mock the service response
        List<GenreRate> genreRates = Arrays.asList(new GenreRate(Arrays.asList("Action"), 3.0, 4), new GenreRate(Arrays.asList("Comedy"), 2.5, 6));
        when(gfaService.getGenreFrequencyWithRatings()).thenReturn(genreRates);

        // Perform the GET request
        mockMvc.perform(get("/gfa")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testGetGenreFrequencyWithRatingsIncludingGenre() throws Exception {
        // Mock the service response
        String genre = "Action";
        List<GenreRate> genreRates = Arrays.asList(new GenreRate(Arrays.asList("Action"), 3.0, 4), new GenreRate(Arrays.asList("Comedy"), 2.5, 6));
        when(gfaService.getGenreFrequencyWithRatingsIncludingGenre(genre)).thenReturn(genreRates);

        // Perform the GET request
        mockMvc.perform(get("/gfa/genre/{genre}", genre)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testGetGenreFrequencyWithRatingsIncludingYear() throws Exception {
        // Mock the service response
        int year = 2022;
        List<GenreRate> genreRates = Arrays.asList(new GenreRate(Arrays.asList("Action"), 3.0, 4), new GenreRate(Arrays.asList("Comedy"), 2.5, 6));
        when(gfaService.getGenreFrequencyWithRatingsIncludingYear(year)).thenReturn(genreRates);

        // Perform the GET request
        mockMvc.perform(get("/gfa/{year}", year)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}