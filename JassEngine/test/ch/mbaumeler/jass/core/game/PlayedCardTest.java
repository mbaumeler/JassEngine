package ch.mbaumeler.jass.core.game;

import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_SEVEN;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_SIX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.card.Card;

public class PlayedCardTest {

	private PlayerToken playerToken;

	@Before
	public void setUp() {
		playerToken = PlayerToken.PLAYER0;
	}

	@Test
	public void testEquals() {
		PlayedCard playedCard = new PlayedCard(SPADES_SIX, playerToken);
		PlayedCard playedCard2 = new PlayedCard(SPADES_SIX, playerToken);
		assertEquals(playedCard, playedCard2);
	}

	@Test
	public void testNotEquals() {
		PlayedCard playedCard = new PlayedCard(SPADES_SIX, PlayerToken.PLAYER0);
		PlayedCard playedCard2 = new PlayedCard(SPADES_SIX, PlayerToken.PLAYER1);
		assertFalse(playedCard.equals(playedCard2));
	}

	@Test
	public void testGetCard() {
		Card card = CLUBS_SEVEN;
		PlayerToken player = PlayerToken.PLAYER0;
		PlayedCard playedCard = new PlayedCard(card, player);
		assertSame(card, playedCard.getCard());
	}

	@Test
	public void testToString() {
		Card card = CLUBS_SEVEN;
		PlayerToken player = PlayerToken.PLAYER0;
		PlayedCard playedCard = new PlayedCard(card, player);
		assertEquals(player + " played " + card, playedCard.toString());
	}

	@Test
	public void testGetPlayer() {
		PlayerToken player = PlayerToken.PLAYER0;
		PlayedCard playedCard = new PlayedCard(CLUBS_SEVEN, player);
		assertSame(player, playedCard.getPlayer());
	}

	@Test
	public void testHashcode() {
		Card card = CLUBS_SEVEN;
		PlayerToken player = PlayerToken.PLAYER0;
		PlayedCard playedCard = new PlayedCard(card, player);
		PlayedCard playedCard2 = new PlayedCard(card, player);
		assertEquals(playedCard.hashCode(), playedCard2.hashCode());
	}

	@Test
	public void testNotEqualsWithNull() {
		PlayerToken player = PlayerToken.PLAYER0;
		PlayedCard playedCard = new PlayedCard(CLUBS_SEVEN, player);
		assertFalse(playedCard.equals(null));
	}

	@Test
	public void testNotEqualsWithObject() {
		PlayerToken player = PlayerToken.PLAYER0;
		PlayedCard playedCard = new PlayedCard(CLUBS_SEVEN, player);
		assertFalse(playedCard.equals(new Object()));
	}

	@Test
	public void testEqualsWithSame() {
		PlayerToken player = PlayerToken.PLAYER0;
		PlayedCard playedCard = new PlayedCard(CLUBS_SEVEN, player);
		assertTrue(playedCard.equals(playedCard));
	}

	@Test
	public void testNotEqualsWithNotSamePlayer() {
		Card card = CLUBS_SEVEN;
		PlayedCard playedCard = new PlayedCard(card, PlayerToken.PLAYER0);
		PlayedCard playedCard2 = new PlayedCard(card, PlayerToken.PLAYER1);
		assertFalse(playedCard.equals(playedCard2));
	}

	@Test
	public void testNotEqualsWithNotSameCard() {
		PlayedCard playedCard = new PlayedCard(CLUBS_SEVEN, playerToken);
		PlayedCard playedCard2 = new PlayedCard(CLUBS_SIX, playerToken);
		assertFalse(playedCard.equals(playedCard2));
	}

	@Test
	public void testNotEqualsWithNotNullCard() {
		PlayedCard playedCard = new PlayedCard(null, playerToken);
		PlayedCard playedCard2 = new PlayedCard(CLUBS_SIX, playerToken);
		assertFalse(playedCard.equals(playedCard2));
	}

	@Test
	public void testNotEqualsWithNotNullCardNullOtherPlayer() {
		PlayerToken player = PlayerToken.PLAYER0;
		PlayedCard playedCard = new PlayedCard(CLUBS_SIX, player);
		PlayedCard playedCard2 = new PlayedCard(null, player);
		assertFalse(playedCard.equals(playedCard2));
	}

	@Test
	public void testNotEqualsNullPlayer() {
		Card card = CLUBS_SEVEN;
		PlayedCard playedCard = new PlayedCard(card, null);
		PlayedCard playedCard2 = new PlayedCard(card, PlayerToken.PLAYER0);
		assertFalse(playedCard.equals(playedCard2));
	}

	@Test
	public void testNotEqualsNullPlayerOther() {
		Card card = CLUBS_SEVEN;
		PlayedCard playedCard = new PlayedCard(card, PlayerToken.PLAYER0);
		PlayedCard playedCard2 = new PlayedCard(card, null);
		assertFalse(playedCard.equals(playedCard2));
	}

}
