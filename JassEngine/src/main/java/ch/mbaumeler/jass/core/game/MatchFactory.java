package ch.mbaumeler.jass.core.game;

import javax.inject.Inject;

import ch.mbaumeler.jass.core.CardFactory;
import ch.mbaumeler.jass.core.MatchState;
import ch.mbaumeler.jass.core.game.impl.MatchImpl;
import ch.mbaumeler.jass.core.game.wys.WysRules;
import ch.mbaumeler.jass.core.game.wys.WysScoreRule;

/* REVIEW NEEDED */public class MatchFactory {

	@Inject
	JassRules jassRules;

	@Inject
	WysRules wysRules;

	@Inject
	ScoreRules scoreRules;

	@Inject
	ScoreUtil scoreUtil;

	@Inject
	WysScoreRule wysScoreRule;

	@Inject
	CardFactory cardFactory;

	public MatchImpl createMatch(PlayerToken startingPlayer) {
		return new MatchImpl(startingPlayer, scoreUtil, jassRules, cardFactory.createShuffledCards(), scoreRules,
				wysRules, wysScoreRule);
	}

	public MatchImpl createMatch(MatchState matchState) {
		return new MatchImpl(matchState, scoreUtil, jassRules, scoreRules, wysRules, wysScoreRule);
	}

}
