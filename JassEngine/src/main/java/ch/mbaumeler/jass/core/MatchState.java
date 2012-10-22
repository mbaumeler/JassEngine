package ch.mbaumeler.jass.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.impl.Round;
import ch.mbaumeler.jass.core.game.wys.Wys;

/* REVIEW NEEDED */public class MatchState {

	private final Ansage ansage;
	private final List<Card> cards;
	private final List<Round> playedCards;
	private final PlayerToken startingPlayer;
	private final boolean geschoben;
	private final Map<PlayerToken, Set<Wys>> wysMap;
	private final PlayerToken stoeckPlayer;

	public MatchState(Ansage ansage, List<Card> cards, List<Round> playedCards, PlayerToken startingPlayer,
			boolean geschoben, Map<PlayerToken, Set<Wys>> wysMap, PlayerToken stoeckPlayer) {
		this.ansage = ansage;
		this.cards = new ArrayList<Card>(cards);
		this.playedCards = new ArrayList<Round>(playedCards);
		this.startingPlayer = startingPlayer;
		this.geschoben = geschoben;
		this.wysMap = new HashMap<PlayerToken, Set<Wys>>(wysMap);
		this.stoeckPlayer = stoeckPlayer;
	}

	public Ansage getAnsage() {
		return ansage;
	}

	public List<Card> getCards() {
		return cards;
	}

	public List<Round> getPlayedCards() {
		return playedCards;
	}

	public PlayerToken getStartingPlayer() {
		return startingPlayer;
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
