package ch.mbaumeler.jass.core.game.impl;

import java.util.List;

import ch.mbaumeler.jass.core.CardFactory;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.card.Deck;

public class CardFactoryImpl implements CardFactory {

	@Override
	public List<Card> createShuffledCards() {
		Deck deck = new Deck();
		deck.shuffle();
		return deck.getCards();
	}
}
