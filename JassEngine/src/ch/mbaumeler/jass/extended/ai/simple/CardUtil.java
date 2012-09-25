package ch.mbaumeler.jass.extended.ai.simple;

import static ch.mbaumeler.jass.core.card.CardSuit.CLUBS;
import static ch.mbaumeler.jass.core.card.CardSuit.DIAMONDS;
import static ch.mbaumeler.jass.core.card.CardSuit.HEARTS;
import static ch.mbaumeler.jass.core.card.CardSuit.SPADES;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.game.PlayedCard;

public class CardUtil {

	public Map<CardSuit, List<PlayedCard>> createCardMap(List<PlayedCard> cards) {

		Map<CardSuit, List<PlayedCard>> map = new HashMap<CardSuit, List<PlayedCard>>();
		map.put(CLUBS, new ArrayList<PlayedCard>());
		map.put(HEARTS, new ArrayList<PlayedCard>());
		map.put(SPADES, new ArrayList<PlayedCard>());
		map.put(DIAMONDS, new ArrayList<PlayedCard>());

		for (PlayedCard card : cards) {
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
