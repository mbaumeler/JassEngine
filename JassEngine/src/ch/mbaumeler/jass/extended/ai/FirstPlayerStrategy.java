package ch.mbaumeler.jass.extended.ai;

import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.PlayedCard;

public class FirstPlayerStrategy implements CardStrategy {

	private StrategyUtil strategyUtil = new StrategyUtil();

	@Override
	public Card getPlayableCard(List<Card> cardsInHand, Match match) {
		return strategyUtil.firstPlayableWinnercardOfAnySuit(cardsInHand, match);
	}

	@Override
	public boolean isResponsible(List<PlayedCard> cardsOnTable) {
		int size = cardsOnTable.size();
		return size == 0 || size == 4;
	}
}
