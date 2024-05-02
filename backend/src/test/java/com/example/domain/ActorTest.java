package com.example.domain;

import com.example.cse364project.domain.Actor;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActorTest {

    @Test
    public void testActorConstructor() {
        String name = "Jinwoo";
        List<String> titles = Arrays.asList("Movie1", "Movie2", "Movie3");
        int[] count = {3, 2, 1};
        double[] genreScore = {0.1, 0.2, 0.3};

        Actor actor = new Actor(name, titles, count, genreScore);

        assertEquals(name, actor.getName());
        assertEquals(titles, actor.getTitles());
        assertEquals(count.length, actor.getCount().length);
        assertEquals(genreScore.length, actor.getGenre().length);
        for (int i = 0; i < count.length; i++) {
            assertEquals(count[i], actor.getCount()[i]);
        }
        for (int i = 0; i < genreScore.length; i++) {
            assertEquals(genreScore[i], actor.getGenre()[i]);
        }
    }

    @Test
    public void testActorSetter() {
        Actor actor = new Actor("", null, null, null);

        String name = "Woojin";
        List<String> titles = Arrays.asList("Movie4", "Movie5");
        int[] count = {2, 1};
        double[] genreScore = {0.4, 0.5};

        actor.setName(name);
        actor.setTitle(titles);
        actor.setCount(count);
        actor.setName(genreScore);

        assertEquals(name, actor.getName());
        assertEquals(titles, actor.getTitles());
        assertEquals(count.length, actor.getCount().length);
        assertEquals(genreScore.length, actor.getGenre().length);
        for (int i = 0; i < count.length; i++) {
            assertEquals(count[i], actor.getCount()[i]);
        }
        for (int i = 0; i < genreScore.length; i++) {
            assertEquals(genreScore[i], actor.getGenre()[i]);
        }
    }
}

