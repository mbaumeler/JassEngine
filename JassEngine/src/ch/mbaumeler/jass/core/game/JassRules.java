package ch.mbaumeler.jass.core.game;

import static ch.mbaumeler.jass.core.card.CardValue.JACK;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ch.mbaumeler.jass.core.card.CardSuit;

public class JassRules {

	@Inject
	ScoreUtil scoreUtil;

	public boolean isCardPlayable(PlayedCard card, List<PlayedCard> cardsInHand, List<PlayedCard> cardsOnTable,
			Ansage trumpf, boolean isNewRoundStarted) {

		return ansageSet(trumpf) && hasCardInHand(card, cardsInHand)
				&& isAllowedToPlay(card, cardsInHand, cardsOnTable, trumpf, isNewRoundStarted);
	}

	private CardSuit getCurrentSuit(List<PlayedCard> cardsOnTable) {
		return cardsOnTable.isEmpty() ? null : cardsOnTable.get(0).getSuit();
	}

	private boolean isAllowedToPlay(PlayedCard cardToPlay, List<PlayedCard> cardsInHand, List<PlayedCard> cardsOnTable,
			Ansage ansage, boolean isNewRoundStarted) {

		CardSuit currentSuit = getCurrentSuit(cardsOnTable);

		return isTrumpfAndNotUnderTrumpf(cardToPlay, cardsInHand, cardsOnTable, ansage) || isNewRoundStarted
				|| isSameSuit(cardToPlay, currentSuit) || hasNotSameSuit(currentSuit, cardsInHand, ansage);
	}

	private boolean ansageSet(Ansage ansage) {
		return ansage != null;
	}

	private boolean hasCardInHand(PlayedCard card, List<PlayedCard> cardsInHand) {
		return cardsInHand.contains(card);
	}

	private boolean hasNotSameSuit(CardSuit suit, List<PlayedCard> cardsInHand, Ansage trumpf) {
		for (PlayedCard card : cardsInHand) {
			if (card.getSuit().equals(suit) && isNotTrumpfJack(card, trumpf)) {
				return false;
			}
		}
		return true;
	}

	private boolean isNotTrumpfJack(PlayedCard cardToPlay, Ansage trumpf) {
		return !(isTrumpf(cardToPlay, trumpf) && cardToPlay.getValue().equals(JACK));
	}

	private boolean isTrumpf(PlayedCard cardToPlay, Ansage ansage) {
		return ansage.is(cardToPlay.getSuit());
	}

	private boolean isSameSuit(PlayedCard cardToPlay, CardSuit currentSuit) {
		return cardToPlay.getSuit().equals(currentSuit);
	}

	private boolean isTrumpfAndNotUnderTrumpf(PlayedCard cardToPlay, List<PlayedCard> cardsInHand,
			List<PlayedCard> cardsOnTable, Ansage ansage) {

		CardSuit currentSuit = getCurrentSuit(cardsOnTable);
		if (isTrumpf(cardToPlay, ansage)) {
			if (currentSuit == null || ansage.is(currentSuit)) {
				return true;
			} else {
				return hasNotSameSuit(currentSuit, cardsInHand, ansage)
						|| isNotUnterTrumpf(cardToPlay, ansage, cardsOnTable);
			}

		}
		return false;
	}

	private boolean isNotUnterTrumpf(PlayedCard cardToPlay, Ansage ansage, List<PlayedCard> cardsOnTable) {

		PlayedCard currentWinner = scoreUtil.getWinnerCard(cardsOnTable, ansage);

		if (!ansage.is(currentWinner.getSuit())) {
			return true;
		}

		List<PlayedCard> list = new ArrayList<PlayedCard>();
		list.add(currentWinner);
		list.add(cardToPlay);
		return currentWinner != scoreUtil.getWinnerCard(list, ansage);
	}

}
