package ch.mbaumeler.jass.core.game.wys;

import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.BLATT;
import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.STOECK;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_NINE;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_QUEEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.wys.Wys.WysTyp;

public class WysTest {

	@Test
	public void testGetCards() {
		Set<Card> set = new HashSet<Card>();
		Wys wys = new Wys(set, BLATT);
		assertEquals(set, wys.getCards());
	}

	@Test
	public void testEquals() {
		Wys wys = createWys(BLATT, CLUBS_EIGHT);
		Wys wys2 = createWys(BLATT, CLUBS_EIGHT);
		assertEquals(wys, wys2);
	}

	@Test
	public void testNotEqualsWithDifferentValue() {
		Wys wys = createWys(BLATT, HEARTS_QUEEN);
		Wys wys2 = createWys(BLATT, CLUBS_EIGHT);
		assertFalse(wys.equals(wys2));
	}

	@Test
	public void testNotEqualsWithDifferentWys() {
		Wys wys = createWys(STOECK, HEARTS_QUEEN);
		Wys wys2 = createWys(BLATT, HEARTS_QUEEN);
		assertFalse(wys.equals(wys2));
	}

	@Test
	public void testGetTyp() {
		Wys wys = createWys(STOECK, HEARTS_QUEEN);
		assertEquals(STOECK, wys.getTyp());
	}

	@Test
	public void testHashcode() {
		Wys wys = createWys(BLATT, CLUBS_EIGHT);
		Wys wys2 = createWys(BLATT, CLUBS_EIGHT);
		assertEquals(wys.hashCode(), wys2.hashCode());
	}

	@Test
	public void testHashcodeWithDifferentWys() {
		Wys wys = createWys(BLATT, CLUBS_EIGHT);
		Wys wys2 = createWys(BLATT, CLUBS_NINE);
		assertFalse(wys.hashCode() == wys2.hashCode());
	}

	@Test
	public void testEqualsWithDifferentValues() {
		Wys wys = createWys(BLATT, CLUBS_EIGHT);
		assertTrue(wys.equals(wys));
		assertFalse(wys.equals(null));
		assertFalse(wys.equals(new Object()));
	}

	private Wys createWys(WysTyp typ, Card... cards) {
		Set<Card> set = new HashSet<Card>(Arrays.asList(cards));
		return new Wys(set, typ);
	}

}
