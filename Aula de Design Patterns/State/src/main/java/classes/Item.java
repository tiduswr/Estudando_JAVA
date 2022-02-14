package classes;

public class Item<E> {
    private E nome;
    
    public Item(E nome){
        this.nome = nome;
    }
    
    public E getNome(){
        return nome;
    }
}
