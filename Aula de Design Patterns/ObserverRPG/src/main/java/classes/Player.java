package classes;

import interfaces.Observable;
import interfaces.Observer;
import java.util.ArrayList;

public class Player implements Observable{
    
    private ArrayList<Observer> obsList;
    private String nome;

    public Player(String nome) {
        this.nome = nome;
        obsList = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public void attach(Observer o) {
        this.obsList.add(o);
    }

    @Override
    public void detach(Observer o) {
        this.obsList.remove(o);
    }

    @Override
    public void notifyAllObservers() {
        this.obsList.forEach(e -> e.update());
    }

    @Override
    public void notifyObserver(Observer o) {
        o.update();
    }
    
    public void atacar(Observer o){
        notifyObserver(o);
    }
    
    public void atacarEmArea(){
        notifyAllObservers();
    }
    
}
