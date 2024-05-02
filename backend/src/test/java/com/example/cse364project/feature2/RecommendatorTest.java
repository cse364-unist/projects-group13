package com.example.cse364project.feature2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.cse364project.domain.Actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class RecommendatorTest {

    @Test
    void testRecommend() {
        // Create test data
        List<String> movieList1 = Arrays.asList("Movie1", "Movie2", "Movie3");
        List<String> movieList2 = Arrays.asList("Movie1", "Movie5");
        List<String> movieList3 = Arrays.asList("Movie1");
        List<String> movieList4 = Arrays.asList("Movie1", "Movie2", "Movie5", "Movie10");

        double[] genre = {0.5, 0.3, 0.2};
        List<Actor> supporters = new ArrayList<>();
        supporters.add(new Actor("Supporter1", movieList1, new int[]{1, 2, 3}, new double[]{0.4, 0.2, 0.1}));
        supporters.add(new Actor("Supporter2", movieList2, new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.3}));
        int synergy = 50;
        String plot = "Test plot";
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Actor1", movieList3, new int[]{1, 2, 3}, new double[]{0.6, 0.4, 0.3}));
        actors.add(new Actor("Actor2", movieList4, new int[]{1, 2, 3}, new double[]{0.2, 0.5, 0.1}));
        int maxSize = 2;

        // Create an instance of Recommendator
        Recommendator recommendator = new Recommendator();

        // Call the recommend method
        Set<Actor> recommendedActors = recommendator.recommend(genre, supporters, synergy, plot, actors, maxSize);

        // Assert the result
        Assertions.assertEquals(maxSize, recommendedActors.size());
        // Add more assertions if needed
    }
}