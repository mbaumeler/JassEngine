package ch.mbaumeler.jass.core.game;

import static ch.mbaumeler.jass.core.card.CardValue.JACK;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.card.CardSuit;

public class JassRules {

	@Inject
	ScoreUtil scoreUtil;

	public boolean isCardPlayable(Card card, List<Card> cardsInHand,
			List<PlayedCard> cardsOnTable, Ansage trumpf,
			boolean isNewRoundStarted) {

		return ansageSet(trumpf)
				&& hasCardInHand(card, cardsInHand)
				&& isAllowedToPlay(card, cardsInHand, cardsOnTable, trumpf,
						isNewRoundStarted);
	}

	private CardSuit getCurrentSuit(List<PlayedCard> cardsOnTable) {
		return cardsOnTable.isEmpty() ? null : cardsOnTable.get(0).getCard()
				.getSuit();
	}

	private boolean isAllowedToPlay(Card cardToPlay, List<Card> cardsInHand,
			List<PlayedCard> cardsOnTable, Ansage ansage,
			boolean isNewRoundStarted) {

		CardSuit currentSuit = getCurrentSuit(cardsOnTable);

		return isTrumpfAndNotUnderTrumpf(cardToPlay, cardsInHand, cardsOnTable,
				ansage)
				|| isNewRoundStarted
				|| isSameSuit(cardToPlay, currentSuit)
				|| hasNotSameSuit(currentSuit, cardsInHand, ansage);
	}

	private boolean ansageSet(Ansage ansage) {
		return ansage != null;
	}

	private boolean hasCardInHand(Card card, List<Card> cardsInHand) {
		return cardsInHand.contains(card);
	}

	private boolean hasNotSameSuit(CardSuit suit, List<Card> cardsInHand,
			Ansage trumpf) {
		for (Card card : cardsInHand) {
			if (card.getSuit().equals(suit) && isNotTrumpfJack(card, trumpf)) {
				return false;
			}
		}
		return true;
	}

	private boolean isNotTrumpfJack(Card cardToPlay, Ansage trumpf) {
		return !(isTrumpf(cardToPlay, trumpf) && cardToPlay.getValue().equals(
				JACK));
	}

	private boolean isTrumpf(Card cardToPlay, Ansage ansage) {
		return ansage.isTrumpf(cardToPlay.getSuit());
	}

	private boolean isSameSuit(Card cardToPlay, CardSuit currentSuit) {
		return cardToPlay.getSuit().equals(currentSuit);
	}

	private boolean isTrumpfAndNotUnderTrumpf(Card cardToPlay,
			List<Card> cardsInHand, List<PlayedCard> cardsOnTable, Ansage ansage) {

		CardSuit currentSuit = getCurrentSuit(cardsOnTable);
		if (isTrumpf(cardToPlay, ansage)) {
			if (currentSuit == null || ansage.isTrumpf(currentSuit)) {
				return true;
			} else {
				return hasNotSameSuit(currentSuit, cardsInHand, ansage)
						|| isNotUnterTrumpf(cardToPlay, ansage, cardsOnTable);
			}

		}
		return false;
	}

	private boolean isNotUnterTrumpf(Card cardToPlay, Ansage ansage,
			List<PlayedCard> cardsOnTable) {

		PlayedCard currentWinner = scoreUtil
				.getWinnerCard(cardsOnTable, ansage);

		if (!ansage.isTrumpf(currentWinner.getCard().getSuit())) {
			return true;
		}

		List<PlayedCard> list = new ArrayList<PlayedCard>();
		list.add(currentWinner);
		list.add(new PlayedCard(cardToPlay, null));
		return currentWinner != scoreUtil.getWinnerCard(list, ansage);
	}

}
