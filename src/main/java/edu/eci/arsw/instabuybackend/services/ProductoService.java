package edu.eci.arsw.instabuybackend.services;

import edu.eci.arsw.instabuybackend.model.Producto;

public interface ProductoService {

    Producto create(Producto producto) ;
    Producto getProductoByNombre(String nombre);
}
