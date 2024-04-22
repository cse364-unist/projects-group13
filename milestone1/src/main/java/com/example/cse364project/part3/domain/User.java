package com.example.cse364project.part3.domain;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private char gender;
    private int age;
    private int occupation;
    private String postal;

    public User(String id, char gender, int age, int occupation, String postal) {
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
        this.postal = postal;
    }

    public String getId() {
        return id;
    }

    public char getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public int getOccupation() {
        return occupation;
    }

    public String getPostal() {
        return postal;
    }


    public void setId(String userId) {
        this.id = userId;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setOccupation(int occupation) {
        this.occupation = occupation;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    @Override
    public String toString() {
        return "User{" + '\'' +
                "id=" + id +
                ", gender=" + gender +
                ", age=" + age +
                ", occupation=" + occupation +
                ", postal='" + postal + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                gender == user.gender &&
                age == user.age &&
                occupation == user.occupation &&
                Objects.equals(postal, user.postal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gender, age, occupation, postal);
    }
}