package edu.eci.arsw.instabuybackend.controllers;

import edu.eci.arsw.instabuybackend.config.Token;
import edu.eci.arsw.instabuybackend.services.UserService;
import edu.eci.arsw.instabuybackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletException;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@RestController
@RequestMapping("users")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User userLogin) throws ServletException {
        String jwtToken = "";

        if (userLogin.getEmail() == null || userLogin.getPassword() == null) {
            throw new ServletException("Please fill in username and password");
        }

        String email = userLogin.getEmail();
        String password = userLogin.getPassword();

        User user = userService.getUserByEmail(email);

        String pwd = user.getPassword();


        if (!pwd.equals(password)) {
            throw new ServletException("Invalid login. Please check your name and password.");
        }

        jwtToken = Jwts.builder().setSubject(email).claim("roles", "user").setIssuedAt(new Date()).signWith(
                SignatureAlgorithm.HS256, "secretkey").compact();

        return new ResponseEntity<>(new Token(jwtToken, user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User registerUser) {
        return new ResponseEntity<>(userService.create(registerUser), HttpStatus.CREATED);
    }
}