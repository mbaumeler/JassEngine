package ch.mbaumeler.jass.core.game.impl;

import static ch.mbaumeler.jass.core.card.CardSuit.CLUBS;
import static ch.mbaumeler.jass.core.card.CardSuit.DIAMONDS;
import static ch.mbaumeler.jass.core.card.CardSuit.HEARTS;
import static ch.mbaumeler.jass.core.card.CardSuit.SPADES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.card.CardValue;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.Ansage.SpielModi;
import ch.mbaumeler.jass.core.game.JassRules;
import ch.mbaumeler.jass.core.game.PlayedCard;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.PlayerTokenRepository;
import ch.mbaumeler.jass.core.game.ScoreRules;
import ch.mbaumeler.jass.core.game.ScoreUtil;
import ch.mbaumeler.jass.core.game.wys.WysRules;
import ch.mbaumeler.jass.core.game.wys.WysScoreRule;
import ch.mbaumeler.jass.core.game.wys.WysStore;

public class MatchImplTest {

	private Match match;
	private List<PlayerToken> playerList;
	private ScoreUtil scoreUtilMock;
	private WysScoreRule wysScoreRuleMock;
	private JassRules jassRulesMock;
	private WysRules wysRuleMock;
	private List<PlayedCard> cards;

	@Before
	public void setup() {
		PlayerTokenRepository playerRepository = new PlayerTokenRepository();
		playerList = playerRepository.getAll();
		scoreUtilMock = mock(ScoreUtil.class);
		wysRuleMock = mock(WysRules.class);
		wysScoreRuleMock = mock(WysScoreRule.class);
		cards = createSortedDeck();
		jassRulesMock = mock(JassRules.class);
		match = new MatchImpl(playerRepository, playerList.get(0), scoreUtilMock, jassRulesMock, cards,
				new ScoreRules(), wysRuleMock, wysScoreRuleMock);
	}

	private List<PlayedCard> createSortedDeck() {
		List<PlayedCard> result = new ArrayList<PlayedCard>(36);
		result.addAll(sortedDeck(DIAMONDS, PlayerToken.PLAYER0));
		result.addAll(sortedDeck(SPADES, PlayerToken.PLAYER1));
		result.addAll(sortedDeck(HEARTS, PlayerToken.PLAYER2));
		result.addAll(sortedDeck(CLUBS, PlayerToken.PLAYER3));
		return result;
	}

	private List<PlayedCard> sortedDeck(CardSuit cardSuit, PlayerToken playerToken) {
		List<PlayedCard> result = new ArrayList<PlayedCard>();

		for (CardValue value : CardValue.values()) {
			result.add(new PlayedCard(cardSuit, value, playerToken));
		}
		return result;
	}

	@Test
	public void testGetCardsFromPlayer0() {
		List<PlayedCard> cardsPlayer0 = match.getCards(playerList.get(0));
		assertEquals(9, cardsPlayer0.size());
		assertEquals(cards.subList(0, 9), cardsPlayer0);
	}

	@Test
	public void testGetCardsFromPlayer1() {
		List<PlayedCard> cardsPlayer0 = match.getCards(playerList.get(1));
		assertEquals(9, cardsPlayer0.size());
		assertEquals(cards.subList(9, 18), cardsPlayer0);
	}

	@Test
	public void getScore() {
		match.getScore();
		verify(scoreUtilMock).calculateScore(eq(match), any(WysStore.class));
	}

