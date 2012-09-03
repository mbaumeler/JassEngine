package ch.mbaumeler.jass.test.integration;

import java.util.List;
import java.util.Random;

import ch.mbaumeler.jass.core.CardFactory;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.card.Deck;

public class PredictableCardFactory implements CardFactory {

	private static final long INITVALUE = 4134132L;

	private Random random = new Random(INITVALUE);

	private void predictedShuffle(List<Card> cardsToShuffle) {
		int size = cardsToShuffle.size();
		for (int i = 0; i < size; i++) {
			int newIndex = random.nextInt(size);
			Card cardToSwap = cardsToShuffle.get(i);
			Card old = cardsToShuffle.get(newIndex);
			cardsToShuffle.set(newIndex, cardToSwap);
			cardsToShuffle.set(i, old);
		}
	}

	@Override
	public List<Card> createShuffledCards() {
		Deck deck = new Deck();
		List<Card> cards = deck.getCards();
		predictedShuffle(cards);
		return cards;
	}

}
