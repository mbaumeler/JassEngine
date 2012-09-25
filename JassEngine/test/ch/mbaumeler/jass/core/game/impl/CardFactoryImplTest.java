package ch.mbaumeler.jass.core.game.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.CardFactory;
import ch.mbaumeler.jass.core.game.PlayedCard;

public class CardFactoryImplTest {

	private CardFactory cardFactory;

	@Before
	public void setUp() throws Exception {
		cardFactory = new CardFactoryImpl();
	}

	@Test
	public void testCreateShuffledCards() {
		List<PlayedCard> first = cardFactory.createShuffledCards();
		List<PlayedCard> second = cardFactory.createShuffledCards();

		assertNotNull(first);
		assertNotNull(second);
		assertFalse(first.equals(second));
	}

}
