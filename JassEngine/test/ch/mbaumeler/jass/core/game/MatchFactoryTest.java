package ch.mbaumeler.jass.core.game;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.game.impl.CardFactoryImpl;

public class MatchFactoryTest {

	private MatchFactory matchFactory;
	private PlayerToken startingPlayer = PlayerToken.PLAYER0;

	@Before
	public void setUp() throws Exception {
		matchFactory = new MatchFactory();
		matchFactory.playerRepository = new PlayerTokenRepository();
		matchFactory.cardFactory = new CardFactoryImpl();
	}

	@Test
	public void testCreateNewMatch() {
		assertNotNull(matchFactory.createMatch(startingPlayer));
	}
}
