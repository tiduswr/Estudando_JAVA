package main;

import classes.*;

public class main {
    public static void main(String args[]) {
        Coquetel mc = new Cachaca();
        
        showCoquetelInfo(mc);
        
        System.out.println("Decorator em ação...");
        mc = new Refrigerante(mc);
        
        showCoquetelInfo(mc);
    }
    
    public static void showCoquetelInfo(Coquetel c){
        System.out.println(c.getNome() + " = " + c.getPreco());
    }
    
}
