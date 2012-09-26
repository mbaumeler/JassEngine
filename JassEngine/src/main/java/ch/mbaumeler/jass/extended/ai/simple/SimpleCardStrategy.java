package ch.mbaumeler.jass.extended.ai.simple;

import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;

public interface SimpleCardStrategy {

	Card getPlayableCard(List<Card> cardsInHand, Match match);

	boolean isResponsible(List<Card> cardsOnTable);
}
