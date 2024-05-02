package com.example.cse364project.controller;

import com.example.cse364project.domain.User;
import com.example.cse364project.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<User>>> getUsers(
        @RequestParam(required = false) Character gender,
        @RequestParam(required = false) Integer age,
        @RequestParam(required = false) Integer occupation,
        @RequestParam(required = false) String postal) {

        List<User> users;

        if (gender != null && age != null && occupation != null && postal != null)
            users = userService.getUsersByGenderAndAgeAndOccupationAndPostal(gender, age, occupation, postal);
        else if (gender == null && age == null && occupation == null && postal == null)
            users = userService.getAllUsers();
        else
            users = userService.getUsersByDynamicQuery(gender, age, occupation, postal);

        List<EntityModel<User>> userModels = new ArrayList<>();
        for (User user : users) {
            userModels.add(EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel()));
        }

        CollectionModel<EntityModel<User>> collectionModel = CollectionModel.of(userModels,
            linkTo(methodOn(UserController.class).getUsers(gender, age, occupation, postal)).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<User>> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(EntityModel.of(user)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EntityModel<User>> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<User>> updateUser(@PathVariable String id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return updatedUser != null ? ResponseEntity.ok(EntityModel.of(updatedUser))
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<User>> patchUser(@PathVariable String id, @RequestBody User user) {
        User patchedUser = userService.patchUser(id, user);
        return patchedUser != null ? ResponseEntity.ok(EntityModel.of(patchedUser))
                : ResponseEntity.notFound().build();
    }
}
