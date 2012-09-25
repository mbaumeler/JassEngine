package ch.mbaumeler.jass.core;

import java.util.List;

import ch.mbaumeler.jass.core.game.Card;

/**
 * Factory to create a list of cards.
 * 
 * @author mab
 */
public interface CardFactory {

	/**
	 * Creates a new list of 36 shuffled cards.
	 * 
	 * @return new list of shuffled cards.
	 */
	List<Card> createShuffledCards();
}