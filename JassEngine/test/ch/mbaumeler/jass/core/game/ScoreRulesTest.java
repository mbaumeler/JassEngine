package ch.mbaumeler.jass.core.game;

import static ch.mbaumeler.jass.core.card.CardSuit.CLUBS;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_SEVEN;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_TEN;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_NINE;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ScoreRulesTest {

	private ScoreRules scoreRules;
	private Ansage ansage;

	@Before
	public void setup() {
		scoreRules = new ScoreRules();
		ansage = new Ansage(CLUBS);
	}

	@Test
	public void testGetPointForTrumpfJack() {
		assertEquals(20, scoreRules.getScore(CLUBS_JACK, ansage));
	}

	@Test
	public void testGetPointForJack() {
		assertEquals(2, scoreRules.getScore(HEARTS_JACK, ansage));
	}

	@Test
	public void testGetPointForQueen() {
		assertEquals(3, scoreRules.getScore(CLUBS_QUEEN, ansage));
	}

	@Test
	public void testGetPointForKing() {
		assertEquals(4, scoreRules.getScore(CLUBS_KING, ansage));
	}

	@Test
	public void testGetPointForAce() {
		assertEquals(11, scoreRules.getScore(CLUBS_ACE, ansage));
	}

	@Test
	public void testGetPointForTen() {
		assertEquals(10, scoreRules.getScore(CLUBS_TEN, ansage));
	}

	@Test
	public void testGetPointForRest() {
		assertEquals(0, scoreRules.getScore(HEARTS_NINE, ansage));
		assertEquals(0, scoreRules.getScore(CLUBS_EIGHT, ansage));
		assertEquals(0, scoreRules.getScore(CLUBS_SEVEN, ansage));
		assertEquals(0, scoreRules.getScore(CLUBS_SIX, ansage));
	}

}
