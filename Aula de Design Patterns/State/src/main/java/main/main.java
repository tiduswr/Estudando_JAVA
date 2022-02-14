package main;

import classes.MarioEntity;
import classes.StateObservator;

public class main {
    public static void main(String[] args) {
        MarioEntity m = new MarioEntity();
        
        m.addEventListener(new StateObservator());
        
        m.getMushroom();
        m.getFeather();
        m.receiveDamage();
        m.getFlower();
        m.receiveDamage();
        m.receiveDamage();
        m.getFeather();
        m.receiveDamage();
        m.receiveDamage();
    }
}
