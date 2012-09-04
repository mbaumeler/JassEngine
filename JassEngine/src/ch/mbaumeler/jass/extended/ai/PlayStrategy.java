package ch.mbaumeler.jass.extended.ai;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;

public interface PlayStrategy {

	Card getCardToPlay(Match match);
}