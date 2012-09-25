package ch.mbaumeler.jass.extended.ui;

import java.util.List;
import java.util.Set;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.MatchState;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.Card;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.Score;
import ch.mbaumeler.jass.core.game.wys.Wys;

public class ObserverableMatch implements Match {

	private Match delegate;
	private ObserverRepository observerRepository;

	public enum Event {
		PLAYED_CARD, WYS, ANSAGE
	};

	public ObserverableMatch(Match match, ObserverRepository observerRepository) {
		this.delegate = match;
		this.observerRepository = observerRepository;
	}

	@Override
	public List<Card> getCards(PlayerToken player) {
		return delegate.getCards(player);
	}

	@Override
	public List<Card> getCardsOnTable() {
		return delegate.getCardsOnTable();
	}

	@Override
	public Ansage getAnsage() {
		return delegate.getAnsage();
	}

	@Override
	public void setAnsage(Ansage ansage) {
		delegate.setAnsage(ansage);
		observerRepository.notifyObservers();
	}

	@Override
	public PlayerToken getActivePlayer() {
		return delegate.getActivePlayer();
	}

	@Override
	public boolean isCardPlayable(Card card) {
		return delegate.isCardPlayable(card);
	}

	@Override
	public List<Card> getCardsFromRound(int i) {
		return delegate.getCardsFromRound(i);
	}

	@Override
	public void playCard(Card card) {
		delegate.playCard(card);
		observerRepository.notifyObservers();
	}

	@Override
	public boolean isComplete() {
		return delegate.isComplete();
	}

	@Override
	public Score getScore() {
		return delegate.getScore();
	}

	@Override
	public int getRoundsCompleted() {
		return delegate.getRoundsCompleted();
	}

	@Override
	public void wys(Set<Wys> wysSet) {
		delegate.wys(wysSet);
		observerRepository.notifyObservers();
	}

	@Override
	public void schiebe() {
		delegate.schiebe();
		observerRepository.notifyObservers();
	}

	@Override
	public boolean isGeschoben() {
		return delegate.isGeschoben();
	}

	@Override
	public MatchState createMatchState() {
		return delegate.createMatchState();
	}

}
