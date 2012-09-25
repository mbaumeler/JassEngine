package ch.mbaumeler.jass.extended.ai.simple;

import java.util.ArrayList;
import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.Card;

public class ThirdPlayerStrategy implements SimpleCardStrategy {

	private StrategyUtil util = new StrategyUtil();

	@Override
	public Card getPlayableCard(List<Card> cardsInHand, Match match) {

		List<Card> cardsOnTable = new ArrayList<Card>(match.getCardsOnTable());
		Ansage trumpf = match.getAnsage();
		Card firstPlayedCard = cardsOnTable.isEmpty() ? null : cardsOnTable.get(0);

		boolean alreadyWinning = util.alreadyWinning(cardsOnTable, trumpf);

		if (alreadyWinning) {
			return util.getCardWithMostScoreWhichIsPlayable(cardsInHand, trumpf, match);
		} else if (util.getHighestCardOfSameColor(firstPlayedCard, trumpf, cardsInHand) != null) {
			return util.getHighestCardOfSameColor(firstPlayedCard, trumpf, cardsInHand);
		} else {
			return util.getLeastPlayableCard(cardsInHand, match);
		}
	}

	@Override
	public boolean isResponsible(List<Card> cardsOnTable) {
		return cardsOnTable.size() == 2;
	}

}
