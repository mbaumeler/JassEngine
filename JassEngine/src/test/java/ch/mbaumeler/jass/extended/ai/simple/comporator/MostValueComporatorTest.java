package ch.mbaumeler.jass.extended.ai.simple.comporator;

import static ch.mbaumeler.jass.core.card.CardSuit.HEARTS;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_TEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.extended.ai.simple.comparator.MostValueComparator;

/* REVIEW NEEDED */ public class MostValueComporatorTest {

	private MostValueComparator mostValueComporator;

	@Before
	public void setUp() {
		mostValueComporator = new MostValueComparator(new Ansage(HEARTS));
	}

	@Test
	public void test() {
		List<Card> cardsInput = Arrays.asList(DIAMONDS_QUEEN, DIAMONDS_TEN, DIAMONDS_EIGHT, DIAMONDS_ACE);
		List<Card> expected = Arrays.asList(DIAMONDS_ACE, DIAMONDS_TEN, DIAMONDS_QUEEN, DIAMONDS_EIGHT);

		assertFalse(cardsInput.equals(expected));
		Collections.sort(cardsInput, mostValueComporator);
		assertEquals(expected, cardsInput);
	}
}
