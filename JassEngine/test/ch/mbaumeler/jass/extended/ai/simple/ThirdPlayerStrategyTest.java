package ch.mbaumeler.jass.extended.ai.simple;

import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_SEVEN;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_SIX;
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
import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.Card;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.impl.MatchImpl;

public class ThirdPlayerStrategyTest {

	private ThirdPlayerStrategy thirdPlayerStrategy;

	private Match match;
	private List<Card> cardsInHand;
	private List<Card> cardsOnTable;

	@Before
	public void setup() {
		thirdPlayerStrategy = new ThirdPlayerStrategy();
		cardsInHand = new ArrayList<Card>();
		cardsOnTable = new ArrayList<Card>();
		match = mock(MatchImpl.class);
		when(match.isCardPlayable(any(Card.class))).thenReturn(true);
		when(match.getCardsOnTable()).thenReturn(cardsOnTable);
		when(match.getCards(any(PlayerToken.class))).thenReturn(cardsInHand);
		when(match.getAnsage()).thenReturn(new Ansage(CardSuit.HEARTS));
	}

	@Test
	public void testHaveThirdPlayerHasHigherCardThenFirstPlayer() {
		cardsOnTable.add(DIAMONDS_SIX);
		cardsOnTable.add(DIAMONDS_SEVEN);
		cardsInHand.add(DIAMONDS_EIGHT);
		cardsInHand.add(DIAMONDS_QUEEN);

		Card card = thirdPlayerStrategy.getPlayableCard(cardsInHand, match);

		assertEquals(DIAMONDS_QUEEN, card);
	}

	@Test
	public void testIsResponsibleFor() {
		@SuppressWarnings("unchecked")
		List<Card> cardsOnTableMock = mock(List.class);
		when(cardsOnTableMock.size()).thenReturn(2);
		assertTrue(thirdPlayerStrategy.isResponsible(cardsOnTableMock));
		when(cardsOnTableMock.size()).thenReturn(0);
		assertFalse(thirdPlayerStrategy.isResponsible(cardsOnTableMock));
	}

}
