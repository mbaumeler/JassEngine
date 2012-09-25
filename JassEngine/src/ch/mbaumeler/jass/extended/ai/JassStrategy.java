package ch.mbaumeler.jass.extended.ai;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.Ansage;

public interface JassStrategy {

	Card getCardToPlay(Match match);

	Ansage getAnsage(Match match);

}
