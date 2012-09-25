package ch.mbaumeler.jass.extended.ai.simple;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayedCard;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.extended.ai.JassStrategy;

public class SimpleStrategy implements JassStrategy {

	@Inject
	private SelectTrumpfStrategy selectTrumpfStrategy;

	@Inject
	@Named(value = "strategies")
	private List<SimpleCardStrategy> strategies;

	@Override
	public PlayedCard getCardToPlay(Match match) {
		PlayerToken activePlayer = match.getActivePlayer();
		List<PlayedCard> cardsInHand = match.getCards(activePlayer);

		for (SimpleCardStrategy strategy : strategies) {
			if (strategy.isResponsible(match.getCardsOnTable())) {
				return strategy.getPlayableCard(new ArrayList<PlayedCard>(cardsInHand), match);
			}
		}
		throw new IllegalStateException("No strategy to play");
	}

	@Override
	public Ansage getAnsage(Match match) {
		return selectTrumpfStrategy.getAnsage(match);
	}
}
