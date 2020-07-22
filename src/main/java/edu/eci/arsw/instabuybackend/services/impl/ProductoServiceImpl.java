package edu.eci.arsw.instabuybackend.services.impl;

import edu.eci.arsw.instabuybackend.model.Producto;
import edu.eci.arsw.instabuybackend.persistence.ProductoRepository;
import edu.eci.arsw.instabuybackend.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;


    @Override
    public Producto create(Producto producto) {
        return productoRepository.save(producto);
    }


    @Override
    public Producto getProductoByNombre(String nombre) {
        Optional<Producto> optionalProducto = productoRepository.findByNombre(nombre);
        if (optionalProducto.isPresent()){
            return optionalProducto.get();
        }
        return null;
    }
}
