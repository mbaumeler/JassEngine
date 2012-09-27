package ch.mbaumeler.jass.core.game.impl;

import javax.inject.Inject;

import ch.mbaumeler.jass.core.Game;
import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.MatchFactory;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.PlayerTokenRepository;
import ch.mbaumeler.jass.core.game.Score;

/* REVIEW NEEDED */public class GameImpl implements Game {

	private Match currentMatch;

	private final Score totalScore;

	private final PlayerTokenRepository playerRepository;

	private final MatchFactory matchFactory;

	private int startingPlayerIndex = 0;

	@Inject
	public GameImpl(PlayerTokenRepository players, MatchFactory matchFactory) {
		this.matchFactory = matchFactory;
		this.playerRepository = players;
		this.totalScore = new Score(playerRepository);
		createMatch();

	}

	@Override
	public void createMatch() {

		if (currentMatch == null || getCurrentMatch().isComplete()) {
			if (currentMatch != null) {
				totalScore.add(getCurrentMatch().getScore());
			}

			PlayerToken startingPlayer = playerRepository.getAll().get(startingPlayerIndex);
			Match match = matchFactory.createMatch(startingPlayer);
			currentMatch = match;
			startingPlayerIndex = ++startingPlayerIndex % 4;
		} else {
			throw new IllegalStateException("Current match is not complete.");
		}
	}

	@Override
	public PlayerTokenRepository getPlayerRepository() {
		return playerRepository;
	}

	@Override
	public Match getCurrentMatch() {
		return currentMatch;
	}

	@Override
	public Score getTotalScore() {
		Score score = new Score(playerRepository);
		score.add(totalScore);
		score.add(getCurrentMatch().getScore());
		return score;
	}

}
