package edu.eci.arsw.instabuybackend.controllers;

import edu.eci.arsw.instabuybackend.config.Token;
import edu.eci.arsw.instabuybackend.model.Producto;
import edu.eci.arsw.instabuybackend.services.ProductoService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.Date;


@RestController
@RequestMapping("productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @PostMapping("/register")
    public ResponseEntity<Producto> register(@RequestBody Producto registerProducto) {
        return new ResponseEntity<>(productoService.create(registerProducto), HttpStatus.CREATED);
    }
}