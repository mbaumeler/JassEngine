package ch.mbaumeler.jass.extended.ai.simple;

import java.util.ArrayList;
import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.Ansage;

/* REVIEW NEEDED */ public class FourthPlayerStrategy implements SimpleCardStrategy {

	private StrategyUtil util = new StrategyUtil();

	@Override
	public Card getPlayableCard(List<Card> cardsInHand, Match match) {

		List<Card> cardsOnTable = new ArrayList<Card>(match.getCardsOnTable());
		Ansage trumpf = match.getAnsage();

		if (util.alreadyWinning(cardsOnTable, trumpf)) {
			return util.getCardWithMostScoreWhichIsPlayable(cardsInHand, trumpf, match);
		} else if (util.leastPlayableWinnerCard(cardsInHand, match) != null) {
			// Try to win
			return util.leastPlayableWinnerCard(cardsInHand, match);
		} else {
			return util.getLeastPlayableCard(cardsInHand, match);
		}
	}

	@Override
	public boolean isResponsible(List<Card> cardsOnTable) {
		return cardsOnTable.size() == 3;
	}

}
