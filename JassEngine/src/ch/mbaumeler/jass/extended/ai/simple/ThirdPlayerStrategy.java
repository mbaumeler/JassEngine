package ch.mbaumeler.jass.extended.ai.simple;

import java.util.ArrayList;
import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayedCard;

public class ThirdPlayerStrategy implements SimpleCardStrategy {

	private StrategyUtil util = new StrategyUtil();

	@Override
	public PlayedCard getPlayableCard(List<PlayedCard> cardsInHand, Match match) {

		List<PlayedCard> cardsOnTable = new ArrayList<PlayedCard>(match.getCardsOnTable());
		Ansage trumpf = match.getAnsage();
		PlayedCard firstPlayedCard = cardsOnTable.isEmpty() ? null : cardsOnTable.get(0);

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
	public boolean isResponsible(List<PlayedCard> cardsOnTable) {
		return cardsOnTable.size() == 2;
	}

}
