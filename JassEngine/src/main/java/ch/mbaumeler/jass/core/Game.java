package ch.mbaumeler.jass.core;

import ch.mbaumeler.jass.core.game.Score;

public interface Game {

	/**
	 * Returns the current played match.
	 * 
	 * @return current played match.
	 */
	Match getCurrentMatch();

	/**
	 * Creates a new Match.
	 * 
	 */
	void createMatch();

	/**
	 * Calculates the score for all matches.
	 * 
	 * @return score of all matches.
	 */
	Score getTotalScore();

	/**
	 * Creates a state of the current game.
	 * 
	 * @return created Gamestate.
	 */
	GameState createGameState();
}