	@Test
	public void setAndGetAnsage() {
		Ansage ansage = new Ansage(SpielModi.OBENABE);
		match.setAnsage(ansage);
		assertSame(ansage, match.getAnsage());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setAnsageTwice() {
		Ansage ansage = new Ansage(SpielModi.OBENABE);
		match.setAnsage(ansage);
		match.setAnsage(ansage);
	}

	@Test
	public void testIsCardPlayable() {
		PlayedCard card = cards.get(0);
		Ansage ansage = new Ansage(SpielModi.OBENABE);
		match.setAnsage(ansage);
		match.isCardPlayable(card);
		verify(jassRulesMock).isCardPlayable(card, cards.subList(0, 9), new ArrayList<PlayedCard>(), ansage, true);
	}

	@Test
	public void testPlayCard() {
		PlayedCard card = cards.get(0);
		Ansage ansage = new Ansage(SpielModi.OBENABE);
		match.setAnsage(ansage);
		when(jassRulesMock.isCardPlayable(card, cards.subList(0, 9), new ArrayList<PlayedCard>(), ansage, true))
				.thenReturn(true);
		match.playCard(card);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPlayCardWithNull() {
		match.playCard(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPlayCardNotPlayable() {
		when(
				jassRulesMock.isCardPlayable(any(PlayedCard.class), anyListOf(PlayedCard.class),
						anyListOf(PlayedCard.class), any(Ansage.class), anyBoolean())).thenReturn(false);
		match.playCard(cards.get(0));
	}

	@Test
	public void testGetCardsOnTable() {
		PlayedCard card = cards.get(0);
		Ansage ansage = new Ansage(SpielModi.OBENABE);
		match.setAnsage(ansage);
		when(jassRulesMock.isCardPlayable(card, cards.subList(0, 9), new ArrayList<PlayedCard>(), ansage, true))
				.thenReturn(true);
		match.playCard(card);

		List<PlayedCard> cardsOnTable = match.getCardsOnTable();
		assertEquals(1, cardsOnTable.size());
		PlayedCard playedCard = cardsOnTable.get(0);
		assertEquals(card, playedCard);
		assertEquals(playerList.get(0), playedCard.getPlayer());
	}

	@Test
	public void testGetActivePlayer() {
		PlayedCard card = cards.get(0);
		Ansage ansage = new Ansage(SpielModi.OBENABE);
		match.setAnsage(ansage);
		when(
				jassRulesMock.isCardPlayable(any(PlayedCard.class), anyListOf(PlayedCard.class),
						anyListOf(PlayedCard.class), eq(ansage), anyBoolean())).thenReturn(true);

		assertEquals(playerList.get(0), match.getActivePlayer());
		match.playCard(card);
		assertEquals(playerList.get(1), match.getActivePlayer());
	}

	@Test
	public void testIsComplete() {
		Ansage ansage = new Ansage(SpielModi.OBENABE);
		PlayedCard winnerCard = mock(PlayedCard.class);
		match.setAnsage(ansage);
		when(
				jassRulesMock.isCardPlayable(any(PlayedCard.class), anyListOf(PlayedCard.class),
						anyListOf(PlayedCard.class), eq(new Ansage(SpielModi.OBENABE)), anyBoolean())).thenReturn(true);
		when(scoreUtilMock.getWinnerCard(anyListOf(PlayedCard.class), eq(ansage))).thenReturn(winnerCard);

		assertFalse(match.isComplete());

		for (int i = 0; i < 36; i++) {
			match.playCard(match.getCards(match.getActivePlayer()).get(0));
		}
		assertTrue(match.isComplete());
	}

	@Test
	public void schiebe() {
		assertEquals(PlayerToken.PLAYER0, match.getActivePlayer());
		match.schiebe();
		assertTrue(match.isGeschoben());
		assertEquals(PlayerToken.PLAYER2, match.getActivePlayer());
		match.setAnsage(new Ansage(CardSuit.HEARTS));
		assertEquals(PlayerToken.PLAYER0, match.getActivePlayer());
	}

	@Test
	public void testSchiebeAfterAnsage() {
		match.setAnsage(new Ansage(CardSuit.CLUBS));
		try {
			match.schiebe();
			fail();
		} catch (IllegalStateException e) {
			assertEquals("Ansage already set", e.getMessage());
		}
	}
}
