package ch.mbaumeler.jass.extended.comporator;

import java.util.Comparator;

import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.Card;
import ch.mbaumeler.jass.core.game.ScoreRules;

public class MostValueComparator implements Comparator<Card> {

	private final Ansage ansage;

	private ScoreRules scoreUtil = new ScoreRules();

	public MostValueComparator(Ansage ansage) {
		this.ansage = ansage;
	}

	@Override
	public int compare(Card card, Card card2) {
		return scoreUtil.getScore(card2, ansage) - scoreUtil.getScore(card, ansage);
	}
}
