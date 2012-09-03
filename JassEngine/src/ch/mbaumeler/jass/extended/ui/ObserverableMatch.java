package ch.mbaumeler.jass.extended.ui;

import static ch.mbaumeler.jass.extended.ui.ObserverableMatch.Event.ANSAGE;
import static ch.mbaumeler.jass.extended.ui.ObserverableMatch.Event.PLAYED_CARD;
import static ch.mbaumeler.jass.extended.ui.ObserverableMatch.Event.WYS;

import java.util.List;
import java.util.Set;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayedCard;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.Score;
import ch.mbaumeler.jass.core.game.wys.Wys;

public class ObserverableMatch implements Match {

	private List<JassModelObserver> observers;
	private Match delegate;

	public enum Event {
		PLAYED_CARD, WYS, ANSAGE
	};

	public ObserverableMatch(Match match, List<JassModelObserver> observers) {
		this.delegate = match;
		this.observers = observers;
	}

	@Override
	public List<Card> getCards(PlayerToken player) {
		return delegate.getCards(player);
	}

	@Override
	public List<PlayedCard> getCardsOnTable() {
		return delegate.getCardsOnTable();
	}

	@Override
	public Ansage getAnsage() {
		return delegate.getAnsage();
	}

	@Override
	public void setAnsage(Ansage ansage) {
		delegate.setAnsage(ansage);
		updateObserver(ANSAGE, delegate.getActivePlayer(), ansage);
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
	public List<PlayedCard> getCardsFromRound(int i) {
		return delegate.getCardsFromRound(i);
	}

	@Override
	public void playCard(Card card) {
		PlayerToken activePlayer = delegate.getActivePlayer();
		delegate.playCard(card);
		updateObserver(PLAYED_CARD, activePlayer, card);
	}

	@Override
	public boolean isComplete() {
		return delegate.isComplete();
	}

	@Override
	public Score getScore() {
		return delegate.getScore();
	}

	private void updateObserver(Event event, PlayerToken activePlayer, Object object) {
		for (JassModelObserver observer : observers) {
			observer.updated(event, activePlayer, object);
		}
	}

	@Override
	public int getRoundsCompleted() {
		return delegate.getRoundsCompleted();
	}

	@Override
	public void wys(Set<Wys> wysSet) {
		delegate.wys(wysSet);
		updateObserver(WYS, delegate.getActivePlayer(), wysSet);
	}

}
