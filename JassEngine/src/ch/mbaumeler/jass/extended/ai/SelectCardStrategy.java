package ch.mbaumeler.jass.extended.ai;

import java.util.ArrayList;
import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.PlayerToken;

public class SelectCardStrategy {

	private List<CardStrategy> strategies = new ArrayList<CardStrategy>(4);

	public SelectCardStrategy() {
		strategies.add(new FirstPlayerStrategy());
		strategies.add(new SecondPlayerStrategy());
		strategies.add(new ThirdPlayerStrategy());
		strategies.add(new FourthPlayerStrategy());
	}

	public Card getCard(Match match) {
		PlayerToken activePlayer = match.getActivePlayer();
		List<Card> cardsInHand = match.getCards(activePlayer);

		for (CardStrategy strategy : strategies) {
			if (strategy.isResponsible(match.getCardsOnTable())) {
				return strategy.getPlayableCard(new ArrayList<Card>(cardsInHand), match);
			}
		}
		throw new IllegalStateException("No strategy to play");
	}
}
