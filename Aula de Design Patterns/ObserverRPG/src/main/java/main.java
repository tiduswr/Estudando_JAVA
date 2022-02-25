
import classes.Enemy;
import classes.Player;

public class main {

    public static void main(String args[]) {
        //Instancia as classes
        Enemy e = new Enemy("Esqueleto");
        Enemy e2 = new Enemy("Aranha");
        Enemy e3 = new Enemy("Dragão");
        Player p = new Player("Tidus");
        
        //Adiciona os inimigos como objetos observaveis para o player
        p.attach(e);
        p.attach(e2);
        p.attach(e3);
        
        //Ataca em area(notifyAll())
        System.out.println("Atacando em area:");
        p.atacarEmArea();
        
        //Ataca apenas 1 inimigo(notify(Observer))
        System.out.println("\nAtacando apenas um inimigo");
        p.atacar(e2);
    }
}
