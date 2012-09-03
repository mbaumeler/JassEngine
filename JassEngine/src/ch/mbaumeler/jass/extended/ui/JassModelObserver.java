package ch.mbaumeler.jass.extended.ui;

import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.extended.ui.ObserverableMatch.Event;

public interface JassModelObserver {

	void updated(Event event, PlayerToken activePlayer, Object object);

}
