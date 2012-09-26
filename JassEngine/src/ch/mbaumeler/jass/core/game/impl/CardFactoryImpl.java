package ch.mbaumeler.jass.core.game.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ch.mbaumeler.jass.core.CardFactory;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.card.CardValue;
import ch.mbaumeler.jass.core.game.PlayerToken;

/* REVIEW NEEDED */ public class CardFactoryImpl implements CardFactory {

	@Override
	public List<Card> createShuffledCards() {

		List<Card> playedCards = new ArrayList<Card>();

		List<PlayerToken> playerToken = shuffeldPlayerList();

		int playerTokenIndex = 0;
		for (CardValue value : shuffeldValues()) {
			for (CardSuit cardSuit : CardSuit.values()) {
				playedCards.add(new Card(cardSuit, value, playerToken.get(playerTokenIndex++)));
			}
		}
		return playedCards;
	}

	private List<CardValue> shuffeldValues() {
		List<CardValue> list = Arrays.asList(CardValue.values());
		Collections.shuffle(list);
		return list;
	}

	private List<PlayerToken> shuffeldPlayerList() {
		List<PlayerToken> playerToken = new ArrayList<PlayerToken>(9 * PlayerToken.values().length);
		for (int i = 0; i < 9; i++) {
			playerToken.addAll(Arrays.asList(PlayerToken.values()));
		}
		Collections.shuffle(playerToken);
		return playerToken;
	}
}
