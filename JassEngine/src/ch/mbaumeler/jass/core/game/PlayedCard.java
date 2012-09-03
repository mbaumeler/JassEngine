package ch.mbaumeler.jass.core.game;

import ch.mbaumeler.jass.core.card.Card;

public class PlayedCard {

	private final Card card;
	private final PlayerToken player;

	public PlayedCard(Card card, PlayerToken player) {
		this.card = card;
		this.player = player;
	}

	public Card getCard() {
		return card;
	}

	public PlayerToken getPlayer() {
		return player;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((card == null) ? 0 : card.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayedCard other = (PlayedCard) obj;
		if (card == null) {
			if (other.card != null)
				return false;
		} else if (!card.equals(other.card))
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return player + " played " + card;
	}

}