package ch.mbaumeler.jass.core.game.wys;

import static ch.mbaumeler.jass.core.card.CardValue.ACE;
import static ch.mbaumeler.jass.core.card.CardValue.EIGHT;
import static ch.mbaumeler.jass.core.card.CardValue.JACK;
import static ch.mbaumeler.jass.core.card.CardValue.KING;
import static ch.mbaumeler.jass.core.card.CardValue.NINE;
import static ch.mbaumeler.jass.core.card.CardValue.QUEEN;
import static ch.mbaumeler.jass.core.card.CardValue.SEVEN;
import static ch.mbaumeler.jass.core.card.CardValue.SIX;
import static ch.mbaumeler.jass.core.card.CardValue.TEN;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.card.CardValue;

public class WysScoreRule {

	public int getScoreFore(Wys wys) {

		switch (wys.getTyp()) {
		case STOECK:
			return getScoreForStoeck();
		case VIER_GLEICHE:
			return getScoreForVierGleiche(wys);
		case BLATT:
			return getScoreForBlatt(wys);
		default:
			throw new IllegalArgumentException("Unknow wys typ: " + wys.getTyp());
		}
	}

	private int getScoreForBlatt(Wys wys) {
		int numberOfCards = wys.getCards().size();
		switch (numberOfCards) {
		case 3:
			return 20;
		case 4:
			return 50;
		case 5:
			return 100;
		case 6:
			return 150;
		case 7:
			return 200;
		case 8:
			return 250;
		case 9:
			return 300;
		default:
			throw new IllegalArgumentException("Wys has unexpected count of cards: " + numberOfCards);
		}
	}

	public int getScoreForStoeck() {
		return 20;
	}

	private int getScoreForVierGleiche(Wys wys) {
		Card firstCard = wys.getCards().iterator().next();
		if (isValue(firstCard, ACE, KING, QUEEN, TEN, EIGHT, SEVEN, SIX)) {
			return 100;
		} else if (isValue(firstCard, JACK)) {
			return 200;
		} else if (isValue(firstCard, NINE)) {
			return 150;
		} else
			throw new IllegalArgumentException("Could not find score for: " + wys.getCards());
	}

	private boolean isValue(Card card, CardValue... value) {
		for (CardValue cardValue : value) {
			if (card.getValue() == cardValue) {
				return true;
			}
		}
		return false;
	}
}
