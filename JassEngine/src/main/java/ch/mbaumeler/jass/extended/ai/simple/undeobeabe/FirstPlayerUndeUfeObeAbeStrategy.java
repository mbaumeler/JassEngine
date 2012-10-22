package ch.mbaumeler.jass.extended.ai.simple.undeobeabe;

import java.util.List;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.Ansage.SpielModi;
import ch.mbaumeler.jass.extended.ai.simple.SimpleSelectCardStrategy;

/* REVIEW NEEDED */public class FirstPlayerUndeUfeObeAbeStrategy implements SimpleSelectCardStrategy {

	@Override
	public Card getPlayableCard(List<Card> cardsInHand, Match match) {

		Card currentCardToPlay = null;
		SpielModi spielModi = match.getAnsage().getSpielModi();
		List<Card> cards = match.getCards(match.getActivePlayer());

		for (Card card : cards) {
			if (match.isCardPlayable(card) && isBetterThanCurrentCard(currentCardToPlay, card, spielModi)) {
				currentCardToPlay = card;
			}
		}
		return currentCardToPlay;
	}

	private boolean isBetterThanCurrentCard(Card currentCard, Card card, SpielModi spielModi) {
		return (currentCard == null || isBetter(currentCard, card, spielModi));
	}

	private boolean isBetter(Card currentCard, Card card, SpielModi spielModi) {
		int ordinalCurrent = card.getValue().ordinal();
		int ordinalCard = currentCard.getValue().ordinal();
		return spielModi == SpielModi.OBENABE ? ordinalCurrent < ordinalCard : ordinalCurrent > ordinalCard;
	}

	@Override
	public boolean isResponsible(Match match) {
		return match.getCardsOnTable().isEmpty() && !match.getAnsage().isTrumpf();
	}
}
