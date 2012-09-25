package ch.mbaumeler.jass.core.game.wys;

import static ch.mbaumeler.jass.core.card.CardSuit.DIAMONDS;
import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.BLATT;
import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.STOECK;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_SEVEN;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_NINE;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_NINE;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_SEVEN;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_TEN;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_QUEEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.wys.Wys.WysTyp;

public class WysRulesTest {

	private WysRules wysRule;
	private Ansage trumpf;

	@Before
	public void setUp() throws Exception {
		wysRule = new WysRules();
		trumpf = new Ansage(DIAMONDS);
	}

	@Test
	public void testGetWyssForEmptyList() {
		List<Card> cards = new ArrayList<Card>();
		Set<Wys> wyss = wysRule.findWyss(cards, trumpf);
		assertNotNull(wyss);
	}

	@Test
	public void testFindNoWyss() {
		List<Card> cards = Arrays.asList(CLUBS_JACK, CLUBS_SIX, DIAMONDS_ACE);
		Set<Wys> wyss = wysRule.findWyss(cards, trumpf);
		assertNotNull(wyss);
		assertEquals(0, wyss.size());
	}

	@Test
	public void testDreiblatt() {
		List<Card> cards = Arrays.asList(CLUBS_EIGHT, HEARTS_KING, CLUBS_SIX, CLUBS_SEVEN, DIAMONDS_NINE);
		Set<Wys> expected = new HashSet<Wys>();
		expected.add(new Wys(Arrays.asList(CLUBS_SIX, CLUBS_SEVEN, CLUBS_EIGHT), BLATT));

		Set<Wys> actual = wysRule.findWyss(cards, trumpf);
		assertNotNull(actual);
		assertEquals(1, actual.size());
		assertEquals(expected, actual);
	}

	@Test
	public void testDreiblattUndVierBlatt() {
		List<Card> cards = Arrays.asList(CLUBS_EIGHT, HEARTS_KING, CLUBS_SIX, CLUBS_SEVEN, DIAMONDS_NINE,
				HEARTS_SEVEN, HEARTS_EIGHT, DIAMONDS_JACK, HEARTS_NINE, HEARTS_TEN);
		Set<Wys> expected = new HashSet<Wys>();
		expected.add(new Wys(Arrays.asList(CLUBS_SIX, CLUBS_SEVEN, CLUBS_EIGHT), BLATT));
		expected.add(new Wys(Arrays.asList(HEARTS_SEVEN, HEARTS_EIGHT, HEARTS_NINE, HEARTS_TEN), BLATT));

		Set<Wys> actual = wysRule.findWyss(cards, trumpf);
		assertNotNull(actual);
		assertEquals(2, actual.size());
		assertEquals(expected, actual);
	}

	@Test
	public void testFindNothing() {
		List<Card> cards = Arrays.asList(SPADES_QUEEN, HEARTS_KING, CLUBS_SIX, DIAMONDS_NINE, HEARTS_EIGHT,
				DIAMONDS_QUEEN, HEARTS_NINE, HEARTS_QUEEN);

		Set<Wys> actual = wysRule.findWyss(cards, trumpf);
		assertNotNull(actual);
		assertEquals(0, actual.size());
	}

	@Test
	public void testFourQueens() {
		List<Card> cards = Arrays.asList(SPADES_QUEEN, HEARTS_KING, CLUBS_SIX, CLUBS_QUEEN, DIAMONDS_NINE,
				HEARTS_EIGHT, DIAMONDS_QUEEN, HEARTS_NINE, HEARTS_QUEEN);
		Set<Wys> expected = new HashSet<Wys>();
		expected.add(new Wys(Arrays.asList(HEARTS_QUEEN, DIAMONDS_QUEEN, SPADES_QUEEN, CLUBS_QUEEN),
				WysTyp.VIER_GLEICHE));

		Set<Wys> actual = wysRule.findWyss(cards, trumpf);
		assertNotNull(actual);
		assertEquals(1, actual.size());
		assertEquals(expected, actual);
	}

	@Test
	public void testFindStoeck() {
		List<Card> cards = Arrays.asList(SPADES_QUEEN, HEARTS_KING, HEARTS_QUEEN, DIAMONDS_ACE);
		Set<Wys> expected = new HashSet<Wys>();
		expected.add(new Wys(Arrays.asList(HEARTS_QUEEN, HEARTS_KING), STOECK));

		Set<Wys> actual = wysRule.findWyss(cards, new Ansage(CardSuit.HEARTS));
		assertNotNull(actual);
		assertEquals(1, actual.size());
		assertEquals(expected, actual);
	}

