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

import ch.mbaumeler.jass.core.card.Card;

public class JassRulesTest {

	private JassRules jassRules;
	private List<PlayedCard> cardsOnTable;

	@Before
	public void setUp() throws Exception {
		jassRules = new JassRules();
		cardsOnTable = new ArrayList<PlayedCard>();
		jassRules.scoreUtil = new ScoreUtil();
	}

	@Test
	public void testNoTrumpfIsChoosen() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertFalse(jassRules.isCardPlayable(HEARTS_KING, cardsInHand,
				cardsOnTable, null, false));
	}

	@Test
	public void testDoesNotHaveCardInHand() {

		cardsOnTable.add(new PlayedCard(DIAMONDS_SIX, null));

		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertFalse(jassRules.isCardPlayable(DIAMONDS_NINE, cardsInHand,
				cardsOnTable, new Ansage(DIAMONDS), false));
	}

	@Test
	public void testPlaysTrumpf() {

		cardsOnTable.add(new PlayedCard(DIAMONDS_SIX, null));

		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertTrue(jassRules.isCardPlayable(HEARTS_KING, cardsInHand,
				cardsOnTable, new Ansage(HEARTS), false));
	}

	@Test
	public void testIsNewRound() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertTrue(jassRules.isCardPlayable(HEARTS_KING, cardsInHand,
				cardsOnTable, new Ansage(DIAMONDS), true));
	}

	@Test
	public void testIsSameSuit() {

		cardsOnTable.add(new PlayedCard(HEARTS_SIX, null));
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertTrue(jassRules.isCardPlayable(HEARTS_KING, cardsInHand,
				cardsOnTable, new Ansage(DIAMONDS), false));
	}

	@Test
	public void testDoesNotHaveSameColor() {

		cardsOnTable.add(new PlayedCard(DIAMONDS_SIX, null));
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertTrue(jassRules.isCardPlayable(HEARTS_KING, cardsInHand,
				cardsOnTable, new Ansage(DIAMONDS), false));
	}

	@Test
	public void testWouldHaveSameColor() {

		cardsOnTable.add(new PlayedCard(SPADES_SEVEN, null));
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertFalse(jassRules.isCardPlayable(HEARTS_KING, cardsInHand,
				cardsOnTable, new Ansage(DIAMONDS), false));
	}

	@Test
	public void testUndeUfe() {
		cardsOnTable.add(new PlayedCard(SPADES_SEVEN, null));
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertTrue(jassRules.isCardPlayable(SPADES_SIX, cardsInHand,
				cardsOnTable, new Ansage(UNDEUFE), false));
	}

	@Test
	public void testUndeUfe_notPlayable() {

		cardsOnTable.add(new PlayedCard(SPADES_SEVEN, null));
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		assertFalse(jassRules.isCardPlayable(HEARTS_KING, cardsInHand,
				cardsOnTable, new Ansage(UNDEUFE), false));
	}

	@Test
	public void testUnterTrumpfenNotAllowed() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_KING);
		Ansage heartsAnsage = new Ansage(HEARTS);
		cardsOnTable = new ArrayList<PlayedCard>();
		cardsOnTable.add(new PlayedCard(SPADES_SEVEN, null));
		cardsOnTable.add(new PlayedCard(HEARTS_ACE, null));

		assertFalse(jassRules.isCardPlayable(HEARTS_KING, cardsInHand,
				cardsOnTable, heartsAnsage, false));
	}

	@Test
	public void testUeberTrumpfenAllowed() {
		List<Card> cardsInHand = Arrays.asList(SPADES_SIX, HEARTS_ACE);
		Ansage heartsAnsage = new Ansage(HEARTS);
		cardsOnTable = new ArrayList<PlayedCard>();
		cardsOnTable.add(new PlayedCard(SPADES_SEVEN, null));
		cardsOnTable.add(new PlayedCard(HEARTS_KING, null));

		assertTrue(jassRules.isCardPlayable(HEARTS_ACE, cardsInHand,
				cardsOnTable, heartsAnsage, false));
	}

	@Test
	public void testUnterTrumpfenAllowed() {
		List<Card> cardsInHand = Arrays.asList(HEARTS_KING);
		Ansage heartsAnsage = new Ansage(HEARTS);
		cardsOnTable = new ArrayList<PlayedCard>();
		cardsOnTable.add(new PlayedCard(SPADES_SEVEN, null));
		cardsOnTable.add(new PlayedCard(HEARTS_ACE, null));

		assertTrue(jassRules.isCardPlayable(HEARTS_KING, cardsInHand,
				cardsOnTable, heartsAnsage, false));
	}
}
