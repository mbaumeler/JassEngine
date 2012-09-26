package ch.mbaumeler.jass.core.game.wys;

import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.BLATT;
import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.STOECK;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_NINE;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_SEVEN;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_TEN;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_NINE;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_SEVEN;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_TEN;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_NINE;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_SEVEN;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_TEN;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_NINE;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_SEVEN;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_TEN;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.game.wys.Wys.WysTyp;

/* REVIEW NEEDED */ public class WysScoreRuleTest {

	private WysScoreRule wyssScoreRule;

	@Before
	public void setUp() throws Exception {
		wyssScoreRule = new WysScoreRule();
	}

	@Test
	public void testGetPointsForStoeck() {
		Wys stoeck = new Wys(Arrays.asList(CLUBS_KING, CLUBS_QUEEN), STOECK);
		assertEquals(20, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints4FourSix() {
		Wys stoeck = new Wys(Arrays.asList(CLUBS_SIX, DIAMONDS_SIX, HEARTS_SIX, SPADES_SIX), WysTyp.VIER_GLEICHE);
		assertEquals(100, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints4FourSeven() {
		Wys stoeck = new Wys(Arrays.asList(CLUBS_SEVEN, DIAMONDS_SEVEN, HEARTS_SEVEN, SPADES_SEVEN),
				WysTyp.VIER_GLEICHE);
		assertEquals(100, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints4FourEight() {
		Wys stoeck = new Wys(Arrays.asList(CLUBS_EIGHT, DIAMONDS_EIGHT, HEARTS_EIGHT, SPADES_EIGHT),
				WysTyp.VIER_GLEICHE);
		assertEquals(100, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints4FourNine() {
		Wys stoeck = new Wys(Arrays.asList(CLUBS_NINE, DIAMONDS_NINE, HEARTS_NINE, SPADES_NINE), WysTyp.VIER_GLEICHE);
		assertEquals(150, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints4FourTen() {
		Wys stoeck = new Wys(Arrays.asList(CLUBS_TEN, DIAMONDS_TEN, HEARTS_TEN, SPADES_TEN), WysTyp.VIER_GLEICHE);
		assertEquals(100, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints4FourJacks() {
		Wys stoeck = new Wys(Arrays.asList(CLUBS_JACK, DIAMONDS_JACK, HEARTS_JACK, SPADES_JACK), WysTyp.VIER_GLEICHE);
		assertEquals(200, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints4FourQueen() {
		Wys stoeck = new Wys(Arrays.asList(CLUBS_QUEEN, DIAMONDS_QUEEN, HEARTS_QUEEN, SPADES_QUEEN),
				WysTyp.VIER_GLEICHE);
		assertEquals(100, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints4FourKing() {
		Wys stoeck = new Wys(Arrays.asList(CLUBS_KING, DIAMONDS_KING, HEARTS_KING, SPADES_KING), WysTyp.VIER_GLEICHE);
		assertEquals(100, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints4FourAce() {
		Wys stoeck = new Wys(Arrays.asList(CLUBS_ACE, DIAMONDS_ACE, HEARTS_ACE, SPADES_ACE), WysTyp.VIER_GLEICHE);
		assertEquals(100, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints3Blatt() {
		Wys stoeck = new Wys(Arrays.asList(HEARTS_SIX, HEARTS_NINE, HEARTS_SEVEN), BLATT);
		assertEquals(20, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints4Blatt() {
		Wys stoeck = new Wys(Arrays.asList(HEARTS_SIX, HEARTS_NINE, HEARTS_SEVEN, HEARTS_EIGHT), BLATT);
		assertEquals(50, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints5Blatt() {
		Wys stoeck = new Wys(Arrays.asList(HEARTS_SIX, HEARTS_TEN, HEARTS_NINE, HEARTS_SEVEN, HEARTS_EIGHT), BLATT);
		assertEquals(100, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints6Blatt() {
		Wys stoeck = new Wys(
				Arrays.asList(HEARTS_SIX, HEARTS_TEN, HEARTS_NINE, HEARTS_SEVEN, HEARTS_JACK, HEARTS_EIGHT), BLATT);
		assertEquals(150, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints7Blatt() {
		Wys stoeck = new Wys(Arrays.asList(HEARTS_SIX, HEARTS_QUEEN, HEARTS_TEN, HEARTS_NINE, HEARTS_SEVEN,
				HEARTS_JACK, HEARTS_EIGHT), BLATT);
		assertEquals(200, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints8Blatt() {
		Wys stoeck = new Wys(Arrays.asList(HEARTS_SIX, HEARTS_QUEEN, HEARTS_TEN, HEARTS_NINE, HEARTS_SEVEN,
				HEARTS_KING, HEARTS_JACK, HEARTS_EIGHT), BLATT);
		assertEquals(250, wyssScoreRule.getScoreFore(stoeck));
	}

	@Test
	public void testGetPoints9Blatt() {
		Wys stoeck = new Wys(Arrays.asList(HEARTS_ACE, HEARTS_SIX, HEARTS_QUEEN, HEARTS_TEN, HEARTS_NINE, HEARTS_SEVEN,
				HEARTS_KING, HEARTS_JACK, HEARTS_EIGHT), BLATT);
		assertEquals(300, wyssScoreRule.getScoreFore(stoeck));
	}

}