	// @Test
	// public void testGetBestWysWithNoWys() {
	// WysStore wysStoreMock = mock(WysStore.class);
	// LinkedHashMap<Player, Set<Wys>> linkedHashSet = new LinkedHashMap<Player,
	// Set<Wys>>();
	// when(wysStoreMock.getWys()).thenReturn(linkedHashSet);
	// assertNull(wysRule.getBestWys(wysStoreMock));
	// }
	//
	// @Test
	// public void testGetOneWys() {
	// WysStore wysStoreMock = mock(WysStore.class);
	// LinkedHashMap<Player, Set<Wys>> linkedHashSet = new LinkedHashMap<Player,
	// Set<Wys>>();
	// Player player = new Player("test");
	// Set<Wys> wys3blatt = new HashSet<Wys>();
	// wys3blatt.add(new Wys(Arrays.asList(CLUBS_NINE, CLUBS_TEN, CLUBS_JACK),
	// BLATT));
	// linkedHashSet.put(player, wys3blatt);
	// when(wysStoreMock.getWys()).thenReturn(linkedHashSet);
	// Player wysWinner = wysRule.getBestWys(wysStoreMock);
	// assertSame(player, wysWinner);
	// }

	// @Test
	// public void testGetTwoWys() {
	// WysStore wysStoreMock = mock(WysStore.class);
	// LinkedHashMap<Player, Set<Wys>> linkedHashSet = new LinkedHashMap<Player,
	// Set<Wys>>();
	// Player player = new Player("test");
	// Set<Wys> wys3blatt = new HashSet<Wys>();
	// wys3blatt.add(new Wys(Arrays.asList(CLUBS_NINE, CLUBS_TEN, CLUBS_JACK),
	// BLATT));
	// linkedHashSet.put(player, wys3blatt);
	//
	// Player player2 = new Player("test2");
	// Set<Wys> wys4blatt = new HashSet<Wys>();
	// wys4blatt.add(new Wys(Arrays.asList(CLUBS_NINE, CLUBS_TEN, CLUBS_JACK,
	// CLUBS_QUEEN), BLATT));
	// linkedHashSet.put(player2, wys4blatt);
	//
	//
	// when(wysStoreMock.getWys()).thenReturn(linkedHashSet);
	// Player wysWinner = wysRule.getBestWys(wysStoreMock);
	// assertSame(player2, wysWinner);
	// }
	//
	// @Test
	// public void testSameWysButHigherCard() {
	// WysStore wysStoreMock = mock(WysStore.class);
	// LinkedHashMap<Player, Set<Wys>> linkedHashSet = new LinkedHashMap<Player,
	// Set<Wys>>();
	// Player player = new Player("test");
	// Set<Wys> wys3blatt = new HashSet<Wys>();
	// wys3blatt.add(new Wys(Arrays.asList(CLUBS_NINE, CLUBS_TEN, CLUBS_JACK),
	// BLATT));
	// linkedHashSet.put(player, wys3blatt);
	//
	// Player player2 = new Player("test2");
	// Set<Wys> wys4blatt = new HashSet<Wys>();
	// wys4blatt.add(new Wys(Arrays.asList(HEARTS_EIGHT, HEARTS_NINE,
	// HEARTS_TEN), BLATT));
	// linkedHashSet.put(player2, wys4blatt);
	//
	//
	// when(wysStoreMock.getWys()).thenReturn(linkedHashSet);
	// Player wysWinner = wysRule.getBestWys(wysStoreMock);
	// assertSame(player, wysWinner);
	// }
	//
	// @Test
	// public void testSameScoreValueBut5Blatt() {
	// WysStore wysStoreMock = mock(WysStore.class);
	// LinkedHashMap<Player, Set<Wys>> linkedHashSet = new LinkedHashMap<Player,
	// Set<Wys>>();
	// Player player = new Player("test");
	// Set<Wys> vierGleiche = new HashSet<Wys>();
	// vierGleiche.add(new Wys(Arrays.asList(HEARTS_EIGHT, HEARTS_NINE,
	// HEARTS_TEN, HEARTS_JACK, HEARTS_QUEEN), BLATT));
	// linkedHashSet.put(player, vierGleiche);
	//
	// Player player2 = new Player("test2");
	// Set<Wys> wys5blatt = new HashSet<Wys>();
	// wys5blatt.add(new Wys(Arrays.asList(CLUBS_KING, HEARTS_KING,
	// DIAMONDS_KING, SPADES_KING), VIERLING));
	// linkedHashSet.put(player2, wys5blatt);
	//
	// when(wysStoreMock.getWys()).thenReturn(linkedHashSet);
	// Player wysWinner = wysRule.getBestWys(wysStoreMock);
	// assertSame(player, wysWinner);
	// }

}
