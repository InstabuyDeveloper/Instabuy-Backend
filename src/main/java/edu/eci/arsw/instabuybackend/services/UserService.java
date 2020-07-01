package edu.eci.arsw.instabuybackend.services;

import edu.eci.arsw.instabuybackend.model.User;

public interface UserService {

    User create(User user) ;
    User getUserById(String userId) ;
    User getUserByEmail(String email);
}
