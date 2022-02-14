package classes;

public class MarioEntity {
    private MarioState state;
    
    public MarioEntity(){
        state = new SmallMario();
    }
    
    public void getMushroom() {
        state = state.getMushroom();
    }

    public void getFlower() {
        state = state.getFlower();
    }

    public void getFeather() {
        state = state.getFeather();
    }

    public void receiveDamage() {
        state = state.receiveDamage();
    }
    
    public void addEventListener(StateObservator s){
        state.addEventListener(s);
    }
    
    public void removeEventListener(StateObservator s){
        state.removeEventListener(s);
    }
    
}
