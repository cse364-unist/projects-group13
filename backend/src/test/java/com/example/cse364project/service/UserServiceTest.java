package com.example.cse364project.service;

import com.example.cse364project.domain.User;
import com.example.cse364project.exception.UserNotFoundException;
import com.example.cse364project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        // 모의 객체 초기화 (이미 @Mock, @InjectMocks로 처리됨)
    }

    @Test
    void getAllUsersTest() {
        User user1 = new User("1", 'M', 25, 1, "12345");
        User user2 = new User("2", 'F', 30, 2, "67890");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userRepository).findAll();
    }

    @Test
    void getUserByIdTest() {
        User user = new User("1", 'M', 25, 1, "12345");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById("1");

        assertNotNull(foundUser);
        assertEquals("1", foundUser.getId());
        verify(userRepository).findById("1");
    }

    @Test
    void getUserByIdNotFoundTest() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById("1");
        });
    }

    @Test
    void addUserTest() {
        User user = new User("1", 'M', 25, 1, "12345");
        when(userRepository.existsById("1")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.addUser(user);

        assertNotNull(savedUser);
        verify(userRepository).save(user);
    }

    @Test
    void updateUserTest() {
        User oldUser = new User("1", 'M', 30, 2, "67890");
        User newUser = new User("1", 'M', 25, 1, "12345");

        when(userRepository.findById("1")).thenReturn(Optional.of(oldUser));
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User updatedUser = userService.updateUser("1", newUser);

        assertNotNull(updatedUser);
        assertEquals(25, updatedUser.getAge());
        verify(userRepository).save(oldUser);
    }

    @Test
    void deleteUserTest() {
        doNothing().when(userRepository).deleteById("1");

        userService.deleteUser("1");

        verify(userRepository).deleteById("1");
    }

    @Test
    void getUsersByDynamicQueryTest() {
        User user = new User("1", 'M', 25, 1, "12345");
        when(userRepository.findByDynamicQuery('M', 25, 1, "12345"))
                .thenReturn(Optional.of(Collections.singletonList(user)));

        List<User> users = userService.getUsersByDynamicQuery('M', 25, 1, "12345");

        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        verify(userRepository).findByDynamicQuery('M', 25, 1, "12345");
    }

    @Test
    void patchUserTest() {
        User oldUser = new User("1", 'M', 30, 2, "67890");
        User newUser = new User("1", null, null, 1, null);

        when(userRepository.findById("1")).thenReturn(Optional.of(oldUser));
        when(userRepository.save(any(User.class))).thenReturn(oldUser);

        User patchedUser = userService.patchUser("1", newUser);

        assertNotNull(patchedUser);
        assertEquals(1, patchedUser.getOccupation());
        verify(userRepository).save(oldUser);
    }

    @Test
    void addUserExistingUserTest() {
        User existingUser = new User("1", 'M', 25, 1, "12345");
        User newUser = new User("1", 'M', 30, 1, "67890");
        when(userRepository.existsById("1")).thenReturn(true);
        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User updatedUser = userService.addUser(newUser);

        assertNotNull(updatedUser);
        assertEquals(30, updatedUser.getAge());
        assertEquals("67890", updatedUser.getPostal());
        verify(userRepository).save(existingUser);
        verify(userRepository).findById("1");
    }

    @Test
    void updateUserNotFoundTest() {
        User newUser = new User("1", 'M', 25, 1, "12345");
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser("1", newUser));
    }

    @Test
    void patchUserNotFoundTest() {
        User newUser = new User("1", null, null, 1, null);
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.patchUser("1", newUser));
    }

    @Test
    public void deleteUserSuccessfully() {
        doNothing().when(userRepository).deleteById("1");
        userService.deleteUser("1");
        verify(userRepository).deleteById("1");
    }

    @Test
    public void getUsersByDynamicQueryThrowsExceptionWhenNotFound() {
        when(userRepository.findByDynamicQuery('M', 25, 1, "12345")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUsersByDynamicQuery('M', 25, 1, "12345"));
    }

    @Test
    public void getUsersByDynamicQueryReturnsUsersWhenFound() {
        User user = new User("1", 'M', 25, 1, "12345");
        when(userRepository.findByDynamicQuery('M', 25, 1, "12345")).thenReturn(Optional.of(Collections.singletonList(user)));
        List<User> result = userService.getUsersByDynamicQuery('M', 25, 1, "12345");
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    public void getUsersByGenderAndAgeAndOccupationAndPostalThrowsExceptionWhenNotFound() {
        when(userRepository.findByGenderAndAgeAndOccupationAndPostal('F', 30, 2, "67890")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUsersByGenderAndAgeAndOccupationAndPostal('F', 30, 2, "67890"));
    }

    @Test
    public void getUsersByGenderAndAgeAndOccupationAndPostalReturnsUsersWhenFound() {
        User user = new User("2", 'F', 30, 2, "67890");
        when(userRepository.findByGenderAndAgeAndOccupationAndPostal('F', 30, 2, "67890")).thenReturn(Optional.of(Collections.singletonList(user)));
        List<User> result = userService.getUsersByGenderAndAgeAndOccupationAndPostal('F', 30, 2, "67890");
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("2", result.get(0).getId());
    }

    @Test
    void patchUserPartiallyUpdatesUser() {
        User existingUser = new User("1", 'M', 30, 2, "67890");
        User patchData = new User("1", 'F', null, 3, "54321");

        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.patchUser("1", patchData);

        assertNotNull(updatedUser);
        assertEquals('F', updatedUser.getGender());
        assertEquals(30, updatedUser.getAge());
        assertEquals(3, updatedUser.getOccupation());
        assertEquals("54321", updatedUser.getPostal());
        verify(userRepository).save(existingUser);
    }

    @Test
    void patchUserFullUpdateTest() {
        User originalUser = new User("1", 'M', 25, 1, "12345");
        User updates = new User("1", 'F', 30, 2, "67890");

        when(userRepository.findById("1")).thenReturn(Optional.of(originalUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.patchUser("1", updates);

        assertNotNull(updatedUser);
        assertEquals(30, updatedUser.getAge());
        assertEquals('F', updatedUser.getGender());
        assertEquals(2, updatedUser.getOccupation());
        assertEquals("67890", updatedUser.getPostal());
    }

    @Test
    void patchUserPartialUpdateTest() {
        User originalUser = new User("1", 'M', 25, 1, "12345");
        User updates = new User("1", null, 30, null, null);

        when(userRepository.findById("1")).thenReturn(Optional.of(originalUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.patchUser("1", updates);

        assertNotNull(updatedUser);
        assertEquals(30, updatedUser.getAge());
        assertEquals('M', updatedUser.getGender());
        assertEquals(1, updatedUser.getOccupation());
        assertEquals("12345", updatedUser.getPostal());
    }

    @Test
    void patchUserNoUpdateTest() {
        User originalUser = new User("1", 'M', 25, 1, "12345");
        User updates = new User("1", null, null, null, null);

        when(userRepository.findById("1")).thenReturn(Optional.of(originalUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.patchUser("1", updates);

        assertNotNull(updatedUser);
        assertEquals(25, updatedUser.getAge());
        assertEquals('M', updatedUser.getGender());
        assertEquals(1, updatedUser.getOccupation());
        assertEquals("12345", updatedUser.getPostal());
    }

    @Test
    void patchUserThrowsWhenNotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.patchUser("1", new User("1", 'M', 30, 2, "67890")));
    }


}
