package edu.eci.arsw.instabuybackend.model;

import java.util.Comparator;

public class Comparador implements Comparator<Competidor> {

    @Override
    public int compare(Competidor o1, Competidor o2) {
        return ((int) (o1.getAmount() - o2.getAmount())) * -1;
    }

}