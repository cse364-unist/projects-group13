package com.example.cse364project.domain;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RatingTest {

    @Test
    public void testRatingConstructorAndGetters() {
        String movieId = "123";
        String userId = "456";
        int rate = 5;
        String timestamp = "2022-01-01";

        Rating rating = new Rating(movieId, userId, rate, timestamp);

        Assertions.assertEquals(rating.getId(), rating.getId());
        Assertions.assertEquals(movieId, rating.getMovieId());
        Assertions.assertEquals(userId, rating.getUserId());
        Assertions.assertEquals(rate, rating.getRate());
        Assertions.assertEquals(timestamp, rating.getTimestamp());
    }

    @Test
    public void testRatingSetters() {
        Rating rating = new Rating("123", "456", 5, "2022-01-01");

        String newId = "id";
        String newMovieId = "789";
        String newUserId = "012";
        int newRate = 4;
        String newTimestamp = "2022-02-01";

        rating.setId(newId);
        rating.setMovieId(newMovieId);
        rating.setUserId(newUserId);
        rating.setRate(newRate);
        rating.setTimestamp(newTimestamp);

        Assertions.assertEquals(newId, rating.getId());
        Assertions.assertEquals(newMovieId, rating.getMovieId());
        Assertions.assertEquals(newUserId, rating.getUserId());
        Assertions.assertEquals(newRate, rating.getRate());
        Assertions.assertEquals(newTimestamp, rating.getTimestamp());
    }

    @Test
    public void testRatingEqualsAndHashCode() {
        Rating rating1 = new Rating("123", "456", 5, "2022-01-01");
        Rating rating2 = new Rating("123", "456", 5, "2022-01-01");
        Rating rating3 = new Rating("789", "012", 4, "2022-02-01");
        Rating rating4 = new Rating("789", "012", 2, "2022-02-01");
        Rating rating5 = new Rating("789", "012", 4, "2022-03-01");
        Rating rating6 = new Rating("123", "456", 5, "2022-01-01");
        Rating rating7 = new Rating("432", "456", 5, "2022-01-01");
        Rating rating8 = new Rating("432", "23", 5, "2022-01-01");
        rating6.setId("42");

        Assertions.assertEquals(rating2, rating2);
        Assertions.assertNotEquals(rating1, null);
        Assertions.assertNotEquals(rating1, new ArrayList<>());

        Assertions.assertEquals(rating1, rating2);
        Assertions.assertNotEquals(rating1, rating3); // different movieId, userId, rate, timestamp 
        Assertions.assertNotEquals(rating4, rating3); // different rate
        Assertions.assertNotEquals(rating5, rating3); // different timestamp
        Assertions.assertNotEquals(rating6, rating1); // different id
        Assertions.assertNotEquals(rating7, rating1); // different movieId
        Assertions.assertNotEquals(rating8, rating1); // different userId

        Assertions.assertEquals(rating1.hashCode(), rating2.hashCode());
        Assertions.assertNotEquals(rating1.hashCode(), rating3.hashCode());
    }

    @Test
    public void testToString() {
        Rating rating = new Rating("123", "456", 5, "2022-01-01");
        Assertions.assertEquals(rating.toString(), "Rating{" + "id='" + rating.getId() + '\'' + ", movieId=" + "123" + ", userId=" + "456" + ", rate=" + 5 + ", timestamp=" + "2022-01-01" + '}');
    }

    @Test
    public void testEquals() {
        Rating rating1 = new Rating("123", "456", 5, "2022-01-01");
        Rating rating2 = new Rating("123", "456", 5, "2022-01-01");
        Assertions.assertTrue(rating1.equals(rating1));
        Assertions.assertTrue(rating1.equals(rating2));
        Assertions.assertFalse(rating1.equals(null));
    }
}