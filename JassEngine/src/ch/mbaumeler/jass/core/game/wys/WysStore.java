package ch.mbaumeler.jass.core.game.wys;

import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.BLATT;
import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.STOECK;
import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.VIER_GLEICHE;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.Card;
import ch.mbaumeler.jass.core.game.PlayerToken;

public class WysStore {

	private Map<PlayerToken, Set<Wys>> wysMap = new LinkedHashMap<PlayerToken, Set<Wys>>();

	private PlayerToken stoeckPlayer;

	private WysRules wysRule;

	private WysScoreRule wysScoreRule;

	private Match match;

	public WysStore(WysRules wysRule, WysScoreRule wysScoreRule, Match match) {
		this.wysScoreRule = wysScoreRule;
		this.wysRule = wysRule;
		this.match = match;
	}

	public void addWys(Set<Wys> wysSet) {

		PlayerToken activePlayer = match.getActivePlayer();
		List<Card> cardsInHand = match.getCards(activePlayer);
		Set<Wys> allWysFromActivePlayer = wysRule.findWyss(cardsInHand, match.getAnsage());

		for (Wys wys : wysSet) {

			if (wys.getTyp() == STOECK) {
				wysStoeck(activePlayer, cardsInHand, wys);

			} else {
				boolean playerHasCard = allWysFromActivePlayer.contains(wys);
				if ((isFirstRound()) && playerHasCard) {
					addWysToMap(activePlayer, wys);
				} else {
					throw new IllegalArgumentException("Active player does not have " + wys.getCards()
							+ " or wys is not possible in this round.");
				}
			}
		}
	}

	public PlayerToken getStoeckFromPlayer() {
		return stoeckPlayer;
	}

	public PlayerToken getBestWys(Ansage ansage) {

		Set<Entry<PlayerToken, Set<Wys>>> entrySet = wysMap.entrySet();

		PlayerToken bestPlayer = null;
		Wys currentMaxWys = null;
		for (Entry<PlayerToken, Set<Wys>> entry : entrySet) {
			for (Wys wys : entry.getValue()) {
				int score = wysScoreRule.getScoreFore(wys);
				int scoreCurrentMax = currentMaxWys == null ? 0 : wysScoreRule.getScoreFore(currentMaxWys);
				if (score > scoreCurrentMax) {
					bestPlayer = entry.getKey();
					currentMaxWys = wys;
				} else if (score == scoreCurrentMax) {

					if (isBlattWys(wys) && wys.getCards().size() == 5 && currentMaxWys.getTyp() == VIER_GLEICHE) {
						bestPlayer = entry.getKey();
						currentMaxWys = wys;
					} else if (isBlattWys(wys) && isBlattWys(currentMaxWys) && isHigher(wys, currentMaxWys)) {
						bestPlayer = entry.getKey();
						currentMaxWys = wys;
					} else if (ansage.is(wys.getCards().iterator().next().getSuit())) {
						bestPlayer = entry.getKey();
						currentMaxWys = wys;
					}
				}
			}
		}
		return bestPlayer;
	}

	private boolean isHigher(Wys wys, Wys currentMaxWys) {
		return getHighestValue(wys.getCards()) > getHighestValue(currentMaxWys.getCards());
	}

	private boolean isBlattWys(Wys wys) {
		return wys.getTyp() == BLATT;
	}

	private int getHighestValue(Set<Card> cards) {
		int maxValue = 0;
		for (Card card : cards) {
			int currentValue = card.getValue().ordinal();
			maxValue = currentValue > maxValue ? currentValue : maxValue;
		}
		return maxValue;
	}

	public Set<Wys> getWys(PlayerToken playerToken) {
		return wysMap.get(playerToken);
	}

	private void wysStoeck(PlayerToken activePlayer, List<Card> cardsInHand, Wys wys) {
		Iterator<Card> iterator = wys.getCards().iterator();
		Card card1 = iterator.next();
		Card card2 = iterator.next();
		Set<Card> playedCardsFromPlayer = getPlayedCardsFromPlayer(activePlayer);
		if (inHandAndInHandOrAlreadyPlayed(cardsInHand, playedCardsFromPlayer, card1, card2)
				|| inHandAndInHandOrAlreadyPlayed(cardsInHand, playedCardsFromPlayer, card2, card1)) {
			stoeckPlayer = activePlayer;
		} else {
			throw new IllegalStateException("Can't wys stoeck");
		}
	}

	private void addWysToMap(PlayerToken activePlayer, Wys wys) {
		if (wysMap.get(activePlayer) == null) {
			wysMap.put(activePlayer, new HashSet<Wys>());
		}
		wysMap.get(activePlayer).add(wys);
	}

	private boolean inHandAndInHandOrAlreadyPlayed(List<Card> cardsInHand, Set<Card> playedCardsFromPlayer,
			Card card1, Card card2) {
		return cardsInHand.contains(card1) && (cardsInHand.contains(card2) || playedCardsFromPlayer.contains(card2));
	}

	private Set<Card> getPlayedCardsFromPlayer(PlayerToken player) {
		Set<Card> playedCards = new HashSet<Card>();
		for (int i = 0; i < match.getRoundsCompleted(); i++) {
			List<Card> cardsFromRound = match.getCardsFromRound(i);
			for (Card playedCard : cardsFromRound) {
				if (playedCard.getPlayer() == player) {
					playedCards.add(playedCard);
				}
			}
		}
		return playedCards;
	}

	private boolean isFirstRound() {
		return match.getRoundsCompleted() == 0;
	}
}
