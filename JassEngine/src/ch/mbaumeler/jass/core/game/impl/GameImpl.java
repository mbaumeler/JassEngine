package ch.mbaumeler.jass.core.game.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ch.mbaumeler.jass.core.Game;
import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.MatchFactory;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.PlayerTokenRepository;
import ch.mbaumeler.jass.core.game.Score;

public class GameImpl implements Game {

	private final List<MatchImpl> matchs;

	private final PlayerTokenRepository playerRepository;

	private final MatchFactory matchFactory;

	@Inject
	public GameImpl(PlayerTokenRepository players, MatchFactory matchFactory) {
		this.matchFactory = matchFactory;
		this.playerRepository = players;
		this.matchs = new ArrayList<MatchImpl>();
		this.matchs.add(createMatch());
	}

	private MatchImpl createMatch() {
		PlayerToken startingPlayer = playerRepository.getAll().get(matchs.size() % 4);
		return matchFactory.createMatch(startingPlayer);
	}

	@Override
	public PlayerTokenRepository getPlayerRepository() {
		return playerRepository;
	}

	@Override
	public Match getCurrentMatch() {
		MatchImpl match = matchs.get(matchs.size() - 1);
		if (match.isComplete()) {
			match = createMatch();
			matchs.add(match);
		}
		return match;
	}

	@Override
	public Score getTotalScore() {
		Score totalScore = new Score(playerRepository);
		for (Match match : matchs) {
			totalScore.add(match.getScore());
		}
		return totalScore;
	}

}
