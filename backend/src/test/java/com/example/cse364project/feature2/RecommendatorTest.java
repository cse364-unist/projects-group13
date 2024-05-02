package com.example.cse364project.feature2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.cse364project.domain.Actor;
import com.example.cse364project.feature2.Recommendator;

public class RecommendatorTest {

    @DisplayName("testWithSupporter")
    @Test
    public void testRecommendatorWithSupporter() {
        double[] genre = {0, 0, 0, 0, 1};

        Actor supporter1 = new Actor("actor5", Arrays.asList("Movie5"), new int[]{1, 1, 1, 0, 0}, new double[]{1, 0, 0, 0, 0});
        Actor supporter2 = new Actor("actor5", Arrays.asList("Movie5"), new int[]{0, 0, 0, 1, 1}, new double[]{1, 0, 0, 0, 0});

        List<Actor> supporters = new ArrayList<>(Arrays.asList(supporter1, supporter2));

        int synergy = 0;
        String plot = "";

        Actor actor1 = new Actor("actor1", Arrays.asList("Movie1"), new int[]{1, 0, 0, 0, 0}, new double[]{1, 0, 0, 0, 0});
        Actor actor2 = new Actor("actor2", Arrays.asList("Movie2"), new int[]{0, 1, 0, 0, 0}, new double[]{0, 1, 0, 0, 0});
        Actor actor3 = new Actor("actor3", Arrays.asList("Movie3"), new int[]{0, 0, 1, 0, 0}, new double[]{0, 0, 1, 0, 0});
        Actor actor4 = new Actor("actor4", Arrays.asList("Movie4"), new int[]{0, 0, 0, 1, 0}, new double[]{0, 0, 0, 1, 0});
        Actor actor5 = new Actor("actor5", Arrays.asList("Movie5"), new int[]{0, 0, 0, 0, 1}, new double[]{0, 0, 0, 0, 1});

        List<Actor> actors = new ArrayList<>(Arrays.asList(actor1, actor2, actor3, actor4, actor5));
        int maxSize = 1;

        Recommendator recommedator = new Recommendator();
        Set<Actor> result = recommedator.recommend(genre, supporters, synergy, plot, actors, maxSize);

        assertEquals(new HashSet<Actor>(Arrays.asList(actor5)), result);
    }

    @DisplayName("testWithoutSupporter")
    @Test
    public void testRecommendatorWithoutSupporter() {
        double[] genre = {0, 0, 0, 0, 1};

        List<Actor> supporters = new ArrayList<>();

        int synergy = 0;
        String plot = "";

        Actor actor1 = new Actor("actor1", Arrays.asList("Movie1"), new int[]{1, 0, 0, 0, 0}, new double[]{1, 0, 0, 0, 0});
        Actor actor2 = new Actor("actor2", Arrays.asList("Movie2"), new int[]{0, 1, 0, 0, 0}, new double[]{0, 1, 0, 0, 0});
        Actor actor3 = new Actor("actor3", Arrays.asList("Movie3"), new int[]{0, 0, 1, 0, 0}, new double[]{0, 0, 1, 0, 0});
        Actor actor4 = new Actor("actor4", Arrays.asList("Movie4"), new int[]{0, 0, 0, 1, 0}, new double[]{0, 0, 0, 1, 0});
        Actor actor5 = new Actor("actor5", Arrays.asList("Movie5"), new int[]{0, 0, 0, 0, 1}, new double[]{0, 0, 0, 0, 1});

        List<Actor> actors = new ArrayList<>(Arrays.asList(actor1, actor2, actor3, actor4, actor5));
        int maxSize = 1;

        Recommendator recommedator = new Recommendator();
        Set<Actor> result = recommedator.recommend(genre, supporters, synergy, plot, actors, maxSize);

        assertEquals(new HashSet<Actor>(Arrays.asList(actor5)), result);
    }
}