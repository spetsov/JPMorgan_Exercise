package common;

public interface Observable {
	void addObserver(Observer o);
	void notifyObservers();
	void deleteObserver(Observer o);
}
