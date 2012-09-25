package ch.mbaumeler.jass.test.integration;

import static ch.mbaumeler.jass.core.card.CardSuit.CLUBS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.CardFactory;
import ch.mbaumeler.jass.core.Game;
import ch.mbaumeler.jass.core.JassEngine;
import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.MatchState;
import ch.mbaumeler.jass.core.bootstrap.JassModule;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.ScoreUtil;
import ch.mbaumeler.jass.core.game.impl.GameImpl;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class MatchIntegrationTest {

	private Match match;
	private List<PlayerToken> playerList;
	private static final Ansage DEFAULT_ANSAGE = new Ansage(CLUBS);

	@Before
	public void setup() {

		Injector injector = Guice.createInjector(new JassModule() {
			@Override
			public void bindCardFactoryOverride() {
				bind(CardFactory.class).to(PredictableCardFactory.class);
			}
		});
		Game game = injector.getInstance(GameImpl.class);
		match = game.getCurrentMatch();
		playerList = game.getPlayerRepository().getAll();
	}

	@Test
	public void testGetCards() {

		assertEquals(9, match.getCards(playerList.get(0)).size());
		assertEquals(9, match.getCards(playerList.get(0)).size());
		assertEquals(9, match.getCards(playerList.get(0)).size());
		assertEquals(9, match.getCards(playerList.get(0)).size());

		Card firstP1 = match.getCards(playerList.get(0)).get(0);
		Card firstP2 = match.getCards(playerList.get(1)).get(0);
		assertFalse(firstP1.equals(firstP2));
	}

	@Test
	public void testIsActivePlayer() {
		assertEquals(playerList.get(0), match.getActivePlayer());
	}

	@Test
	public void testPlayFirstCard() {
		assertEquals(0, match.getCardsOnTable().size());
		Card cardToPlay = match.getCards(playerList.get(0)).get(0);
		match.setAnsage(DEFAULT_ANSAGE);
		match.playCard(cardToPlay);
		assertEquals(8, match.getCards(playerList.get(0)).size());
		assertEquals(cardToPlay, match.getCardsOnTable().get(0));
		assertEquals(playerList.get(1), match.getActivePlayer());
	}

	@Test
	public void testGetActivePlayer() {
		playAllCards();
		assertTrue(match.isComplete());
		ScoreUtil scoreUtil = new ScoreUtil();
		for (int i = 0; i < 8; i++) {
			List<Card> cards = match.getCardsFromRound(i);

			Card winnerCard = scoreUtil.getWinnerCard(cards, DEFAULT_ANSAGE);
			Card firstPlayerNextRound = match.getCardsFromRound(i + 1).get(0);
			assertEquals(winnerCard.getPlayer(), firstPlayerNextRound.getPlayer());
		}
	}

	@Test
	public void testPlayCardFromOtherPlayer() {
		Card cardFromPlayer2 = match.getCards(playerList.get(2)).get(0);
		match.setAnsage(new Ansage(CardSuit.HEARTS));

		try {
			match.playCard(cardFromPlayer2);

		} catch (IllegalArgumentException e) {
			// expted
		}
	}

	@Test
	public void testStoreMatchState() {
		playAllCards();
		MatchState matchState = match.createMatchState();
		Match createdMatch = new JassEngine().createMatchFromMatchState(matchState);
		assertEquals(match.isComplete(), createdMatch.isComplete());
		assertEquals(match.getAnsage(), createdMatch.getAnsage());
		for (int i = 0; i < match.getRoundsCompleted(); i++) {
			assertEquals(match.getCardsFromRound(i), createdMatch.getCardsFromRound(i));
		}
	}

	@Test
	public void testPlayRound() {
		assertFalse(match.isComplete());
		playAllCards();
		assertTrue(match.isComplete());
	}

	private Card getFirstPlayableCard(List<Card> cards) {
		for (Card card : cards) {
			if (match.isCardPlayable(card)) {
				return card;
			}
		}
		throw new IllegalStateException("Player does not have a playable card: " + cards);
	}

	private void playAllCards() {
		ScoreUtil scoreUtil = new ScoreUtil();
		match.setAnsage(new Ansage(CLUBS));
		for (int i = 0; i < 36; i++) {
			PlayerToken player = match.getActivePlayer();
			if (i % 4 == 0 && i > 4) {
				assertEquals(player, scoreUtil.getWinnerCard(match.getCardsFromRound((i - 1) / 4), match.getAnsage())
						.getPlayer());
			}
			Card card = getFirstPlayableCard(match.getCards(player));
			match.playCard(card);
		}
	}

}
