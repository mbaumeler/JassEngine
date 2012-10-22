package ch.mbaumeler.jass.extended.ai.simple;

import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;

public interface SimpleSelectCardStrategy {

	Card getPlayableCard(List<Card> cardsInHand, Match match);

	boolean isResponsible(Match match);
}
