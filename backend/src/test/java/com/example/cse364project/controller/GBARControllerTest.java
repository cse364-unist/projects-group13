package com.example.cse364project.controller;

import com.example.cse364project.domain.Actor;
import com.example.cse364project.feature2.Recommendator;
import com.example.cse364project.service.GBARService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class GBARControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GBARService gbarService;

    @Mock
    private Recommendator recommendator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        GBARController gbarController = new GBARController(gbarService, recommendator);
        mockMvc = MockMvcBuilders.standaloneSetup(gbarController).build();
    }

    @Test
    public void testGetOneActor1() throws Exception {
        List<String> movies = Arrays.asList("Airplane!", "Airplane II: The Sequel");
        Actor actor = new Actor("Robert Hays", movies, new int[]{2, 0, 0, 2}, new double[]{0.1, 0.0, 0.0, 0.3});
        when(gbarService.getActorByName(anyString())).thenReturn(actor);

        mockMvc.perform(MockMvcRequestBuilders.get("/gbar/find")
                .param("name", "Robert Hays")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Robert Hays"));
    }

    @Test
    public void testGetOneActor2() throws Exception {
        List<String> movies = Arrays.asList("Animal House", "The Blues Brothers");
        Actor actor = new Actor("John Belushi", movies, new int[]{2, 0, 0, 2}, new double[]{0.1, 0.0, 0.0, 0.3});
        when(gbarService.getActorByName(anyString())).thenReturn(actor);

        mockMvc.perform(MockMvcRequestBuilders.get("/gbar/find")
                .param("name", "Robert Hays")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Belushi"));
    }

    @Test
    public void testRecommendActor1() throws Exception {
        List<String> movies1 = Arrays.asList("Airplane!", "Airplane II: The Sequel");
        List<String> movies2 = Arrays.asList("Animal House", "The Blues Brothers");
        Actor actor1 = new Actor("Robert Hays", movies1, new int[]{2, 0, 0, 2}, new double[]{0.1, 0.0, 0.0, 0.3});
        Actor actor2 = new Actor("John Belushi", movies2, new int[]{2, 0, 0, 2}, new double[]{0.1, 0.0, 0.0, 0.3});
        Set<Actor> actors = new HashSet<>(Arrays.asList(actor1, actor2));
        when(gbarService.getActorByName("Robert Hays")).thenReturn(actor1);
        when(gbarService.getActorByName("John Belushi")).thenReturn(actor2);
        when(recommendator.recommend((double[]) any(), anyList(), anyInt(), anyString(), anyList(), anyInt())).thenReturn(actors);

        mockMvc.perform(MockMvcRequestBuilders.post("/gbar/recommend")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"genre\": [27.6, 7.4, 0, 52, 0, 6.6, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],\n" +
                        "  \"synergy\": 20,\n" +
                        "  \"supporter\": [\"Robert Hays\", \"John Belushi\"],\n" +
                        "  \"plot\": \"plot is here\"\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testRecommendActor2() throws Exception {
        List<String> movies1 = Arrays.asList("Howl's Moving Castle", "Spirited Away");
        List<String> movies2 = Arrays.asList("The World of Us", "The Table");
        Actor actor1 = new Actor("Miazaki Hayao", movies1, new int[]{2, 0, 0, 2}, new double[]{0.1, 0.0, 0.0, 0.3});
        Actor actor2 = new Actor("Jo Sumin", movies2, new int[]{2, 0, 0, 2}, new double[]{0.1, 0.0, 0.0, 0.3});
        Set<Actor> actors = new HashSet<>(Arrays.asList(actor1, actor2));
        when(gbarService.getActorByName("Miazaki Hayao")).thenReturn(actor1);
        when(gbarService.getActorByName("Jo Sumin")).thenReturn(actor2);
        when(recommendator.recommend((double[]) any(), anyList(), anyInt(), anyString(), anyList(), anyInt())).thenReturn(actors);

        mockMvc.perform(MockMvcRequestBuilders.post("/gbar/recommend")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"genre\": [27.6, 7.4, 0, 52, 0, 6.6, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],\n" +
                        "  \"synergy\": 20,\n" +
                        "  \"supporter\": [\"Miazaki Hayao\", \"Jo Sumin\"],\n" +
                        "  \"plot\": \"plot is here\"\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}