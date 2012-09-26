package ch.mbaumeler.jass.core.game;

import static ch.mbaumeler.jass.core.card.CardSuit.CLUBS;
import static ch.mbaumeler.jass.core.card.CardSuit.DIAMONDS;
import static ch.mbaumeler.jass.core.card.CardSuit.HEARTS;
import static ch.mbaumeler.jass.core.game.Ansage.SpielModi.OBENABE;
import static ch.mbaumeler.jass.core.game.Ansage.SpielModi.UNDEUFE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.game.Ansage.SpielModi;

/* REVIEW NEEDED */ public class AnsageTest {

	@Test
	public void testIsTrumpf() {
		Ansage ansage = new Ansage(CLUBS);
		assertTrue(ansage.is(CLUBS));
		assertFalse(ansage.is(HEARTS));
		assertEquals(SpielModi.TRUMPF, ansage.getSpielModi());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTrumpfWithoutSuit() {
		new Ansage(SpielModi.TRUMPF);
	}

	@Test
	public void testIsSpielModi() {
		Ansage ansage = new Ansage(UNDEUFE);
		assertEquals(UNDEUFE, ansage.getSpielModi());
		assertFalse(ansage.is(CLUBS));
	}

	@Test
	public void testNullIsNeverTrumpf() {
		Ansage ansage = new Ansage(UNDEUFE);
		assertFalse(ansage.is((SpielModi) null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullTrumpf() {
		new Ansage((CardSuit) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullSpielModi() {
		new Ansage((SpielModi) null);
	}

	@Test
	public void testHashCodeSuitAnsage() {
		Ansage clubsAnsage = new Ansage(CLUBS);
		Ansage clubsAnsage2 = new Ansage(CLUBS);
		assertEquals(clubsAnsage.hashCode(), clubsAnsage2.hashCode());
	}

	@Test
	public void testHashCodeSuitSpielModi() {
		Ansage ansage = new Ansage(OBENABE);
		Ansage ansage2 = new Ansage(OBENABE);
		assertEquals(ansage.hashCode(), ansage2.hashCode());
	}

	@Test
	public void testEqualsSpielModi() {
		Ansage clubsAnsage = new Ansage(OBENABE);
		assertTrue(clubsAnsage.equals(clubsAnsage));
		assertFalse(clubsAnsage.equals(null));
		assertFalse(clubsAnsage.equals(new Object()));
		assertFalse(clubsAnsage.equals(new Ansage(UNDEUFE)));
		assertEquals(clubsAnsage, new Ansage(OBENABE));
	}

	@Test
	public void testEqualsTrumpf() {
		Ansage clubsAnsage = new Ansage(HEARTS);
		assertTrue(clubsAnsage.equals(clubsAnsage));
		assertFalse(clubsAnsage.equals(null));
		assertFalse(clubsAnsage.equals(new Object()));
		assertFalse(clubsAnsage.equals(new Ansage(DIAMONDS)));
		assertEquals(clubsAnsage, new Ansage(HEARTS));
	}

	@Test
	public void testToString() {
		assertEquals("TRUMPF - HEARTS", new Ansage(HEARTS).toString());
		assertEquals("UNDEUFE", new Ansage(UNDEUFE).toString());
	}

}
