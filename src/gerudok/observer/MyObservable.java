package gerudok.observer;

public interface MyObservable {
    void addObserver(MyObserver var1);

    void removeObserver(MyObserver var1);

    void notifyObservers(Object var1, ObserverEnum changeType);
}
