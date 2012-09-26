package ch.mbaumeler.jass.core.game.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.mbaumeler.jass.core.card.Card;

/* REVIEW NEEDED */ public class Round {

	private List<Card> cards;

	public Round() {
		cards = new ArrayList<Card>(4);
	}

	public void addCard(Card card) {
		if (isComplete()) {
			throw new IllegalArgumentException("Round is complete");
		}
		cards.add(card);
	}

	public Card getLastPlayedCard() {
		return cards.get(cards.size() - 1);
	}

	public boolean isEmpty() {
		return cards.isEmpty();
	}

	public List<Card> getCards() {
		return Collections.unmodifiableList(cards);
	}

	public boolean isComplete() {
		return cards.size() == 4;
	}

}
