package ch.mbaumeler.jass.test.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ch.mbaumeler.jass.core.CardFactory;
import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.card.CardValue;
import ch.mbaumeler.jass.core.game.PlayedCard;
import ch.mbaumeler.jass.core.game.PlayerToken;

public class PredictableCardFactory implements CardFactory {

	private static final long INITVALUE = 4134132L;

	private Random random = new Random(INITVALUE);

	@Override
	public List<PlayedCard> createShuffledCards() {

		List<PlayedCard> playedCards = new ArrayList<PlayedCard>();

		List<PlayerToken> playerToken = shuffeldPlayerList();

		int playerTokenIndex = 0;
		for (CardValue value : shuffeldValues()) {
			for (CardSuit cardSuit : CardSuit.values()) {
				playedCards.add(new PlayedCard(cardSuit, value, playerToken.get(playerTokenIndex++)));
			}
		}
		return playedCards;
	}

	private List<CardValue> shuffeldValues() {
		List<CardValue> list = Arrays.asList(CardValue.values());
		Collections.shuffle(list, random);
		return list;
	}

	private List<PlayerToken> shuffeldPlayerList() {
		List<PlayerToken> playerToken = new ArrayList<PlayerToken>(9 * PlayerToken.values().length);
		for (int i = 0; i < 9; i++) {
			playerToken.addAll(Arrays.asList(PlayerToken.values()));
		}
		Collections.shuffle(playerToken, random);
		return playerToken;
	}
}
