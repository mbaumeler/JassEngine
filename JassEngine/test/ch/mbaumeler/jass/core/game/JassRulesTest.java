package ch.mbaumeler.jass.core.game;

import static ch.mbaumeler.jass.core.card.CardSuit.DIAMONDS;
import static ch.mbaumeler.jass.core.card.CardSuit.HEARTS;
import static ch.mbaumeler.jass.core.game.Ansage.SpielModi.UNDEUFE;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_NINE;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_SEVEN;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_SIX;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class JassRulesTest {

	private JassRules jassRules;
	private List<Card> cardsOnTable;

	@Before
	public void setUp() throws Exception {
		jassRules = new JassRules();
		cardsOnTable = new ArrayList<Card>();
		jassRules.scoreUtil = new ScoreUtil();
	}

	@Test
	public void testNoTrumpfIsChoosen() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertFalse(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, cardsOnTable, null, false));
	}

	@Test
	public void testDoesNotHaveCardInHand() {

		cardsOnTable.add(DIAMONDS_SIX);

		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertFalse(jassRules.isCardPlayable(DIAMONDS_NINE, cardsInHand, cardsOnTable, new Ansage(DIAMONDS), false));
	}

	@Test
	public void testPlaysTrumpf() {

		cardsOnTable.add(DIAMONDS_SIX);

		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertTrue(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, cardsOnTable, new Ansage(HEARTS), false));
	}

	@Test
	public void testIsNewRound() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertTrue(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, cardsOnTable, new Ansage(DIAMONDS), true));
	}

	@Test
	public void testIsSameSuit() {

		cardsOnTable.add(HEARTS_SIX);
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertTrue(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, cardsOnTable, new Ansage(DIAMONDS), false));
	}

	@Test
	public void testDoesNotHaveSameColor() {

		cardsOnTable.add(DIAMONDS_SIX);
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertTrue(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, cardsOnTable, new Ansage(DIAMONDS), false));
	}

	@Test
	public void testWouldHaveSameColor() {

		cardsOnTable.add(SPADES_SEVEN);
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertFalse(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, cardsOnTable, new Ansage(DIAMONDS), false));
	}

	@Test
	public void testUndeUfe() {
		cardsOnTable.add(SPADES_SEVEN);
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertTrue(jassRules.isCardPlayable(SPADES_SIX, cardsInHand, cardsOnTable, new Ansage(UNDEUFE), false));
	}

	@Test
	public void testUndeUfe_notPlayable() {

		cardsOnTable.add(SPADES_SEVEN);
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertFalse(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, cardsOnTable, new Ansage(UNDEUFE), false));
	}

	@Test
	public void testUnterTrumpfenNotAllowed() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		Ansage heartsAnsage = new Ansage(HEARTS);
		cardsOnTable = new ArrayList<Card>();
		cardsOnTable.add(SPADES_SEVEN);
		cardsOnTable.add(HEARTS_ACE);

		assertFalse(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, cardsOnTable, heartsAnsage, false));
	}

	@Test
	public void testUeberTrumpfenAllowed() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_ACE);
		Ansage heartsAnsage = new Ansage(HEARTS);
		cardsOnTable = new ArrayList<Card>();
		cardsOnTable.add(SPADES_SEVEN);
		cardsOnTable.add(HEARTS_KING);

		assertTrue(jassRules.isCardPlayable(HEARTS_ACE, cardsInHand, cardsOnTable, heartsAnsage, false));
	}

	@Test
	public void testUnterTrumpfenAllowed() {
		List<Card> cardsInHand = Arrays.asList(HEARTS_KING);
		Ansage heartsAnsage = new Ansage(HEARTS);
		cardsOnTable = new ArrayList<Card>();
		cardsOnTable.add(SPADES_SEVEN);
		cardsOnTable.add(HEARTS_ACE);

		assertTrue(jassRules.isCardPlayable(HEARTS_KING, cardsInHand, cardsOnTable, heartsAnsage, false));
	}
}
