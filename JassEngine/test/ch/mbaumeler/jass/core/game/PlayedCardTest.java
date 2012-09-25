package ch.mbaumeler.jass.core.game;

import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_SEVEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.card.CardValue;

public class PlayedCardTest {

	private PlayerToken playerToken;

	@Before
	public void setUp() {
		playerToken = PlayerToken.PLAYER0;
	}

	@Test
	public void testEquals() {
		PlayedCard playedCard = new PlayedCard(CardSuit.SPADES, CardValue.SIX, playerToken);
		PlayedCard playedCard2 = new PlayedCard(CardSuit.SPADES, CardValue.SIX, playerToken);
		assertEquals(playedCard, playedCard2);
	}

	@Test
	public void testNotEquals() {
		PlayedCard playedCard = new PlayedCard(CardSuit.SPADES, CardValue.SIX, PlayerToken.PLAYER0);
		PlayedCard playedCard2 = new PlayedCard(CardSuit.SPADES, CardValue.SIX, PlayerToken.PLAYER1);
		assertFalse(playedCard.equals(playedCard2));
	}

	@Test
	public void testGetCardSuit() {
		PlayedCard playedCard = new PlayedCard(CardSuit.CLUBS, CardValue.SEVEN, PlayerToken.PLAYER0);
		assertSame(CardSuit.CLUBS, playedCard.getSuit());
	}

	@Test
	public void testGetValue() {
		PlayedCard playedCard = new PlayedCard(CardSuit.CLUBS, CardValue.SEVEN, PlayerToken.PLAYER0);
		assertSame(CardValue.SEVEN, playedCard.getValue());
	}

	@Test
	public void testToString() {
		PlayedCard card = CLUBS_SEVEN;
		assertEquals("CardSuit: " + card.getSuit() + ", CardValue: " + card.getValue() + ", PlayerToken: "
				+ PlayerToken.PLAYER0, card.toString());
	}

	@Test
	public void testGetPlayer() {
		assertSame(PlayerToken.PLAYER0, CLUBS_SEVEN.getPlayer());
	}

	@Test
	public void testHashcode() {
		PlayedCard playedCard = new PlayedCard(CardSuit.CLUBS, CardValue.SEVEN, PlayerToken.PLAYER0);
		PlayedCard playedCard2 = new PlayedCard(CardSuit.CLUBS, CardValue.SEVEN, PlayerToken.PLAYER0);
		assertEquals(playedCard.hashCode(), playedCard2.hashCode());
	}

	@Test
	public void testNotEqualsWithNull() {
		PlayedCard playedCard = new PlayedCard(CardSuit.CLUBS, CardValue.SEVEN, PlayerToken.PLAYER0);
		assertFalse(playedCard.equals(null));
	}

	@Test
	public void testNotEqualsWithObject() {
		PlayedCard playedCard = new PlayedCard(CardSuit.CLUBS, CardValue.SEVEN, PlayerToken.PLAYER0);
		assertFalse(playedCard.equals(new Object()));
	}

	@Test
	public void testEqualsWithSame() {
		PlayedCard playedCard = new PlayedCard(CardSuit.CLUBS, CardValue.SEVEN, PlayerToken.PLAYER0);
		assertTrue(playedCard.equals(playedCard));
	}

	@Test
	public void testNotEqualsWithNotSamePlayer() {
		PlayedCard playedCard = new PlayedCard(CardSuit.CLUBS, CardValue.SEVEN, PlayerToken.PLAYER0);
		PlayedCard playedCard2 = new PlayedCard(CardSuit.CLUBS, CardValue.SEVEN, PlayerToken.PLAYER1);
		assertFalse(playedCard.equals(playedCard2));
	}

	@Test
	public void testNotEqualsWithNotSameCardValue() {
		PlayedCard playedCard = new PlayedCard(CardSuit.CLUBS, CardValue.SEVEN, PlayerToken.PLAYER0);
		PlayedCard playedCard2 = new PlayedCard(CardSuit.CLUBS, CardValue.SIX, PlayerToken.PLAYER0);
		assertFalse(playedCard.equals(playedCard2));
	}

	@Test
	public void testNotEqualsWithNotSameCardSuit() {
		PlayedCard playedCard = new PlayedCard(CardSuit.HEARTS, CardValue.SEVEN, PlayerToken.PLAYER0);
		PlayedCard playedCard2 = new PlayedCard(CardSuit.CLUBS, CardValue.SEVEN, PlayerToken.PLAYER0);
		assertFalse(playedCard.equals(playedCard2));
	}

}
