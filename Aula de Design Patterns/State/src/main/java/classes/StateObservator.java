package classes;

public class StateObservator {
    public void showItemGetInfo(Item<String> i){
        if(i != null){
            System.out.println("O mario pegou um(a): " + i.getNome());
        }else{
            System.out.println("O mario esta morto!");
        }
    }
    
    public void showDamageInfo(Item<String> i){
        if(i != null){
            System.out.println("O mario perdeu um(a): " + i.getNome());
        }else{
            System.out.println("O mario esta morto!");
        }
    }
    
}
