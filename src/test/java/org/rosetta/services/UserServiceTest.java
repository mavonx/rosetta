package org.rosetta.services;

import org.mockito.Mock;
import org.rosetta.model.User;
import org.mockito.InjectMocks;
import org.mindrot.jbcrypt.BCrypt;
import org.junit.jupiter.api.Test;
import org.rosetta.repositories.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void register_newEmail_returnsSavedUser() {
        when(userRepository.findByEmail("mohamed@gmail.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            return new User(1, user.getName(), user.getEmail(), user.getPassword());
        });

        User registered = userService.register(new User("Mohamed", "mohamed@gmail.com", "123"));

        assertNotNull(registered);
        assertEquals(1, registered.getId());
        assertEquals("Mohamed", registered.getName());
        assertEquals("mohamed@gmail.com", registered.getEmail());
        assertNotEquals("123", registered.getPassword());
    }

    @Test
    void register_duplicateEmail_throwsIllegalStateException() {
        when(userRepository.findByEmail("mohamed@gmail.com")).thenReturn(
            Optional.of(
                new User(1, "Mohamed", "mohamed@gmail.com", "123")
            )
        );

        assertThrows(IllegalStateException.class, () -> {
            userService.register(new User("Mohamed", "mohamed@gmail.com", "123"));
        });
    }

    @Test
    void login_correctPassword_returnsUser() {
        when(userRepository.findByEmail("mohamed@gmail.com")).thenReturn(
            Optional.of(
                new User(
                    1,
                    "Mohamed",
                    "mohamed@gmail.com",
                    BCrypt.hashpw("123", BCrypt.gensalt())
                )
            )
        );

        User loggedIn = userService.login("mohamed@gmail.com", "123");

        assertNotNull(loggedIn);
        assertEquals(1, loggedIn.getId());
        assertEquals("Mohamed", loggedIn.getName());
        assertEquals("mohamed@gmail.com", loggedIn.getEmail());
        assertTrue(BCrypt.checkpw("123", loggedIn.getPassword()));
    }

    @Test
    void login_unknownEmail_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.login("samy@gmail.com", "123");
        });
    }

    @Test
    void login_wrongPassword_throwsIllegalArgumentException() {
        when(userRepository.findByEmail("mohamed@gmail.com")).thenReturn(
            Optional.of(
                new User(
                    1,
                    "Mohamed",
                    "mohamed@gmail.com",
                    BCrypt.hashpw("123", BCrypt.gensalt())
                )
            )
        );

        assertThrows(IllegalArgumentException.class, () -> {
            userService.login("mohamed@gmail.com", "ABC");
        });
    }
}
