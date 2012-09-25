package ch.mbaumeler.jass.core.game;

import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.card.CardValue;

public class Card {

	private final CardSuit cardSuit;
	private final CardValue cardValue;
	private final PlayerToken player;

	public Card(CardSuit cardSuit, CardValue cardValue, PlayerToken player) {
		this.cardSuit = cardSuit;
		this.cardValue = cardValue;
		this.player = player;
	}

	public CardSuit getSuit() {
		return cardSuit;
	}

	public CardValue getValue() {
		return cardValue;
	}

	public PlayerToken getPlayer() {
		return player;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardSuit == null) ? 0 : cardSuit.hashCode());
		result = prime * result + ((cardValue == null) ? 0 : cardValue.hashCode());
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
		Card other = (Card) obj;
		if (cardSuit != other.cardSuit)
			return false;
		if (cardValue != other.cardValue)
			return false;
		if (player != other.player)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CardSuit: " + cardSuit + ", CardValue: " + cardValue + ", PlayerToken: " + player;
	}
}