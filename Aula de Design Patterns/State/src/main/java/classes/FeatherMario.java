package classes;

import java.util.ArrayList;
import java.util.Stack;

public class FeatherMario extends MarioItens implements MarioState {

    public FeatherMario(Stack<Item<String>> i, ArrayList<StateObservator> e) {
        super(i, e);
    }
    
        @Override
    public MarioState getMushroom() {
        Item<String> i = new Item<>("Cogumelo");
        this.addItem(i);
        this.notifyAllObserversAboutItemGet(i);
        TallMario s = new TallMario(itens, this.events);
        return s;
    }

    @Override
    public MarioState getFlower() {
        Item<String> i = new Item<>("Flor");
        this.addItem(i);
        this.notifyAllObserversAboutItemGet(i);;
        FlowerMario s = new FlowerMario(itens, this.events);
        return s;
    }

    @Override
    public MarioState getFeather() {
        Item<String> i = new Item<>("Pena");
        this.addItem(i);
        this.notifyAllObserversAboutItemGet(i);
        FeatherMario s = new FeatherMario(itens, this.events);
        return s;
    }

    @Override
    public MarioState receiveDamage() {
        Item<String> test = this.removeItem();
        this.notifyAllObserversAboutDamage(test);
        
        if(test == null){
            MarioDied s = new MarioDied(itens, this.events);
            return s;
        }else{
            return this;
        }
    }
    
}
