package ch.mbaumeler.jass.extended.ui;

import java.util.ArrayList;
import java.util.List;

import ch.mbaumeler.jass.core.Game;
import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.PlayerTokenRepository;
import ch.mbaumeler.jass.core.game.Score;

public class ObservableGame implements Game {

	private Game delegate;

	private List<JassModelObserver> observers = new ArrayList<JassModelObserver>();

	public ObservableGame(Game jassGame) {
		this.delegate = jassGame;
	}

	public void addObserver(JassModelObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	@Override
	public PlayerTokenRepository getPlayerRepository() {
		return delegate.getPlayerRepository();
	}

	@Override
	public Match getCurrentMatch() {
		return new ObserverableMatch(delegate.getCurrentMatch(), observers);
	}

	@Override
	public Score getTotalScore() {
		return delegate.getTotalScore();
	}

}
