package main;

import classes.*;

public class main {

    public static void main(String args[]) {
        FabricaDeCarro fabrica = new FabricaVolks();
        Carro gol = fabrica.criarCarro();
        
        gol.showInfo();
    }
}
