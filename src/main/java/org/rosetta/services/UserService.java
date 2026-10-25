package org.rosetta.services;

import org.rosetta.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.rosetta.repositories.UserRepository;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public User register(User user) {
        String normalizedEmail = user.getEmail().toLowerCase();
        if(userRepository.findByEmail(normalizedEmail).isPresent())
            throw new IllegalStateException("Email address is already registered");
        return userRepository.save(
            new User(
                user.getName(),
                normalizedEmail,
                BCrypt.hashpw(user.getPassword(), BCrypt.gensalt())
            )
        );
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email.toLowerCase()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if(!BCrypt.checkpw(password, user.getPassword()))
            throw new IllegalArgumentException("Invalid email or password");
        return user;
    }
}
