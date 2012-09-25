package ch.mbaumeler.jass.extended.ai;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.Card;

public interface JassStrategy {

	Card getCardToPlay(Match match);

	Ansage getAnsage(Match match);

}
