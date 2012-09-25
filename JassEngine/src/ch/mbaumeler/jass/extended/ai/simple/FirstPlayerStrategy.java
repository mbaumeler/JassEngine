package ch.mbaumeler.jass.extended.ai.simple;

import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.Card;

public class FirstPlayerStrategy implements SimpleCardStrategy {

	private StrategyUtil strategyUtil = new StrategyUtil();

	@Override
	public Card getPlayableCard(List<Card> cardsInHand, Match match) {
		return strategyUtil.firstPlayableWinnercardOfAnySuit(cardsInHand, match);
	}

	@Override
	public boolean isResponsible(List<Card> cardsOnTable) {
		int size = cardsOnTable.size();
		return size == 0 || size == 4;
	}
}
