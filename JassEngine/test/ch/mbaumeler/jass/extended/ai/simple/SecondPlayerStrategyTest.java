package ch.mbaumeler.jass.extended.ai.simple;

import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_QUEEN;
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

/* REVIEW NEEDED */ public class SecondPlayerStrategyTest {

	private SecondPlayerStrategy secondPlayerStrategy;

	private Match match;
	private List<Card> cardsInHand;
	private List<Card> cardsOnTable;

	@Before
	public void setup() {
		secondPlayerStrategy = new SecondPlayerStrategy();
		cardsInHand = new ArrayList<Card>();
		cardsOnTable = new ArrayList<Card>();
		match = mock(MatchImpl.class);
		when(match.isCardPlayable(any(Card.class))).thenReturn(true);
		when(match.getCardsOnTable()).thenReturn(cardsOnTable);
		when(match.getCards(any(PlayerToken.class))).thenReturn(cardsInHand);
		when(match.getAnsage()).thenReturn(new Ansage(CardSuit.HEARTS));
	}

	@Test
	public void testHaveSecondPlayerHigherCardThenFirstPlayer() {
		match.setAnsage(new Ansage(CardSuit.HEARTS));
		cardsOnTable.add(DIAMONDS_JACK);
		cardsInHand.add(DIAMONDS_EIGHT);
		cardsInHand.add(DIAMONDS_QUEEN);

		Card card = secondPlayerStrategy.getPlayableCard(cardsInHand, match);

		assertEquals(DIAMONDS_QUEEN, card);
	}

	@Test
	public void testIsResponsibleFor() {
		@SuppressWarnings("unchecked")
		List<Card> cardsOnTableMock = mock(List.class);
		when(cardsOnTableMock.size()).thenReturn(1);
		assertTrue(secondPlayerStrategy.isResponsible(cardsOnTableMock));
		when(cardsOnTableMock.size()).thenReturn(0);
		assertFalse(secondPlayerStrategy.isResponsible(cardsOnTableMock));
	}
}
