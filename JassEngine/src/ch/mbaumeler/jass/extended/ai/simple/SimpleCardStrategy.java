package ch.mbaumeler.jass.extended.ai.simple;

import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.PlayedCard;

public interface SimpleCardStrategy {

	PlayedCard getPlayableCard(List<PlayedCard> cardsInHand, Match match);

	boolean isResponsible(List<PlayedCard> cardsOnTable);
}
