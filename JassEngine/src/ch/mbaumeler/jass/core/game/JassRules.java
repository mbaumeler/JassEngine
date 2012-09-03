package ch.mbaumeler.jass.core.game;

import static ch.mbaumeler.jass.core.card.CardValue.JACK;

import java.util.List;

import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.card.CardSuit;

public class JassRules {

	public boolean isCardPlayable(Card card, List<Card> cardsInHand, CardSuit currentSuite, Ansage trumpf,
			boolean isNewRoundStarted) {

		return isTrumpfChoosen(trumpf) && hasCardInHand(card, cardsInHand)
				&& isAllowedToPlay(card, cardsInHand, currentSuite, trumpf, isNewRoundStarted);
	}

	private boolean isAllowedToPlay(Card card, List<Card> cardsInHand, CardSuit currentSuite, Ansage trumpf,
			boolean isNewRoundStarted) {

		// TODO untertrumpfen
		return isTrumpf(card, trumpf) || isNewRoundStarted || isSameSuit(card, currentSuite)
				|| hasNotSameSuit(currentSuite, cardsInHand, trumpf);
	}

	private boolean isTrumpfChoosen(Ansage trumpf) {
		return trumpf != null;
	}

	private boolean hasCardInHand(Card card, List<Card> cardsInHand) {
		return cardsInHand.contains(card);
	}

	private boolean hasNotSameSuit(CardSuit suit, List<Card> cardsInHand, Ansage trumpf) {
		for (Card card : cardsInHand) {
			if (card.getSuit().equals(suit) && isNotTrumpfJack(card, trumpf)) {
				return false;
			}
		}
		return true;
	}

	private boolean isNotTrumpfJack(Card cardToPlay, Ansage trumpf) {
		return !(isTrumpf(cardToPlay, trumpf) && cardToPlay.getValue().equals(JACK));
	}

	private boolean isSameSuit(Card cardToPlay, CardSuit currentSuit) {
		return cardToPlay.getSuit().equals(currentSuit);
	}

	private boolean isTrumpf(Card cardToPlay, Ansage trumpf) {
		return trumpf != null && trumpf.isTrumpf(cardToPlay.getSuit());
	}

}
