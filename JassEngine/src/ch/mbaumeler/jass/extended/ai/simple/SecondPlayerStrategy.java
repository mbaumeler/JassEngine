package ch.mbaumeler.jass.extended.ai.simple;

import java.util.ArrayList;
import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.Ansage;

public class SecondPlayerStrategy implements SimpleCardStrategy {

	private StrategyUtil strategyUtil = new StrategyUtil();

	@Override
	public Card getPlayableCard(List<Card> cardsInHand, Match match) {

		List<Card> cardsOnTable = new ArrayList<Card>(match.getCardsOnTable());
		Ansage trumpf = match.getAnsage();
		Card firstPlayedCard = cardsOnTable.isEmpty() ? null : cardsOnTable.get(0);

		if (strategyUtil.getHighestCardOfSameColor(firstPlayedCard, trumpf, cardsInHand) != null) {
			return strategyUtil.getHighestCardOfSameColor(firstPlayedCard, trumpf, cardsInHand);
		} else {
			return strategyUtil.getLeastPlayableCard(cardsInHand, match);
		}
	}

	@Override
	public boolean isResponsible(List<Card> cardsOnTable) {
		return cardsOnTable.size() == 1;
	}

}
