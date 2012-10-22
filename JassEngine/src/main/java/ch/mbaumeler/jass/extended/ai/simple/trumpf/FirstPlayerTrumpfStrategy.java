package ch.mbaumeler.jass.extended.ai.simple.trumpf;

import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.extended.ai.simple.SimpleSelectCardStrategy;
import ch.mbaumeler.jass.extended.ai.simple.StrategyUtil;

/* REVIEW NEEDED */public class FirstPlayerTrumpfStrategy implements SimpleSelectCardStrategy {

	private StrategyUtil strategyUtil = new StrategyUtil();

	@Override
	public Card getPlayableCard(List<Card> cardsInHand, Match match) {
		return strategyUtil.firstPlayableWinnercardOfAnySuit(cardsInHand, match);
	}

	@Override
	public boolean isResponsible(Match match) {
		return match.getCardsOnTable().isEmpty() && match.getAnsage().isTrumpf();
	}
}
