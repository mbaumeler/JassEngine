package ch.mbaumeler.jass.extended.ai;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayedCard;

public interface JassStrategy {

	PlayedCard getCardToPlay(Match match);

	Ansage getAnsage(Match match);

}
