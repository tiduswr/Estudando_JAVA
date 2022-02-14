package classes;

public interface MarioState {
    public MarioState getMushroom();
    public MarioState getFlower();
    public MarioState getFeather();
    public MarioState receiveDamage();
    public boolean addEventListener(StateObservator s);
    public boolean removeEventListener(StateObservator s);
}
