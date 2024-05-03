package com.example.cse364project.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testGettersAndSetters() {
        String id = "123";
        char gender = 'M';
        int age = 25;
        int occupation = 1;
        String postal = "12345";

        User user = new User(id, gender, age, occupation, postal);

        Assertions.assertEquals(id, user.getId());
        Assertions.assertEquals(gender, user.getGender());
        Assertions.assertEquals(age, user.getAge());
        Assertions.assertEquals(occupation, user.getOccupation());
        Assertions.assertEquals(postal, user.getPostal());

        String newId = "456";
        char newGender = 'F';
        int newAge = 30;
        int newOccupation = 2;
        String newPostal = "67890";

        user.setId(newId);
        user.setGender(newGender);
        user.setAge(newAge);
        user.setOccupation(newOccupation);
        user.setPostal(newPostal);

        Assertions.assertEquals(newId, user.getId());
        Assertions.assertEquals(newGender, user.getGender());
        Assertions.assertEquals(newAge, user.getAge());
        Assertions.assertEquals(newOccupation, user.getOccupation());
        Assertions.assertEquals(newPostal, user.getPostal());
    }

    @Test
    public void testEqualsAndHashCode() {
        String id = "123";
        char gender = 'M';
        int age = 25;
        int occupation = 1;
        String postal = "12345";

        User user1 = new User(id, gender, age, occupation, postal);
        User user2 = new User(id, gender, age, occupation, postal);

        Assertions.assertEquals(user1, user1);
        Assertions.assertNotEquals(user1, null);

        Assertions.assertEquals(user1, user2);
        Assertions.assertEquals(user1.hashCode(), user2.hashCode());

        User differentId = new User("456", gender, age, occupation, postal);
        assertFalse(user1.equals(differentId));

        User differentGender = new User(id, 'W', age, occupation, postal);
        assertFalse(user1.equals(differentGender));

        User differentAge = new User(id, gender, 35, occupation, postal);
        assertFalse(user1.equals(differentAge));

        User differentOccupation = new User(id, gender, age, 2, postal);
        assertFalse(user1.equals(differentOccupation));

        User differentPostal = new User(id, gender, age, occupation, "54321");
        assertFalse(user1.equals(differentPostal));

        assertFalse(user1.equals(null));

        Object otherObject = new Object(); 

        assertFalse(user1.equals(otherObject));
    }

    @Test
    void testToString() {
        String id = "123";
        char gender = 'M';
        int age = 25;
        int occupation = 1;
        String postal = "12345";

        User user = new User(id, gender, age, occupation, postal);
        String expectedString = "User{" + '\'' +
        "id=" + id +
        ", gender=" + gender +
        ", age=" + age +
        ", occupation=" + occupation +
        ", postal='" + postal + '\'' +
        '}';
        assertEquals(expectedString, user.toString());
    }
}