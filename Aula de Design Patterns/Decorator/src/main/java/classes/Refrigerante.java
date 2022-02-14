package classes;

public class Refrigerante extends CoquetelDecorator{
    
    public Refrigerante(Coquetel c) {
        super(c);
        nome = "Coca Cola";
        preco = 2.0f;
    }
    
}
