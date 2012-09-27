package ch.mbaumeler.jass.core.card;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CardValueTest {

	@Test
	public void test() {
		assertEquals(9, CardValue.values().length);
	}

}
