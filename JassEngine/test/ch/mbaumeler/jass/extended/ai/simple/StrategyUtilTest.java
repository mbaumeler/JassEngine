package ch.mbaumeler.jass.extended.ai.simple;

import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_SEVEN;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_SIX;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.Card;
import ch.mbaumeler.jass.core.game.impl.MatchImpl;
import ch.mbaumeler.jass.test.util.CardDomain;

public class StrategyUtilTest {

	private StrategyUtil strategyUtil;
	private Match match;

	@Before
	public void setUp() throws Exception {
		strategyUtil = new StrategyUtil();
		match = mock(MatchImpl.class);
	}

	@Test
	public void testGetHighestCardOfSameColor() {

		Card firstPlayedCard = CardDomain.DIAMONDS_EIGHT;

		List<Card> cardsInHand = Arrays.asList(DIAMONDS_SIX, DIAMONDS_SEVEN, DIAMONDS_QUEEN);
		Card highestCardOfSameColor = strategyUtil.getHighestCardOfSameColor(firstPlayedCard, new Ansage(
				CardSuit.CLUBS), cardsInHand);

		assertEquals(highestCardOfSameColor, DIAMONDS_QUEEN);
	}

	@Test
	public void testGetLeastValueableCard_noTrumpf() {

		when(match.getAnsage()).thenReturn(new Ansage(CardSuit.HEARTS));
		when(match.isCardPlayable(any(Card.class))).thenReturn(true);

		List<Card> cardsInHand = Arrays.asList(DIAMONDS_KING, DIAMONDS_ACE, DIAMONDS_SIX, DIAMONDS_SEVEN,
				DIAMONDS_QUEEN);

		Card card = strategyUtil.getLeastPlayableCard(cardsInHand, match);
		assertEquals(DIAMONDS_SIX, card);

	}

	@Test
	public void testGetLeastValueableCard_withTrumpf() {

		when(match.getAnsage()).thenReturn(new Ansage(CardSuit.HEARTS));
		when(match.isCardPlayable(any(Card.class))).thenReturn(true);

		List<Card> cardsInHand = Arrays.asList(DIAMONDS_KING, HEARTS_SIX, DIAMONDS_ACE, HEARTS_JACK,
				DIAMONDS_SIX, DIAMONDS_SEVEN, DIAMONDS_QUEEN);

		Card card = strategyUtil.getLeastPlayableCard(cardsInHand, match);
		assertEquals(DIAMONDS_SIX, card);

	}

}
