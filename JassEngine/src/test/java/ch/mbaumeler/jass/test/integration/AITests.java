package ch.mbaumeler.jass.test.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.CardFactory;
import ch.mbaumeler.jass.core.Game;
import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.bootstrap.JassModule;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.Score;
import ch.mbaumeler.jass.core.game.impl.GameImpl;
import ch.mbaumeler.jass.extended.ai.simple.SimpleStrategy;
import ch.mbaumeler.jass.extended.ai.simple.SimpleStrategyEngine;

import com.google.inject.Guice;
import com.google.inject.Injector;

/* REVIEW NEEDED */public class AITests {

	private static final int MATCHES_TO_PLAY = 2000;

	private Game game;

	private SimpleStrategy simpleStrategy;

	private final int CURRENT_TEAM_SCORE_1 = 207284;
	private final int CURRENT_TEAM_SCORE_2 = 120116;

	@Before
	public void setup() {

		Injector injector = Guice.createInjector(new JassModule() {
			@Override
			public void bindCardFactoryOverride() {
				bind(CardFactory.class).to(PredictableCardFactory.class);
			}
		});
		game = injector.getInstance(GameImpl.class);

		simpleStrategy = new SimpleStrategyEngine().create();
	}

	@Test
	public void testGetTotalScore() {
		for (int i = 0; i < MATCHES_TO_PLAY; i++) {
			Match match = game.getCurrentMatch();
			playRound(match);
		}

		Score score = game.getTotalScore();
		PlayerToken firstPlayer = PlayerToken.PLAYER0;

		int scoreTeam1 = score.getPlayerScore(firstPlayer);
		int scoreTeam2 = score.getOppositeScore(firstPlayer);
		System.out.println(scoreTeam1 + " " + scoreTeam2);
		assertTrue("The strategy got worse!",
				(scoreTeam1 - scoreTeam2 >= (CURRENT_TEAM_SCORE_1 - CURRENT_TEAM_SCORE_2)));

		boolean changed = CURRENT_TEAM_SCORE_1 != scoreTeam1;
		if (changed) {
			System.out.println("***************************************************************");
			System.out.println("* AI Improved: Adjust " + scoreTeam1 + " as new scoreValue and " + scoreTeam2 + " *");
			System.out.println("***************************************************************");
		}
		assertFalse(changed);
	}

	private void playRound(Match match) {
		for (int i = 0; i < 9; i++) {
			for (int m = 0; m < 4; m++) {
				PlayerToken player = match.getActivePlayer();
				ansageStrategy(match, player);
				playStrategy(match);
			}
			match.collectCards();
		}
		game.createMatch();
	}

	private void playStrategy(Match match) {
		if (PlayerToken.isTeam1(match.getActivePlayer())) {
			match.playCard(simpleStrategy.getCardToPlay(match));
		} else {
			match.playCard(getFirstPlayableCard(match));
		}
	}

	private void ansageStrategy(Match match, PlayerToken player) {
		if (match.getAnsage() == null) {
			if (PlayerToken.isTeam1(player)) {
				match.setAnsage(simpleStrategy.getAnsage(match));
			} else {
				match.setAnsage(new Ansage(CardSuit.HEARTS));
			}
		}
	}

	private Card getFirstPlayableCard(Match match) {
		PlayerToken activePlayer = match.getActivePlayer();
		List<Card> cards = match.getCards(activePlayer);

		for (Card card : cards) {
			if (match.isCardPlayable(card)) {
				return card;
			}
		}
		throw new IllegalStateException("Player does not have a playable card: " + cards);
	}

}
