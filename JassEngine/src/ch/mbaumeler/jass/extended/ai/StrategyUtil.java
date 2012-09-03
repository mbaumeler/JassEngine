package ch.mbaumeler.jass.extended.ai;

import static ch.mbaumeler.jass.core.card.CardValue.ACE;
import static ch.mbaumeler.jass.core.card.CardValue.TEN;

import java.util.Collections;
import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayedCard;
import ch.mbaumeler.jass.core.game.ScoreUtil;
import ch.mbaumeler.jass.extended.comporator.BestCardComparator;
import ch.mbaumeler.jass.extended.comporator.MostValueComparator;

/** TO REFACTOR */
public class StrategyUtil {

	public Card getHighestCardOfSameColor(Card firstPlayedCard, Ansage trumpf, List<Card> cardsInHand) {
		CardSuit suit = firstPlayedCard.getSuit();
		List<Card> sameColor = new CardUtil().createCardMap(cardsInHand).get(suit);
		if (sameColor.isEmpty()) {
			return null;
		}
		BestCardComparator bestCardComparator = new BestCardComparator(trumpf, suit);
		Collections.sort(sameColor, bestCardComparator);
		Card bestPlayerCard = sameColor.get(0);

		return bestCardComparator.compare(bestPlayerCard, firstPlayedCard) < 0 ? bestPlayerCard : null;
	}

	public boolean alreadyWinning(List<PlayedCard> cardsOnTable, Ansage trumpf) {
		ScoreUtil scoreUtil = new ScoreUtil();
		PlayedCard winnerCard = scoreUtil.getWinnerCard(cardsOnTable, trumpf);
		return cardsOnTable.indexOf(winnerCard) == 1;
	}

	public Card leastPlayableWinnerCard(List<Card> cardsInHand, Match match) {
		BestCardComparator bestCardComporator = new BestCardComparator(match.getAnsage(), getCurrentSuit(match));
		Collections.sort(cardsInHand, bestCardComporator);

		PlayedCard currentWinnerCard = new ScoreUtil().getWinnerCard(match.getCardsOnTable(), match.getAnsage());

		return getLastPlayableWinnerCard(cardsInHand, match, currentWinnerCard, bestCardComporator);
	}

	public CardSuit getCurrentSuit(Match match) {
		List<PlayedCard> cardsOnTable = match.getCardsOnTable();
		return cardsOnTable.isEmpty() ? null : cardsOnTable.get(0).getCard().getSuit();
	}

	public Card firstPlayableWinnercardOfAnySuit(List<Card> cardsInHand, Match match) {
		Collections.sort(cardsInHand, new BestCardComparator(match.getAnsage(), null));
		return getFirstPlayableCard(cardsInHand, match);
	}

	public Card getCardWithMostScoreWhichIsPlayable(List<Card> cardsInHand, Ansage trumpf, Match match) {
		Collections.sort(cardsInHand, new MostValueComparator(trumpf));

		Card cardToReturn = null;
		for (Card card : cardsInHand) {
			if (match.isCardPlayable(card)
					&& (cardToReturn == null || (cardToReturn.getValue() == ACE && card.getValue() == TEN))) {
				cardToReturn = card;
			}
		}

		return cardToReturn;
	}

	private Card getLastPlayableWinnerCard(List<Card> cards, Match match, PlayedCard currentWinnerCard,
			BestCardComparator bestCardComporator) {

		Card cardToPlay = null;
		for (Card card : cards) {
			if (match.isCardPlayable(card) && bestCardComporator.compare(currentWinnerCard.getCard(), card) > 0) {
				cardToPlay = card;
			}
		}
		return cardToPlay;
	}

	public Card getFirstPlayableCard(List<Card> cards, Match match) {
		for (Card card : cards) {
			if (match.isCardPlayable(card)) {
				return card;
			}
		}
		throw new IllegalStateException("Player does not have a playable card: " + cards);
	}

	public Card getLeastPlayableCard(List<Card> cardsInHand, Match match) {
		BestCardComparator bestCardComporator = new BestCardComparator(match.getAnsage(), null);
		Collections.sort(cardsInHand, bestCardComporator);
		Collections.reverse(cardsInHand);
		return getFirstPlayableCard(cardsInHand, match);
	}

}
