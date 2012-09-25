package ch.mbaumeler.jass.extended.ai.simple;

import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.PlayedCard;

public class FirstPlayerStrategy implements SimpleCardStrategy {

	private StrategyUtil strategyUtil = new StrategyUtil();

	@Override
	public PlayedCard getPlayableCard(List<PlayedCard> cardsInHand, Match match) {
		return strategyUtil.firstPlayableWinnercardOfAnySuit(cardsInHand, match);
	}

	@Override
	public boolean isResponsible(List<PlayedCard> cardsOnTable) {
		int size = cardsOnTable.size();
		return size == 0 || size == 4;
	}
}
