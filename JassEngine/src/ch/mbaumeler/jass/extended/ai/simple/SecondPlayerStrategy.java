package ch.mbaumeler.jass.extended.ai.simple;

import java.util.ArrayList;
import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayedCard;

public class SecondPlayerStrategy implements SimpleCardStrategy {

	private StrategyUtil strategyUtil = new StrategyUtil();

	@Override
	public PlayedCard getPlayableCard(List<PlayedCard> cardsInHand, Match match) {

		List<PlayedCard> cardsOnTable = new ArrayList<PlayedCard>(match.getCardsOnTable());
		Ansage trumpf = match.getAnsage();
		PlayedCard firstPlayedCard = cardsOnTable.isEmpty() ? null : cardsOnTable.get(0);

		if (strategyUtil.getHighestCardOfSameColor(firstPlayedCard, trumpf, cardsInHand) != null) {
			return strategyUtil.getHighestCardOfSameColor(firstPlayedCard, trumpf, cardsInHand);
		} else {
			return strategyUtil.getLeastPlayableCard(cardsInHand, match);
		}
	}

	@Override
	public boolean isResponsible(List<PlayedCard> cardsOnTable) {
		return cardsOnTable.size() == 1;
	}

}
