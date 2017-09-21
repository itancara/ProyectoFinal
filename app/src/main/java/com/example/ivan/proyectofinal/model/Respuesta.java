package com.example.ivan.proyectofinal.model;
import java.util.ArrayList;


public class Respuesta {

    private boolean success;
    private int count;

    public ArrayList<Comida> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Comida> recipes) {
        this.recipes = recipes;
    }

    private ArrayList<Comida> recipes;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}


