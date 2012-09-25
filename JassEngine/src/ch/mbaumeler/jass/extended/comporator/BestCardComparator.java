package ch.mbaumeler.jass.extended.comporator;

import java.util.Comparator;

import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayedCard;

public class BestCardComparator implements Comparator<PlayedCard> {

	private final Ansage ansage;
	private final CardSuit suit;

	public BestCardComparator(Ansage ansage, CardSuit suit) {
		this.ansage = ansage;
		this.suit = suit;
	}

	@Override
	public int compare(PlayedCard card1, PlayedCard card2) {
		return suit != null ? compareSuitSensitiv(card1, card2) : compareSuitUnsensitiv(card1, card2);
	}

	private int compareSuitSensitiv(PlayedCard card1, PlayedCard card2) {
		if (isTrumpf(card1) && !isTrumpf(card2)) {
			return -1;
		} else if (!isTrumpf(card1) && isTrumpf(card2)) {
			return 1;
		} else if (isTrumpf(card1) && isTrumpf(card2)) {
			return getTrumpfValue(card2) - getTrumpfValue(card1);
		} else if (isSuit(card1) && isSuit(card2)) {
			return card2.getValue().ordinal() - card1.getValue().ordinal();
		} else if (!isSuit(card1) && isSuit(card2)) {
			return 1;
		} else {
			return -1;
		}
	}

	private int compareSuitUnsensitiv(PlayedCard card1, PlayedCard card2) {
		if (isTrumpf(card1) && !isTrumpf(card2)) {
			return -1;
		} else if (!isTrumpf(card1) && isTrumpf(card2)) {
			return 1;
		} else if (isTrumpf(card1) && isTrumpf(card2)) {
			return getTrumpfValue(card2) - getTrumpfValue(card1);
		} else {
			return card2.getValue().ordinal() - card1.getValue().ordinal();
		}
	}

	private boolean isTrumpf(PlayedCard card) {
		return ansage.is(card.getSuit());
	}

	private boolean isSuit(PlayedCard card) {
		return card.getSuit().equals(suit);
	}

	private int getTrumpfValue(PlayedCard card) {

		switch (card.getValue()) {
		case JACK:
			return 20;
		case NINE:
			return 14;
		default:
			return card.getValue().ordinal();
		}
	}

}