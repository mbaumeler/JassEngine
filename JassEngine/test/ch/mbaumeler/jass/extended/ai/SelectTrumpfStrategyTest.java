package ch.mbaumeler.jass.extended.ai;

import static ch.mbaumeler.jass.core.card.CardSuit.DIAMONDS;
import static ch.mbaumeler.jass.core.card.CardSuit.HEARTS;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_TEN;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_NINE;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_NINE;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_SEVEN;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_TEN;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_SEVEN;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_TEN;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.Ansage;

public class SelectTrumpfStrategyTest {

	private SelectTrumpfStrategy selectTrumpfStrategy;

	@Before
	public void setup() {
		selectTrumpfStrategy = new SelectTrumpfStrategy();
	}

	@Test
	public void testGetTrumpfOnlyOneHearts() {
		List<Card> cards = Arrays.asList(HEARTS_ACE, HEARTS_KING, HEARTS_QUEEN, HEARTS_JACK, HEARTS_TEN, HEARTS_NINE,
				HEARTS_EIGHT, HEARTS_SEVEN, HEARTS_SIX);

		assertEquals(new Ansage(HEARTS), selectTrumpfStrategy.getTrumpf(cards));
	}

	@Test
	public void testGetTrumpfJackNineAce() {

		List<Card> cards = Arrays.asList(HEARTS_ACE, SPADES_KING, SPADES_QUEEN, HEARTS_JACK, SPADES_TEN, HEARTS_NINE,
				SPADES_EIGHT, SPADES_SEVEN, SPADES_SIX);

		assertEquals(new Ansage(HEARTS), selectTrumpfStrategy.getTrumpf(cards));
	}

	@Test
	public void testDifferentCards() {

		List<Card> cards = Arrays.asList(DIAMONDS_JACK, DIAMONDS_NINE, CLUBS_TEN, HEARTS_TEN, HEARTS_QUEEN, SPADES_TEN,
				SPADES_QUEEN, SPADES_KING);

		assertEquals(new Ansage(DIAMONDS), selectTrumpfStrategy.getTrumpf(cards));
	}

}
