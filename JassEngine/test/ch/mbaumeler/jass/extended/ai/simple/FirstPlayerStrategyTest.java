package ch.mbaumeler.jass.extended.ai.simple;

import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_SIX;
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
import ch.mbaumeler.jass.core.game.PlayedCard;
import ch.mbaumeler.jass.core.game.PlayerToken;

public class FirstPlayerStrategyTest {

	private FirstPlayerStrategy firstPlayerStrategy;

	private Match match;
	private List<PlayedCard> cardsInHand;
	private List<PlayedCard> cardsOnTable;

	@Before
	public void setup() {
		firstPlayerStrategy = new FirstPlayerStrategy();
		cardsInHand = new ArrayList<PlayedCard>();
		cardsOnTable = new ArrayList<PlayedCard>();
		match = mock(Match.class);
		when(match.isCardPlayable(any(PlayedCard.class))).thenReturn(true);
		when(match.getCardsOnTable()).thenReturn(cardsOnTable);
		when(match.getCards(any(PlayerToken.class))).thenReturn(cardsInHand);
		when(match.getAnsage()).thenReturn(new Ansage(CardSuit.HEARTS));
	}

	@Test
	public void testPlaysTrumpf() {
		cardsInHand.add(DIAMONDS_EIGHT);
		cardsInHand.add(DIAMONDS_JACK);

		PlayedCard card = firstPlayerStrategy.getPlayableCard(cardsInHand, match);
		assertEquals(DIAMONDS_JACK, card);
	}

	@Test
	public void testPlayTrumpfJackAtBeginning() {
		cardsInHand.add(DIAMONDS_EIGHT);
		cardsInHand.add(HEARTS_JACK);
		cardsInHand.add(DIAMONDS_QUEEN);

		PlayedCard card = firstPlayerStrategy.getPlayableCard(cardsInHand, match);

		assertEquals(HEARTS_JACK, card);
	}

	@Test
	public void testPlayAss() {
		cardsInHand.add(DIAMONDS_ACE);
		cardsInHand.add(DIAMONDS_QUEEN);

		PlayedCard card = firstPlayerStrategy.getPlayableCard(cardsInHand, match);

		assertEquals(DIAMONDS_ACE, card);
	}

	@Test
	public void testTrumpfSix() {
		cardsInHand.add(DIAMONDS_ACE);
		cardsInHand.add(DIAMONDS_QUEEN);
		cardsInHand.add(HEARTS_SIX);
		PlayedCard card = firstPlayerStrategy.getPlayableCard(cardsInHand, match);

		assertEquals(HEARTS_SIX, card);
	}

	@Test
	public void testIsResponsibleFor() {
		@SuppressWarnings("unchecked")
		List<PlayedCard> cardsOnTableMock = mock(List.class);

		when(cardsOnTableMock.size()).thenReturn(0);
		assertTrue(firstPlayerStrategy.isResponsible(cardsOnTableMock));
		when(cardsOnTableMock.size()).thenReturn(1);
		assertFalse(firstPlayerStrategy.isResponsible(cardsOnTableMock));
		when(cardsOnTableMock.size()).thenReturn(4);
		assertTrue(firstPlayerStrategy.isResponsible(cardsOnTableMock));
	}
}
