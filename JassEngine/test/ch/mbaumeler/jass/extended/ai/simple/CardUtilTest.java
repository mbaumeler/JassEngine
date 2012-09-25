package ch.mbaumeler.jass.extended.ai.simple;

import static ch.mbaumeler.jass.core.card.CardSuit.CLUBS;
import static ch.mbaumeler.jass.core.card.CardSuit.DIAMONDS;
import static ch.mbaumeler.jass.core.card.CardSuit.HEARTS;
import static ch.mbaumeler.jass.core.card.CardSuit.SPADES;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_SIX;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.game.PlayedCard;

public class CardUtilTest {

	private CardUtil cardUtil;

	@Before
	public void setUp() throws Exception {
		cardUtil = new CardUtil();
	}

	@Test
	public void testCreateCardMap() {
		List<PlayedCard> cards = Arrays.asList(DIAMONDS_ACE, DIAMONDS_KING, HEARTS_JACK, CLUBS_EIGHT, SPADES_SIX);
		Map<CardSuit, List<PlayedCard>> map = cardUtil.createCardMap(cards);
		assertEquals(4, map.size());
		assertEquals(2, map.get(DIAMONDS).size());
		assertEquals(1, map.get(HEARTS).size());
		assertEquals(1, map.get(CLUBS).size());
		assertEquals(1, map.get(SPADES).size());
	}

	@Test
	public void testCreateCardMapWithEmptySuites() {
		List<PlayedCard> cards = Arrays.asList(DIAMONDS_ACE, DIAMONDS_KING);
		Map<CardSuit, List<PlayedCard>> map = cardUtil.createCardMap(cards);
		assertEquals(4, map.size());
		assertEquals(2, map.get(DIAMONDS).size());
		assertEquals(0, map.get(HEARTS).size());
		assertEquals(0, map.get(CLUBS).size());
		assertEquals(0, map.get(SPADES).size());
	}

}
