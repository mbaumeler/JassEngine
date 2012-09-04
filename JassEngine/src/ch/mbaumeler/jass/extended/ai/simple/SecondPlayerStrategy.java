package ch.mbaumeler.jass.extended.ai.simple;

import java.util.ArrayList;
import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayedCard;

public class SecondPlayerStrategy implements SimpleCardStrategy {

	private StrategyUtil strategyUtil = new StrategyUtil();

	@Override
	public Card getPlayableCard(List<Card> cardsInHand, Match match) {

		List<PlayedCard> cardsOnTable = new ArrayList<PlayedCard>(match.getCardsOnTable());
		Ansage trumpf = match.getAnsage();
		Card firstPlayedCard = cardsOnTable.isEmpty() ? null : cardsOnTable.get(0).getCard();

		if (strategyUtil.getHighestCardOfSameColor(firstPlayedCard, trumpf, cardsInHand) != null) {
			return strategyUtil.getHighestCardOfSameColor(firstPlayedCard, trumpf, cardsInHand);
		} else {
			return strategyUtil.getFirstPlayableCard(cardsInHand, match);
		}
	}

	@Override
	public boolean isResponsible(List<PlayedCard> cardsOnTable) {
		return cardsOnTable.size() == 1;
	}

}
