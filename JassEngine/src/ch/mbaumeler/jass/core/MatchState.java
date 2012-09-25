package ch.mbaumeler.jass.core;

import java.util.List;

import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.Card;
import ch.mbaumeler.jass.core.game.wys.WysStore;

public class MatchState {

	private final Ansage ansage;
	private final List<Card> cards;
	private final List<Card> playedCards;
	private final int startingPlayerOffset;
	private final boolean geschoben;
	private final WysStore wysStore;

	public MatchState(Ansage ansage, List<Card> cards, List<Card> playedCards, int startingPlayerOffset,
			boolean geschoben, WysStore wysStore) {
		this.ansage = ansage;
		this.cards = cards;
		this.playedCards = playedCards;
		this.startingPlayerOffset = startingPlayerOffset;
		this.geschoben = geschoben;
		this.wysStore = wysStore;
	}

	public Ansage getAnsage() {
		return ansage;
	}

	public List<Card> getCards() {
		return cards;
	}

	public List<Card> getPlayedCards() {
		return playedCards;
	}

	public int getStartingPlayerOffset() {
		return startingPlayerOffset;
	}

	public boolean isGeschoben() {
		return geschoben;
	}

	public WysStore getWysStore() {
		return wysStore;
	}
}
