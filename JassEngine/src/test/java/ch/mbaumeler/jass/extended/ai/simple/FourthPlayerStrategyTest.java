package ch.mbaumeler.jass.extended.ai.simple;

import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_SEVEN;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_TEN;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_TEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.impl.MatchImpl;

/* REVIEW NEEDED */ public class FourthPlayerStrategyTest {

	private FourthPlayerStrategy fourthPlayerStrategy;

	private Match match;
	private List<Card> cardsInHand;
	private List<Card> cardsOnTable;

	@Before
	public void setup() {
		fourthPlayerStrategy = new FourthPlayerStrategy();
		cardsInHand = new ArrayList<Card>();
		cardsOnTable = new ArrayList<Card>();
		match = mock(MatchImpl.class);
		when(match.isCardPlayable(any(Card.class))).thenReturn(true);
		when(match.getCardsOnTable()).thenReturn(cardsOnTable);
		when(match.getCards(any(PlayerToken.class))).thenReturn(cardsInHand);
		when(match.getAnsage()).thenReturn(new Ansage(CardSuit.HEARTS));
	}

	@Test
	public void testLastPlayerPlaysValueCard() {
		cardsOnTable.add(DIAMONDS_SIX);
		cardsOnTable.add(DIAMONDS_QUEEN);
		cardsOnTable.add(DIAMONDS_SEVEN);
		cardsInHand.add(DIAMONDS_EIGHT);
		cardsInHand.add(DIAMONDS_TEN);
		cardsInHand.add(DIAMONDS_QUEEN);
		Card card = fourthPlayerStrategy.getPlayableCard(cardsInHand, match);
		assertEquals(DIAMONDS_TEN, card);
	}

	@Test
	public void testLastPlayerPlaysValueCardOfOtherSuit() {
		cardsOnTable.add(DIAMONDS_SIX);
		cardsOnTable.add(DIAMONDS_QUEEN);
		cardsOnTable.add(DIAMONDS_SEVEN);
		cardsInHand.add(SPADES_KING);
		cardsInHand.add(SPADES_TEN);
		cardsInHand.add(SPADES_SIX);
		Card card = fourthPlayerStrategy.getPlayableCard(cardsInHand, match);
		assertEquals(SPADES_TEN, card);
	}

	@Test
	public void testLastPlayerPlaysWinningCardIfPossibleByPlayingTrumpf() {
		Card currentWinnerCard = DIAMONDS_QUEEN;
		cardsOnTable.add(currentWinnerCard);
		cardsOnTable.add(DIAMONDS_SIX);
		cardsOnTable.add(DIAMONDS_SEVEN);
		cardsInHand.add(SPADES_KING);
		cardsInHand.add(HEARTS_SIX);
		cardsInHand.add(SPADES_SIX);

		Card card = fourthPlayerStrategy.getPlayableCard(cardsInHand, match);
		assertEquals(HEARTS_SIX, card);
	}

	@Test
	public void testLastPlayerPlaysLeastPossibleWinningCard() {
		Card currentWinnerCard = DIAMONDS_QUEEN;
		cardsOnTable.add(currentWinnerCard);
		cardsOnTable.add(DIAMONDS_SIX);
		cardsOnTable.add(DIAMONDS_SEVEN);
		cardsInHand.add(SPADES_KING);
		cardsInHand.add(HEARTS_JACK);
		cardsInHand.add(HEARTS_SIX);

		Card card = fourthPlayerStrategy.getPlayableCard(cardsInHand, match);
		assertEquals(HEARTS_SIX, card);
	}

	@Test
	public void testIsResponsibleFor() {
		@SuppressWarnings("unchecked")
		List<Card> cardsOnTableMock = mock(List.class);
		when(cardsOnTableMock.size()).thenReturn(3);
		assertTrue(fourthPlayerStrategy.isResponsible(cardsOnTableMock));
		when(cardsOnTableMock.size()).thenReturn(0);
		assertFalse(fourthPlayerStrategy.isResponsible(cardsOnTableMock));
	}
}
