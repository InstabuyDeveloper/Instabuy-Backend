package edu.eci.arsw.instabuybackend.model;

public class Competidor {
    private String name;
    private double amount = 0;

    public Competidor(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Competidor {amount=" + amount + ", name=" + name + "}";
    }

}