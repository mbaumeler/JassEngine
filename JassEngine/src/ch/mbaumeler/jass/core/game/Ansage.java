package ch.mbaumeler.jass.core.game;

import static ch.mbaumeler.jass.core.game.Ansage.SpielModi.TRUMPF;
import ch.mbaumeler.jass.core.card.CardSuit;

public class Ansage {

	public enum SpielModi {
		OBENABE, UNDEUFE, TRUMPF
	};

	private final SpielModi spielModi;
	private final CardSuit cardSuit;

	public Ansage(CardSuit cardSuit) {
		assertNotNull(cardSuit);
		this.cardSuit = cardSuit;
		this.spielModi = TRUMPF;
	}

	public Ansage(SpielModi spielModi) {
		assertNotNull(spielModi);
		if (spielModi == TRUMPF) {
			throw new IllegalArgumentException("Trumpf is not allowed as spielmodi. Use CardSuit constructor instead.");
		}
		this.spielModi = spielModi;
		this.cardSuit = null;
	}

	private void assertNotNull(Object object) {
		if (object == null) {
			throw new IllegalArgumentException("Trumpf value can't be null");
		}
	}

	public boolean isTrumpf(CardSuit cardSuit) {
		return spielModi == TRUMPF && this.cardSuit == cardSuit;
	}

	public SpielModi getSpielModi() {
		return spielModi;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardSuit == null) ? 0 : cardSuit.hashCode());
		result = prime * result + ((spielModi == null) ? 0 : spielModi.hashCode());
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
		Ansage other = (Ansage) obj;
		if (cardSuit != other.cardSuit)
			return false;
		if (spielModi != other.spielModi)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return spielModi == TRUMPF ? TRUMPF.name() + " - " + cardSuit.name() : spielModi.name();
	}

	public boolean isTrumpf() {
		return cardSuit != null;
	}

}
