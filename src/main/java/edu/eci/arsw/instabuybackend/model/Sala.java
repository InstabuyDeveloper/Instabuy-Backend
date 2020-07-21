package edu.eci.arsw.instabuybackend.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Sala {

    private String productName;
    private String salaName;

    private ConcurrentHashMap<String, Competidor> datos = new ConcurrentHashMap<>();

    public Sala(String salaName) {
        this.salaName = salaName;
    }

    public Sala(String productName, String salaName) {
        this.productName = productName;
        this.salaName = salaName;

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
        datos.get(name).setAmount(amount);

    }

    // owner
    public void winner(String name) {

    }

}