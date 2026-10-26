package org.rosetta.services;

import org.rosetta.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.rosetta.repositories.UserRepository;
import org.rosetta.repositories.FakeUserRepository;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        UserRepository userRepository = new FakeUserRepository();
        userService = new UserService(userRepository);
    }

    @Test
    void register_newEmail_returnsSavedUser() {
        User registered = userService.register(new User("Mohamed", "mohamed@gmail.com", "123"));

        assertNotNull(registered);
        assertEquals("Mohamed", registered.getName());
        assertEquals("mohamed@gmail.com", registered.getEmail());
        assertNotEquals("123", registered.getPassword());
    }

    @Test
    void register_duplicateEmail_throwsIllegalStateException() {
        userService.register(new User("Mohamed", "m@gmail.com", "123"));

        assertThrows(IllegalStateException.class, () -> {
            userService.register(new User("Mahmoud", "m@gmail.com", "ABC"));
        });
    }

    @Test
    void login_correctPassword_returnsUser() {
        userService.register(new User("Mohamed", "mohamed@gmail.com", "123"));
        userService.register(new User("Mahmoud", "mahmoud@gmail.com", "ABC"));

        User loggedIn = userService.login("mohamed@gmail.com", "123");
        assertNotNull(loggedIn);
        assertEquals("Mohamed", loggedIn.getName());
        assertEquals("mohamed@gmail.com", loggedIn.getEmail());
    }

    @Test
    void login_unknownEmail_throwsIllegalArgumentException() {
        userService.register(new User("Mohamed", "mohamed@gmail.com", "123"));
        userService.register(new User("Mahmoud", "mahmoud@gmail.com", "ABC"));

        assertThrows(IllegalArgumentException.class, () -> {
            userService.login("samy@gmail.com", "123");
        });
    }

    @Test
    void login_wrongPassword_throwsIllegalArgumentException() {
        userService.register(new User("Mohamed", "mohamed@gmail.com", "123"));
        userService.register(new User("Mahmoud", "mahmoud@gmail.com", "ABC"));

        assertThrows(IllegalArgumentException.class, () -> {
            userService.login("mohamed@gmail.com", "ABC");
        });
    }
}
