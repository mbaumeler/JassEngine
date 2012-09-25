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
import ch.mbaumeler.jass.core.game.Card;

public class WysRules {

	public Set<Wys> findWyss(List<Card> cards, Ansage ansage) {

		Set<Wys> foundWyss = new HashSet<Wys>();
		List<Card> sorted = sortBySuitAndValue(cards);

		Map<CardValue, Set<Card>> cardsWithSameValueMap = new HashMap<CardValue, Set<Card>>();

		Set<Card> stoeck = new HashSet<Card>();

		List<Card> analyzeBlattStack = new ArrayList<Card>();

		for (Card card : sorted) {
			analyseForFourCardsWithSameValue(foundWyss, cardsWithSameValueMap, card);
			analyseForStoeck(foundWyss, card, ansage, stoeck);
			analyseForBlatt(foundWyss, analyzeBlattStack, card);
		}

		if (analyzeBlattStack.size() >= 3) {
			foundWyss.add(new Wys(analyzeBlattStack, BLATT));
		}
		return foundWyss;
	}

	private void analyseForBlatt(Set<Wys> foundWyss, List<Card> analyzeBlattStack, Card card) {
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

	private void analyseForStoeck(Set<Wys> foundWyss, Card card, Ansage trumpf, Set<Card> stoeck) {
		CardSuit suit = card.getSuit();
		CardValue value = card.getValue();
		if (trumpf.is(suit) && (value == KING || value == QUEEN)) {
			stoeck.add(card);
		}
		if (stoeck.size() == 2) {
			foundWyss.add(new Wys(stoeck, STOECK));
		}
	}

	private void analyseForFourCardsWithSameValue(Set<Wys> foundWyss, Map<CardValue, Set<Card>> valueMap,
			Card card) {
		CardValue value = card.getValue();
		if (valueMap.get(value) == null) {
			valueMap.put(value, new HashSet<Card>());
		}
		Set<Card> set = valueMap.get(value);
		set.add(card);
		if (set.size() == 4) {
			foundWyss.add(new Wys(set, VIER_GLEICHE));
		}
	}

	private List<Card> sortBySuitAndValue(Collection<Card> cards) {
		List<Card> sorted = new ArrayList<Card>(cards);
		Collections.sort(sorted, new Comparator<Card>() {
			public int compare(Card card1, Card card2) {
				int suit = card1.getSuit().compareTo(card2.getSuit());
				return (suit != 0) ? suit : card1.getValue().compareTo(card2.getValue());
			}
		});
		return sorted;
	}

	private Card lastCardFromStack(List<Card> blattStack) {
		return blattStack.isEmpty() ? null : blattStack.get(blattStack.size() - 1);
	}

	private boolean isSameSuitAndFollowingPrevious(Card card, Card card2) {

		return card != null && card2 != null && card.getSuit() == card2.getSuit()
				&& card.getValue().ordinal() == card2.getValue().ordinal() - 1;
	}
}
