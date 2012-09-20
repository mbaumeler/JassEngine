package ch.mbaumeler.jass.core.game;

import static ch.mbaumeler.jass.core.game.Ansage.SpielModi.OBENABE;
import static ch.mbaumeler.jass.core.game.Ansage.SpielModi.UNDEUFE;
import ch.mbaumeler.jass.core.card.Card;

public class ScoreRules {

	public int getScore(Card card, Ansage ansage) {

		boolean isTrumpf = isTrumpf(card, ansage);
		boolean isUndeUfe = ansage.getSpielModi() == UNDEUFE;
		boolean isObenAbe = ansage.getSpielModi() == OBENABE;

		switch (card.getValue()) {
		case JACK:
			return isTrumpf ? 20 : 2;
		case NINE:
			return isTrumpf ? 14 : 0;
		case QUEEN:
			return 3;
		case KING:
			return 4;
		case TEN:
			return 10;
		case ACE:
			return isUndeUfe ? 0 : 11;
		case EIGHT:
			return (isObenAbe || isUndeUfe) ? 8 : 0;
		case SIX:
			return isUndeUfe ? 11 : 0;
		default:
			return 0;
		}
	}

	private boolean isTrumpf(Card card, Ansage ansage) {
		return ansage.is(card.getSuit());
	}

}
