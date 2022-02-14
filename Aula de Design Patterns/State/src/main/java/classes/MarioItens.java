package classes;

import java.util.ArrayList;
import java.util.Stack;

public abstract class MarioItens {
    protected Stack<Item<String>> itens;
    protected ArrayList<StateObservator> events;
    
    public MarioItens(Stack<Item<String>> i, ArrayList<StateObservator> e){
        if(i == null || e == null){
            itens = new Stack<>();
            events = new ArrayList<>();
        }else{
            events = e;
            itens = i;
        }
    }
    
    public void notifyAllObserversAboutItemGet(Item<String> i){
        events.forEach(e -> e.showItemGetInfo(i));
    }
    public void notifyAllObserversAboutDamage(Item<String> i){
        events.forEach(e -> e.showDamageInfo(i));
    }
    
    public boolean addEventListener(StateObservator s){
        return this.events.add(s);
    }
    
    public boolean removeEventListener(StateObservator s){
        return this.events.remove(s);
    }
    
    protected void setItens(Stack<Item<String>> i){
        this.itens = i;
    }
    
    protected Item<String> getItem(int i){
        return itens.peek();
    }
    
    protected Item<String> addItem(Item<String> s){
        if(itens.size() > 1){
            itens.remove(0);
            return itens.push(s);
        }else{
            return itens.push(s);
        }
    }
    
    protected Item<String> removeItem(){
        if(itens.empty()){
            return null;
        }else{
            return itens.pop();
        }
    }
    
}
