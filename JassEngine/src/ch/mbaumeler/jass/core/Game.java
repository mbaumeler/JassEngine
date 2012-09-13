package ch.mbaumeler.jass.core;

import ch.mbaumeler.jass.core.game.PlayerTokenRepository;
import ch.mbaumeler.jass.core.game.Score;

public interface Game {

	/**
	 * Returns the playerrepository.
	 * 
	 * @return the playerrepository.
	 */
	PlayerTokenRepository getPlayerRepository();

	/**
	 * Returns the current played match.
	 * 
	 * @return current played match.
	 */
	Match getCurrentMatch();

	/**
	 * Calculates the score for all matches.
	 * 
	 * @return score of all matches.
	 */
	Score getTotalScore();
}