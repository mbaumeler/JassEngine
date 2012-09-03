package ch.mbaumeler.jass.core.card;

import static ch.mbaumeler.jass.core.card.CardSuit.CLUBS;
import static ch.mbaumeler.jass.core.card.CardSuit.DIAMONDS;
import static ch.mbaumeler.jass.core.card.CardValue.ACE;
import static ch.mbaumeler.jass.core.card.CardValue.JACK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CardTest {

	@Test
	public void testEquals() {
		assertEquals(new Card(CLUBS, ACE), new Card(CLUBS, ACE));
	}

	@Test
	public void testHashCode() {
		assertEquals(new Card(CLUBS, ACE).hashCode(), new Card(CLUBS, ACE).hashCode());
	}

	@Test
	public void testEqualsWithSame() {
		Card card1 = new Card(CLUBS, ACE);
		assertTrue(card1.equals(card1));
	}

	@Test
	public void testUnequalsSuit() {
		Card card1 = new Card(CLUBS, ACE);
		Card card2 = new Card(DIAMONDS, ACE);
		assertFalse(card1.equals(card2));
		assertFalse(card1.hashCode() == card2.hashCode());
	}

	@Test
	public void testUnequalsValue() {
		Card card1 = new Card(CLUBS, JACK);
		Card card2 = new Card(CLUBS, ACE);
		assertFalse(card1.equals(card2));
		assertFalse(card1.hashCode() == card2.hashCode());
	}

	@Test
	public void testUnequalsNull() {
		assertFalse(new Card(CLUBS, ACE).equals(null));
	}

	@Test
	public void testUnequalsEmptyObject() {
		Card card1 = new Card(CLUBS, ACE);
		Object object = new Object();
		assertFalse(card1.equals(object));
		assertFalse(card1.hashCode() == object.hashCode());
	}

	@Test
	public void testGetSuit() {
		Card card1 = new Card(CLUBS, ACE);
		assertEquals(CLUBS, card1.getSuit());
	}

	@Test
	public void testGetValue() {
		Card card1 = new Card(CLUBS, ACE);
		assertEquals(ACE, card1.getValue());
	}

	@Test
	public void testToString() {
		assertEquals("CLUBS_ACE", new Card(CLUBS, ACE).toString());
	}

}
