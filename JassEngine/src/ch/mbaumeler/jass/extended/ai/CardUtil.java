package ch.mbaumeler.jass.extended.ai;

import static ch.mbaumeler.jass.core.card.CardSuit.CLUBS;
import static ch.mbaumeler.jass.core.card.CardSuit.DIAMONDS;
import static ch.mbaumeler.jass.core.card.CardSuit.HEARTS;
import static ch.mbaumeler.jass.core.card.CardSuit.SPADES;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.card.CardSuit;

public class CardUtil {

	public Map<CardSuit, List<Card>> createCardMap(List<Card> cards) {

		Map<CardSuit, List<Card>> map = new HashMap<CardSuit, List<Card>>();
		map.put(CLUBS, new ArrayList<Card>());
		map.put(HEARTS, new ArrayList<Card>());
		map.put(SPADES, new ArrayList<Card>());
		map.put(DIAMONDS, new ArrayList<Card>());

		for (Card card : cards) {
			switch (card.getSuit()) {
			case CLUBS:
				map.get(CLUBS).add(card);
				break;
			case HEARTS:
				map.get(HEARTS).add(card);
				break;
			case SPADES:
				map.get(SPADES).add(card);
				break;
			case DIAMONDS:
				map.get(DIAMONDS).add(card);
				break;
			}
		}
		return map;
	}
}
