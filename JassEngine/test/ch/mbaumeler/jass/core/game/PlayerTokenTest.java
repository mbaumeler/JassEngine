package ch.mbaumeler.jass.core.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/* REVIEW NEEDED */ public class PlayerTokenTest {

	@Test
	public void testNumberOfTokens() {
		assertEquals(4, PlayerToken.values().length);
	}
}
