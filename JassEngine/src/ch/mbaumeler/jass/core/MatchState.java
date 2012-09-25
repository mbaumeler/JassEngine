package ch.mbaumeler.jass.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.Card;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.wys.Wys;

public class MatchState {

	private final Ansage ansage;
	private final List<Card> cards;
	private final List<Card> playedCards;
	private final int startingPlayerOffset;
	private final boolean geschoben;
	private final Map<PlayerToken, Set<Wys>> wysMap;
	private final PlayerToken stoeckPlayer;

	public MatchState(Ansage ansage, List<Card> cards, List<Card> playedCards, int startingPlayerOffset,
			boolean geschoben, Map<PlayerToken, Set<Wys>> wysMap, PlayerToken stoeckPlayer) {
		this.ansage = ansage;
		this.cards = cards;
		this.playedCards = playedCards;
		this.startingPlayerOffset = startingPlayerOffset;
		this.geschoben = geschoben;
		this.wysMap = wysMap;
		this.stoeckPlayer = stoeckPlayer;
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

	public Map<PlayerToken, Set<Wys>> getWysMap() {
		return wysMap;
	}

	public PlayerToken getStoeckPlayer() {
		return stoeckPlayer;
	}

}
