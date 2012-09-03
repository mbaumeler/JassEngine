package ch.mbaumeler.jass.core.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

	private List<Card> cards;

	public Deck() {
		cards = new ArrayList<Card>();
		for (CardSuit suit : CardSuit.values()) {
			for (CardValue value : CardValue.values()) {
				cards.add(new Card(suit, value));
			}
		}
	}

	public List<Card> getCards() {
		return cards;
	}

	public void shuffle() {
		Collections.shuffle(cards);
	}

}
