package ch.mbaumeler.jass.core.game;

import static ch.mbaumeler.jass.core.card.CardSuit.DIAMONDS;
import static ch.mbaumeler.jass.core.card.CardSuit.HEARTS;
import static ch.mbaumeler.jass.core.card.CardSuit.SPADES;
import static ch.mbaumeler.jass.core.game.Ansage.SpielModi.UNDEUFE;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_NINE;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_SIX;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.card.Card;

public class JassRulesTest {

	private JassRules jassRules;

	@Before
	public void setUp() throws Exception {
		jassRules = new JassRules();
	}

	@Test
	public void testNoTrumpfIsChoosen() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertFalse(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, null, null, false));
	}

	@Test
	public void testDoesNotHaveCardInHand() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertFalse(jassRules.isCardPlayable(DIAMONDS_NINE, cardsInHand, DIAMONDS, new Ansage(DIAMONDS), false));
	}

	@Test
	public void testPlaysTrumpf() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertTrue(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, DIAMONDS, new Ansage(HEARTS), false));
	}

	@Test
	public void testIsNewRound() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertTrue(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, null, new Ansage(DIAMONDS), true));
	}

	@Test
	public void testIsSameSuit() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertTrue(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, HEARTS, new Ansage(DIAMONDS), false));
	}

	@Test
	public void testDoesNotHaveSameColor() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertTrue(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, DIAMONDS, new Ansage(DIAMONDS), false));
	}

	@Test
	public void testWouldHaveSameColor() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertFalse(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, SPADES, new Ansage(DIAMONDS), false));
	}

	@Test
	public void testUndeUfe() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertTrue(jassRules.isCardPlayable(SPADES_SIX, cardsInHand, SPADES, new Ansage(UNDEUFE), false));
	}

	@Test
	public void testUndeUfe_notPlayable() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertFalse(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, SPADES, new Ansage(UNDEUFE), false));
	}
}
