package ch.mbaumeler.jass.extended.ai;

import static ch.mbaumeler.jass.core.card.CardSuit.CLUBS;
import static ch.mbaumeler.jass.core.card.CardSuit.DIAMONDS;
import static ch.mbaumeler.jass.core.card.CardSuit.HEARTS;
import static ch.mbaumeler.jass.core.card.CardSuit.SPADES;

import java.util.List;
import java.util.Map;

import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.ScoreRules;

public class SelectTrumpfStrategy {

	private ScoreRules scoreUtil = new ScoreRules();

	private Map<CardSuit, List<Card>> map;

	public Ansage getTrumpf(List<Card> cards) {

		map = new CardUtil().createCardMap(cards);

		int scoreClubs = getScore(CLUBS);
		int scoreHearts = getScore(HEARTS);
		int scoreSpades = getScore(SPADES);
		int scoreDiamonds = getScore(DIAMONDS);

		int score = 0;
		Ansage trumpf = null;
		if (scoreClubs > score) {
			trumpf = new Ansage(CLUBS);
			score = scoreClubs;
		}
		if (scoreHearts > score) {
			trumpf = new Ansage(HEARTS);
			score = scoreHearts;
		}

		if (scoreSpades > score) {
			trumpf = new Ansage(SPADES);
			score = scoreSpades;
		}
		if (scoreDiamonds > score) {
			trumpf = new Ansage(DIAMONDS);
			score = scoreDiamonds;
		}

		return trumpf;
	}

	private int getScore(CardSuit suit) {
		Ansage ansage = new Ansage(suit);
		List<Card> cards = map.get(suit);
		int scores = 0;
		for (Card card : cards) {
			scores += scoreUtil.getScore(card, ansage);
		}
		return scores;
	}

}
