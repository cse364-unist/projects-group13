package com.example.cse364project.controller;


import com.example.cse364project.domain.User;
import com.example.cse364project.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetUser() throws Exception {
        List<User> users = Arrays.asList(
                new User("1", 'M', 30, 1, "12345"),
                new User("2", 'F', 40, 2, "67890")
        );

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[1].id").value("2"));
    }

    @Test
    void testGetUser2() throws Exception {

        User user1 = new User("1", 'M', 30, 1, "12345");

        when(userService.getUsersByGenderAndAgeAndOccupationAndPostal('M', 30, 1, "12345")).thenReturn(Arrays.asList(user1));

        mockMvc.perform(get("/users")
                    .param("gender", "M")
                    .param("age", "30")
                    .param("occupation", "1")
                    .param("postal", "12345"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value("1"));
    }

    @Test
    void testGetUser3() throws Exception {
        User user1 = new User("1", 'M', 30, 1, "12345");

        when(userService.getUsersByDynamicQuery(null, 30, null, null)).thenReturn(Arrays.asList(user1));

        mockMvc.perform(get("/users")
                    .param("age", "30"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value("1"));
    }

    @Test
    void testGetUser4() throws Exception {
        User user1 = new User("1", 'M', 30, 1, "12345");

        when(userService.getUsersByDynamicQuery('M', null, null, null)).thenReturn(Arrays.asList(user1));

        mockMvc.perform(get("/users")
                    .param("gender", "M"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value("1"));
    }

    @Test
    void testGetUser5() throws Exception {
        User user1 = new User("1", 'M', 30, 1, "12345");

        when(userService.getUsersByDynamicQuery(null, null, null, "12345")).thenReturn(Arrays.asList(user1));

        mockMvc.perform(get("/users")
                    .param("postal", "12345"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value("1"));
    }

    @Test
    void testGetUser6() throws Exception {
        User user1 = new User("1", 'M', 30, 1, "12345");

        when(userService.getUsersByDynamicQuery(null, null, 1, null)).thenReturn(Arrays.asList(user1));

        mockMvc.perform(get("/users")
                    .param("occupation", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value("1"));
    }

    @Test
    void testGetUser7() throws Exception {
        User user1 = new User("1", 'M', 30, 1, "12345");

        when(userService.getUsersByDynamicQuery('M', 30, 1, null)).thenReturn(Arrays.asList(user1));

        mockMvc.perform(get("/users")
                    .param("gender", "M")
                    .param("age", "30")
                    .param("occupation", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value("1"));
    }

    @Test
    void testGetUserById() throws Exception {
        User user = new User("1", 'M', 30, 1, "12345");

        when(userService.getUserById("1")).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.gender").value("M"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.occupation").value(1))
                .andExpect(jsonPath("$.postal").value("12345"));
    }

    @Test
    void testGetUserById_userNotFound() throws Exception {
        when(userService.getUserById("1")).thenReturn(null);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateUser() throws Exception {
        User updatedUser = new User("1", 'F', 40, 2, "67890");

        when(userService.updateUser(eq("1"), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/users/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(updatedUser)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.gender").value("F"))
            .andExpect(jsonPath("$.age").value(40))
            .andExpect(jsonPath("$.occupation").value(2))
            .andExpect(jsonPath("$.postal").value("67890"));
    }

    @Test
    void testUpdateUser_userNotFound() throws Exception {
        User user = new User("1", 'M', 30, 1, "12345");

        when(userService.updateUser(eq("1"), any(User.class))).thenReturn(null);

        mockMvc.perform(put("/users/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddUser() throws Exception {
        User user = new User("1", 'M', 30, 1, "12345");
        User newUser = new User("1", 'M', 30, 1, "12345");

        when(userService.addUser(any(User.class))).thenReturn(newUser);

        mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.gender").value("M"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.occupation").value(1))
                .andExpect(jsonPath("$.postal").value("12345"));
    }

    @Test
    void testAddUser2() throws Exception {

        mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPatchUser() throws Exception {
        User patchedUser = new User("1", 'F', 40, 2, "67890");

        when(userService.patchUser(eq("1"), any(User.class))).thenReturn(patchedUser);

        mockMvc.perform(patch("/users/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(patchedUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.gender").value("F"))
                .andExpect(jsonPath("$.age").value(40))
                .andExpect(jsonPath("$.occupation").value(2))
                .andExpect(jsonPath("$.postal").value("67890"));
    }

    @Test
    void testPatchUser2() throws Exception {
        User user = new User("1", 'M', 30, 1, "12345");

        when(userService.patchUser(eq("1"), any(User.class))).thenReturn(null);

        mockMvc.perform(patch("/users/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    void patchUser_userNotFound() throws Exception {
        User user = new User("1", 'M', 30, 1, "12345");

        when(userService.patchUser(eq("1"), any(User.class))).thenReturn(null);

        mockMvc.perform(patch("/users/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUser2() throws Exception {
        doNothing().when(userService).deleteUser("1");

        mockMvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
