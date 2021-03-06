package ch.mbaumeler.jass.core.game.impl;

import static ch.mbaumeler.jass.core.card.CardSuit.CLUBS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.Game;
import ch.mbaumeler.jass.core.JassEngine;
import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.Score;

/* REVIEW NEEDED */public class GameImplTest {

	private Game game;

	@Before
	public void setup() {
		game = new JassEngine().createJassGame();
		game.createMatch();
	}

	@Test
	public void testGetCurrentMatch() {
		Match match = game.getMatch();
		playMatch(match);
		game.createMatch();
		Match match2 = game.getMatch();
		assertNotSame(match, match2);
	}

	@Test
	public void testStartingPlayer() {
		Match match = game.getMatch();
		assertEquals(PlayerToken.PLAYER0, match.getActivePlayer());
		playMatch(match);
		game.createMatch();
		match = game.getMatch();
		assertEquals(PlayerToken.PLAYER1, match.getActivePlayer());
		playMatch(match);
		game.createMatch();
		match = game.getMatch();
		assertEquals(PlayerToken.PLAYER2, match.getActivePlayer());
		playMatch(match);
		game.createMatch();
		match = game.getMatch();
		assertEquals(PlayerToken.PLAYER3, match.getActivePlayer());
		playMatch(match);
		game.createMatch();
		match = game.getMatch();
		assertEquals(PlayerToken.PLAYER0, match.getActivePlayer());
	}

	@Test
	public void testGetTotalScoreOneRound() {
		Match match = game.getMatch();
		playMatch(match);
		assertTrue(match.isComplete());
		assertEquals(9, match.getRoundsCompleted());
		Score score = game.getTotalScore();
		PlayerToken firstPlayer = PlayerToken.PLAYER0;
		assertEquals(157, score.getPlayerScore(firstPlayer) + score.getOppositeScore(firstPlayer));
	}

	@Test
	public void testGetTotalScore() {
		Match match = game.getMatch();
		playMatch(match);
		game.createMatch();
		Match match2 = game.getMatch();
		playMatch(match2);
		Score score = game.getTotalScore();
		PlayerToken firstPlayer = PlayerToken.PLAYER0;
		assertEquals(157 * 2, score.getPlayerScore(firstPlayer) + score.getOppositeScore(firstPlayer));
	}

	private void playMatch(Match match) {
		match.setAnsage(new Ansage(CLUBS));
		for (int i = 0; i < 9; i++) {
			for (int m = 0; m < 4; m++) {
				PlayerToken player = match.getActivePlayer();
				match.playCard(getFirstPlayableCard(match, match.getCards(player)));
			}
			match.collectCards();
		}
	}

	private Card getFirstPlayableCard(Match match, List<Card> cards) {

		for (Card card : cards) {
			if (match.isCardPlayable(card)) {
				return card;
			}
		}
		throw new IllegalStateException("Player does not have a playable card: " + cards);
	}
}
