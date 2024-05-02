package com.example.cse364project.domain;

import com.example.cse364project.domain.ActorRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActorRequestTest {

    @Test
    public void testActorRequestSetter() {
        ActorRequest request = new ActorRequest();

        double[] genre = {0.1, 0.2, 0.3};
        String[] supporters = {"Actor1", "Actor2", "Actor3"};
        String plot = "Sample plot";
        int synerge = 1;

        request.setGenre(genre);
        request.setSupporter(supporters);
        request.setPlot(plot);
        request.setSynergy(synerge);

        assertArrayEquals(genre, request.getGenre());
        assertArrayEquals(supporters, request.getSupporter());
        assertEquals(plot, request.getPlot());
        assertEquals(synerge, request.getSynergy());
    }
}
