package edu.eci.arsw.instabuybackend.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Sala {

    private Producto producto;
    private String salaName;
    private String creador;
    private String ganador;

    public Sala() {
    }

    private ConcurrentHashMap<String, Competidor> datos = new ConcurrentHashMap<>();

    public Sala(String salaName) {
        this.salaName = salaName;
    }

    public Sala(Producto producto, String salaName, String creador) {
        this.producto = producto;
        this.salaName = salaName;
        this.creador = creador;
    }

    public void addUser(String user) {
        if (!datos.containsKey(user)) {
            Competidor temp = new Competidor(user);
            datos.put(user, temp);
        }

    }

    public List<Competidor> getDatos() {
        List<Competidor> temp = new ArrayList<Competidor>(datos.values());
        Collections.sort(temp, new Comparador());
        return temp;

    }

    public List<Competidor> nameMembers() {
        List<Competidor> temp = new ArrayList<Competidor>(datos.values());
        return temp;

    }

    // user actions

    public void offer(String name, double amount) {
        if(amount >= producto.getPrecioBase()) {
            datos.get(name).setAmount(amount);
        }

    }

    // owner
    public void setGanador(String name) {
        this.ganador=name;
    }

    public String getGanador() {
        return ganador;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getSalaName() {
        return salaName;
    }

    public void setSalaName(String salaName) {
        this.salaName = salaName;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public void setDatos(ConcurrentHashMap<String, Competidor> datos) {
        this.datos = datos;
    }
}