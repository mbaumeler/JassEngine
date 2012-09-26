package ch.mbaumeler.jass.extended.ui;

import ch.mbaumeler.jass.core.Game;
import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.PlayerTokenRepository;
import ch.mbaumeler.jass.core.game.Score;

public class ObservableGame implements Game {

	private Game delegate;

	private ObserverRepository observerRepository;

	public ObservableGame(Game jassGame) {
		this.delegate = jassGame;
		this.observerRepository = new ObserverRepository();
	}

	@Override
	public PlayerTokenRepository getPlayerRepository() {
		return delegate.getPlayerRepository();
	}

	@Override
	public Match getCurrentMatch() {
		return new ObserverableMatch(delegate.getCurrentMatch(), observerRepository);
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

}
