package edu.eci.arsw.instabuybackend.services.impl;

import edu.eci.arsw.instabuybackend.persistence.UserRepository;
import edu.eci.arsw.instabuybackend.services.UserService;
import edu.eci.arsw.instabuybackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(String userId) {
        Optional<User> optionalUser = userRepository.findByuserId(userId);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        return null;
    }
}
