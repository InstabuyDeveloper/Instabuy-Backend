package edu.eci.arsw.instabuybackend.persistence;

import edu.eci.arsw.instabuybackend.model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductoRepository extends MongoRepository<Producto, String> {

    Optional<Producto> findByNombre(String nombre);

}
