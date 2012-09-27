package ch.mbaumeler.jass.core.game.impl;

import javax.inject.Inject;

import ch.mbaumeler.jass.core.Game;
import ch.mbaumeler.jass.core.GameState;
import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.MatchState;
import ch.mbaumeler.jass.core.game.MatchFactory;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.Score;

/* REVIEW NEEDED */public class GameImpl implements Game {

	private Match currentMatch;

	private final Score totalScore;

	private final MatchFactory matchFactory;

	private PlayerToken startingPlayerIndex = PlayerToken.PLAYER0;

	@Inject
	public GameImpl(MatchFactory matchFactory) {
		this.matchFactory = matchFactory;
		this.totalScore = new Score();
		createMatch();

	}

	public GameImpl(GameState gameState, MatchFactory matchFactory) {
		this(matchFactory);
		totalScore.addScore(PlayerToken.PLAYER0, gameState.getTeam1());
		totalScore.addScore(PlayerToken.PLAYER1, gameState.getTeam2());
		currentMatch = matchFactory.createMatch(gameState.getMatchState());
	}

	@Override
	public void createMatch() {

		if (currentMatch == null || getCurrentMatch().isComplete()) {
			if (currentMatch != null) {
				totalScore.add(getCurrentMatch().getScore());
			}

			Match match = matchFactory.createMatch(startingPlayerIndex);
			currentMatch = match;
			startingPlayerIndex = PlayerToken.values()[(startingPlayerIndex.ordinal() + 1) % 4];

		} else {
			throw new IllegalStateException("Current match is not complete.");
		}
	}

	@Override
	public Match getCurrentMatch() {
		return currentMatch;
	}

	@Override
	public Score getTotalScore() {
		Score score = new Score();
		score.add(totalScore);
		score.add(getCurrentMatch().getScore());
		return score;
	}

	@Override
	public GameState createGameState() {
		int team1 = totalScore.getPlayerScore(PlayerToken.PLAYER0);
		int team2 = totalScore.getOppositeScore(PlayerToken.PLAYER0);
		MatchState matchState = currentMatch.createMatchState();
		return new GameState(team1, team2, matchState);
	}
}
