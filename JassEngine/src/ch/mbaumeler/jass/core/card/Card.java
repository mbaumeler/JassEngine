package ch.mbaumeler.jass.core.card;

public class Card {

	private final CardSuit suit;

	private final CardValue value;

	public Card(CardSuit suit, CardValue value) {
		this.suit = suit;
		this.value = value;
	}

	public CardSuit getSuit() {
		return suit;
	}

	public CardValue getValue() {
		return value;
	}

	@Override
	public String toString() {
		return suit + "_" + value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		if (suit != other.suit)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

}
