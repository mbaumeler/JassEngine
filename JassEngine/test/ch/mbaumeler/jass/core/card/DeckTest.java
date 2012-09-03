package ch.mbaumeler.jass.core.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DeckTest {

	private Deck deck;

	@Before
	public void setup() {
		deck = new Deck();
	}

	@Test
	public void testNewDeck() {
		assertEquals(4 * 9, deck.getCards().size());
	}

	@Test
	public void testShuffle() {
		final List<Card> beforeShuffle = new ArrayList<Card>(deck.getCards());
		assertEquals(beforeShuffle, deck.getCards());

		deck.shuffle();
		List<Card> shuffled = deck.getCards();
		assertEquals(beforeShuffle.size(), shuffled.size());
		assertFalse(beforeShuffle.equals(shuffled));
	}

}
