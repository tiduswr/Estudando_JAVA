package classes;

import java.util.ArrayList;
import java.util.Stack;

public class MarioDied extends MarioItens implements MarioState {

    public MarioDied(Stack<Item<String>> i, ArrayList<StateObservator> e) {
        super(i, e);
    }

    @Override
    public MarioState getMushroom() {
        return null;
    }

    @Override
    public MarioState getFlower() {
        return null;
    }

    @Override
    public MarioState getFeather() {
        return null;
    }

    @Override
    public MarioState receiveDamage() {
        return null;
    }
    
}
