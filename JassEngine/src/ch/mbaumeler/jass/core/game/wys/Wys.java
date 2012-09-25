package ch.mbaumeler.jass.core.game.wys;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import ch.mbaumeler.jass.core.card.Card;

public class Wys {

	private final Set<Card> cards;

	private final WysTyp typ;

	public enum WysTyp {
		STOECK, BLATT, VIER_GLEICHE
	};

	public Wys(Collection<Card> cards, WysTyp wysTyp) {
		this.typ = wysTyp;
		this.cards = new HashSet<Card>(cards);
	}

	public WysTyp getTyp() {
		return this.typ;
	}

	public Set<Card> getCards() {
		return cards;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cards == null) ? 0 : cards.hashCode());
		result = prime * result + ((typ == null) ? 0 : typ.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wys other = (Wys) obj;
		if (cards == null) {
			if (other.cards != null)
				return false;
		} else if (!cards.equals(other.cards))
			return false;
		if (typ != other.typ)
			return false;
		return true;
	}

}
