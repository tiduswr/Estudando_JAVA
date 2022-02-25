package interfaces;

public interface Observable {
    public void attach(Observer o);
    public void detach(Observer o);
    public void notifyAllObservers();
    public void notifyObserver(Observer o);
}
