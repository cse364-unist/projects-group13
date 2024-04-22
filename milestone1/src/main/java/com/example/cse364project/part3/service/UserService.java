package com.example.cse364project.part3.service;

import com.example.cse364project.part3.domain.User;
import com.example.cse364project.part3.exception.UserNotFoundException;
import com.example.cse364project.part3.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Could not find ID " + id + "."));
    }

    public User addUser(User user) {
        if (userRepository.existsById(user.getId()))
            return updateUser(user.getId(), user);
        return userRepository.save(user);
    }

    public User updateUser(String id, User user) {
        User updatedUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Could not find ID" + id + "."));
        updatedUser.setAge(user.getAge());
        updatedUser.setGender(user.getGender());
        updatedUser.setOccupation(user.getOccupation());
        updatedUser.setPostal(user.getPostal());
        return userRepository.save(updatedUser);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public List<User> getUsersByDynamicQuery(Character gender, Integer age, Integer occupation, String postal) {
        return userRepository.findByDynamicQuery(gender, age, occupation, postal).orElseThrow(
                () -> new UserNotFoundException("Cannot find entry wihtin this condition: gender=" + gender + ", age="
                        + age.toString() + ", occupation=" + occupation.toString() + ", postal=" + postal + "."));
    }

    public List<User> getUsersByGenderAndAgeAndOccupationAndPostal(Character gender, Integer age, Integer occupation,
            String postal) {
        return userRepository.findByGenderAndAgeAndOccupationAndPostal(gender, age, occupation, postal)
                .orElseThrow(() -> new UserNotFoundException("Cannot find entry wihtin this condition: gender=" + gender
                        + ", age="
                        + age.toString() + ", occupation=" + occupation.toString() + ", postal=" + postal + "."));
    }
}