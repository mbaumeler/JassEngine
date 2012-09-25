package ch.mbaumeler.jass.extended.ai.simple;

import static ch.mbaumeler.jass.core.card.CardValue.ACE;
import static ch.mbaumeler.jass.core.card.CardValue.TEN;

import java.util.Collections;
import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayedCard;
import ch.mbaumeler.jass.core.game.ScoreUtil;
import ch.mbaumeler.jass.extended.comporator.BestCardComparator;
import ch.mbaumeler.jass.extended.comporator.MostValueComparator;

public class StrategyUtil {

	public PlayedCard getHighestCardOfSameColor(PlayedCard firstPlayedCard, Ansage trumpf, List<PlayedCard> cardsInHand) {
		CardSuit suit = firstPlayedCard.getSuit();
		List<PlayedCard> sameColor = new CardUtil().createCardMap(cardsInHand).get(suit);
		if (sameColor.isEmpty()) {
			return null;
		}
		BestCardComparator bestCardComparator = new BestCardComparator(trumpf, suit);
		Collections.sort(sameColor, bestCardComparator);
		PlayedCard bestPlayerCard = sameColor.get(0);

		return bestCardComparator.compare(bestPlayerCard, firstPlayedCard) < 0 ? bestPlayerCard : null;
	}

	public boolean alreadyWinning(List<PlayedCard> cardsOnTable, Ansage trumpf) {
		ScoreUtil scoreUtil = new ScoreUtil();
		PlayedCard winnerCard = scoreUtil.getWinnerCard(cardsOnTable, trumpf);
		return cardsOnTable.indexOf(winnerCard) == 1;
	}

	public PlayedCard leastPlayableWinnerCard(List<PlayedCard> cardsInHand, Match match) {
		BestCardComparator bestCardComporator = new BestCardComparator(match.getAnsage(), getCurrentSuit(match));
		Collections.sort(cardsInHand, bestCardComporator);

		PlayedCard currentWinnerCard = new ScoreUtil().getWinnerCard(match.getCardsOnTable(), match.getAnsage());

		return getLastPlayableWinnerCard(cardsInHand, match, currentWinnerCard, bestCardComporator);
	}

	public CardSuit getCurrentSuit(Match match) {
		List<PlayedCard> cardsOnTable = match.getCardsOnTable();
		return cardsOnTable.isEmpty() ? null : cardsOnTable.get(0).getSuit();
	}

	public PlayedCard firstPlayableWinnercardOfAnySuit(List<PlayedCard> cardsInHand, Match match) {
		Collections.sort(cardsInHand, new BestCardComparator(match.getAnsage(), null));
		return getFirstPlayableCard(cardsInHand, match);
	}

	public PlayedCard getCardWithMostScoreWhichIsPlayable(List<PlayedCard> cardsInHand, Ansage trumpf, Match match) {
		Collections.sort(cardsInHand, new MostValueComparator(trumpf));

		PlayedCard cardToReturn = null;
		for (PlayedCard card : cardsInHand) {
			if (match.isCardPlayable(card)
					&& (cardToReturn == null || (cardToReturn.getValue() == ACE && card.getValue() == TEN))) {
				cardToReturn = card;
			}
		}

		return cardToReturn;
	}

	private PlayedCard getLastPlayableWinnerCard(List<PlayedCard> cards, Match match, PlayedCard currentWinnerCard,
			BestCardComparator bestCardComporator) {

		PlayedCard cardToPlay = null;
		for (PlayedCard card : cards) {
			if (match.isCardPlayable(card) && bestCardComporator.compare(currentWinnerCard, card) > 0) {
				cardToPlay = card;
			}
		}
		return cardToPlay;
	}

	public PlayedCard getFirstPlayableCard(List<PlayedCard> cards, Match match) {
		for (PlayedCard card : cards) {
			if (match.isCardPlayable(card)) {
				return card;
			}
		}
		throw new IllegalStateException("Player does not have a playable card: " + cards);
	}

	public PlayedCard getLeastPlayableCard(List<PlayedCard> cardsInHand, Match match) {
		BestCardComparator bestCardComporator = new BestCardComparator(match.getAnsage(), null);
		Collections.sort(cardsInHand, bestCardComporator);
		Collections.reverse(cardsInHand);
		return getFirstPlayableCard(cardsInHand, match);
	}

}
