package ch.mbaumeler.jass.extended.ai.simple;

import java.util.ArrayList;
import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.extended.ai.PlayStrategy;

public class SelectCardStrategy implements PlayStrategy {

	private List<SimpleCardStrategy> strategies = new ArrayList<SimpleCardStrategy>(
			4);

	public SelectCardStrategy() {
		strategies.add(new FirstPlayerStrategy());
		strategies.add(new SecondPlayerStrategy());
		strategies.add(new ThirdPlayerStrategy());
		strategies.add(new FourthPlayerStrategy());
	}

	public Card getCardToPlay(Match match) {
		PlayerToken activePlayer = match.getActivePlayer();
		List<Card> cardsInHand = match.getCards(activePlayer);

		for (SimpleCardStrategy strategy : strategies) {
			if (strategy.isResponsible(match.getCardsOnTable())) {
				return strategy.getPlayableCard(
						new ArrayList<Card>(cardsInHand), match);
			}
		}
		throw new IllegalStateException("No strategy to play");
	}

}
