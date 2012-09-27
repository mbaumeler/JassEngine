package ch.mbaumeler.jass.core.card;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CardSuitTest {

	@Test
	public void testNumberOfSuites() {
		assertEquals(4, CardSuit.values().length);
	}

}
