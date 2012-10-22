package ch.mbaumeler.jass.extended.ui;

import ch.mbaumeler.jass.core.Game;
import ch.mbaumeler.jass.core.GameState;
import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.Score;

/* REVIEW NEEDED */public class ObservableGame implements Game {

	private final Game delegate;

	private final ObserverRepository observerRepository;

	public ObservableGame(Game game, ObserverRepository observerRepository) {
		this.delegate = game;
		this.observerRepository = observerRepository;
	}

	@Override
	public Match getMatch() {
		return new ObserverableMatch(delegate.getMatch(), observerRepository);
	}

	@Override
	public Score getTotalScore() {
		return delegate.getTotalScore();
	}

	public void addObserver(JassModelObserver observer) {
		observerRepository.addObserver(observer);
	}

	public void removeObserver(JassModelObserver observer) {
		observerRepository.removeObserver(observer);
	}

	public void notifyObservers() {
		observerRepository.notifyObservers();
	}

	@Override
	public void createMatch() {
		delegate.createMatch();
		observerRepository.notifyObservers();
	}

	@Override
	public GameState createGameState() {
		return delegate.createGameState();
	}

}
