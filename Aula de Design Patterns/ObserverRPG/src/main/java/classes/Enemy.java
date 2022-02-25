package classes;

import interfaces.Observer;

public class Enemy implements Observer{
    
    private String name;
    
    public Enemy(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void update() {
        System.out.println(name + ": Recebeu dano!");
    }
    
}
