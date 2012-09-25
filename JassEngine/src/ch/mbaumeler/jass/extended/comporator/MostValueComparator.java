package ch.mbaumeler.jass.extended.comporator;

import java.util.Comparator;

import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayedCard;
import ch.mbaumeler.jass.core.game.ScoreRules;

public class MostValueComparator implements Comparator<PlayedCard> {

	private final Ansage ansage;

	private ScoreRules scoreUtil = new ScoreRules();

	public MostValueComparator(Ansage ansage) {
		this.ansage = ansage;
	}

	@Override
	public int compare(PlayedCard card, PlayedCard card2) {
		return scoreUtil.getScore(card2, ansage) - scoreUtil.getScore(card, ansage);
	}
}
