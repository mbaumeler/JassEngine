package ch.mbaumeler.jass.core;

import java.util.List;
import java.util.Set;

import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.Score;
import ch.mbaumeler.jass.core.game.wys.Wys;

public interface Match {

	/**
	 * Returns the cards in hand of the given player.
	 * 
	 * @param player
	 *            The player.
	 * @return The cards in hand of the given player.
	 */
	List<Card> getCards(PlayerToken player);

	/**
	 * Returns a list of cards on the table. A list of cards between 0 and 4
	 * cards.
	 * 
	 * 
	 * @return cards on the table.
	 */
	List<Card> getCardsOnTable();

	/**
	 * Returns the ansage of the match. Might by null if no ansage is set.
	 * 
	 * @return the ansage or null
	 */
	Ansage getAnsage();

	/**
	 * Sets the ansage. Throws a IllegalArgument-Exception if the ansage is
	 * already set.
	 * 
	 * @param ansage
	 *            the ansage to set.
	 */
	void setAnsage(Ansage ansage);

	/**
	 * Returns the player who can play the next card.
	 * 
	 * @return player which can do the next action.
	 */
	PlayerToken getActivePlayer();

	/**
	 * Returns true if the given cards is playable by the active player or not.
	 * 
	 * @param card
	 *            Card to check.
	 * @return true, if the card is playable by the active player.
	 */
	boolean isCardPlayable(Card card);

	/**
	 * Returns the card from round i.
	 * 
	 * @param i
	 *            0... completed rounds -1
	 * @return the played cards for the search round.
	 */
	List<Card> getCardsFromRound(int i);

	/**
	 * Plays the given card.
	 * 
	 * @param card
	 *            Card to play.
	 */
	void playCard(Card card);

	/**
	 * Schiebt die Ansage.
	 */
	void schiebe();

	/**
	 * True, if the ansage was geschoben.
	 */
	boolean isGeschoben();

	/**
	 * Wys.
	 * 
	 * @param wysSet
	 *            set to wys.
	 */
	void wys(Set<Wys> wysSet);

	/**
	 * Checks if all cards in the match a played.
	 * 
	 * @return true, if all cards are played.
	 */
	boolean isComplete();

	/**
	 * Returns the current score of the match.
	 * 
	 * @return current score.
	 */
	Score getScore();

	/**
	 * Returns the number of rounds which are completed. A completed round is
	 * where four cards are played.
	 * 
	 * @return number of completed rounds.
	 */
	int getRoundsCompleted();

	/**
	 * Creates a match state to store the current state of the match in an
	 * object.
	 * 
	 * @return current state of the match.
	 */
	MatchState createMatchState();

}