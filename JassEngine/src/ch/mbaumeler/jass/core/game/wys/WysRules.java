package ch.mbaumeler.jass.core.game.wys;

import static ch.mbaumeler.jass.core.card.CardValue.KING;
import static ch.mbaumeler.jass.core.card.CardValue.QUEEN;
import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.BLATT;
import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.STOECK;
import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.VIER_GLEICHE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.card.CardValue;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayedCard;

public class WysRules {

	public Set<Wys> findWyss(List<PlayedCard> cards, Ansage trumpf) {

		Set<Wys> foundWyss = new HashSet<Wys>();
		List<PlayedCard> sorted = sortBySuitAndValue(cards);

		Map<CardValue, Set<PlayedCard>> cardsWithSameValueMap = new HashMap<CardValue, Set<PlayedCard>>();

		Set<PlayedCard> stoeck = new HashSet<PlayedCard>();

		List<PlayedCard> analyzeBlattStack = new ArrayList<PlayedCard>();

		for (PlayedCard card : sorted) {
			analyseForFourCardsWithSameValue(foundWyss, cardsWithSameValueMap, card);
			analyseForStoeck(foundWyss, card, trumpf, stoeck);
			analyseForBlatt(foundWyss, analyzeBlattStack, card);
		}

		if (analyzeBlattStack.size() >= 3) {
			foundWyss.add(new Wys(analyzeBlattStack, BLATT));
		}
		return foundWyss;
	}

	private void analyseForBlatt(Set<Wys> foundWyss, List<PlayedCard> analyzeBlattStack, PlayedCard card) {
		if (isSameSuitAndFollowingPrevious(lastCardFromStack(analyzeBlattStack), card)) {
			analyzeBlattStack.add(card);
		} else {
			if (analyzeBlattStack.size() >= 3) {
				foundWyss.add(new Wys(analyzeBlattStack, BLATT));
			}
			analyzeBlattStack.clear();
			analyzeBlattStack.add(card);
		}
	}

	private void analyseForStoeck(Set<Wys> foundWyss, PlayedCard card, Ansage trumpf, Set<PlayedCard> stoeck) {
		CardSuit suit = card.getSuit();
		CardValue value = card.getValue();
		if (trumpf.is(suit) && (value == KING || value == QUEEN)) {
			stoeck.add(card);
		}
		if (stoeck.size() == 2) {
			foundWyss.add(new Wys(stoeck, STOECK));
		}
	}

	private void analyseForFourCardsWithSameValue(Set<Wys> foundWyss, Map<CardValue, Set<PlayedCard>> valueMap,
			PlayedCard card) {
		CardValue value = card.getValue();
		if (valueMap.get(value) == null) {
			valueMap.put(value, new HashSet<PlayedCard>());
		}
		Set<PlayedCard> set = valueMap.get(value);
		set.add(card);
		if (set.size() == 4) {
			foundWyss.add(new Wys(set, VIER_GLEICHE));
		}
	}

	private List<PlayedCard> sortBySuitAndValue(Collection<PlayedCard> cards) {
		List<PlayedCard> sorted = new ArrayList<PlayedCard>(cards);
		Collections.sort(sorted, new Comparator<PlayedCard>() {
			public int compare(PlayedCard card1, PlayedCard card2) {
				int suit = card1.getSuit().compareTo(card2.getSuit());
				return (suit != 0) ? suit : card1.getValue().compareTo(card2.getValue());
			}
		});
		return sorted;
	}

	private PlayedCard lastCardFromStack(List<PlayedCard> blattStack) {
		return blattStack.isEmpty() ? null : blattStack.get(blattStack.size() - 1);
	}

	private boolean isSameSuitAndFollowingPrevious(PlayedCard card, PlayedCard card2) {

		return card != null && card2 != null && card.getSuit() == card2.getSuit()
				&& card.getValue().ordinal() == card2.getValue().ordinal() - 1;
	}
}
