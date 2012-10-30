package ch.mbaumeler.jass.extended.ui;

import java.util.ArrayList;
import java.util.List;

public class ObserverRepository {

	private List<JassModelObserver> observers = new ArrayList<JassModelObserver>();

	public void addObserver(JassModelObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	public void removeObserver(JassModelObserver observer) {
		observers.remove(observer);
	}

	public void notifyObservers() {
		for (JassModelObserver jassObserver : observers) {
			jassObserver.jassModelChanged();
		}
	}

}
