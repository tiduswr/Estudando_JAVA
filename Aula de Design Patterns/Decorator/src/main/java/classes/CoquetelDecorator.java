package classes;

public abstract class CoquetelDecorator extends Coquetel{
    protected Coquetel c;

    public CoquetelDecorator(Coquetel c) {
        this.c = c;
    }
    
    @Override
    public String getNome() {
        return c.getNome() + " e " + nome;
    }
    @Override
    public double getPreco() {
        return c.getPreco() + preco;
    }
    
}
