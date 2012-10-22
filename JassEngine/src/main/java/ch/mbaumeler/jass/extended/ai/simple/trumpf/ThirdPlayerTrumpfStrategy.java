package ch.mbaumeler.jass.extended.ai.simple.trumpf;

import java.util.ArrayList;
import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.extended.ai.simple.SimpleSelectCardStrategy;
import ch.mbaumeler.jass.extended.ai.simple.StrategyUtil;

/* REVIEW NEEDED */public class ThirdPlayerTrumpfStrategy implements SimpleSelectCardStrategy {

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
	public boolean isResponsible(Match match) {
		return match.getCardsOnTable().size() == 2 && match.getAnsage().isTrumpf();
	}

}
