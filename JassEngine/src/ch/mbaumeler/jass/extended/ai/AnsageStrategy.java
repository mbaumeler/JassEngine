package ch.mbaumeler.jass.extended.ai;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.Ansage;

public interface AnsageStrategy {

	Ansage getAnsage(Match match);

}