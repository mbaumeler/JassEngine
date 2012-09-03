package ch.mbaumeler.jass.core.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.mbaumeler.jass.core.game.PlayerToken;

public class PlayerTokenTest {

	@Test
	public void testGetName() {
		PlayerToken player = new PlayerToken("mab");
		assertEquals("mab", player.getName());
	}

	@Test
	public void testToString() {
		PlayerToken player = new PlayerToken("mab");
		assertEquals("mab", player.toString());
	}

}
