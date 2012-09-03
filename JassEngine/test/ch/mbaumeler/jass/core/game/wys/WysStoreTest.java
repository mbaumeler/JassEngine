package ch.mbaumeler.jass.core.game.wys;

import static ch.mbaumeler.jass.core.card.CardSuit.CLUBS;
import static ch.mbaumeler.jass.core.card.CardSuit.DIAMONDS;
import static ch.mbaumeler.jass.core.card.CardSuit.HEARTS;
import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.BLATT;
import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.STOECK;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_NINE;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_TEN;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_NINE;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_TEN;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_ACE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.Card;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayedCard;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.wys.Wys.WysTyp;

public class WysStoreTest {

	private WysStore wysStore;
	private Match matchMock = mock(Match.class);
	private WysRules wysRuleMock = mock(WysRules.class);
	private WysScoreRule wysScoreRuleMock = mock(WysScoreRule.class);
	private PlayerToken playerTokenMock = mock(PlayerToken.class);

	@Before
	public void setUp() throws Exception {
		wysStore = new WysStore(wysRuleMock, wysScoreRuleMock, matchMock);
		when(matchMock.getActivePlayer()).thenReturn(playerTokenMock);
	}

	@Test
	public void testWysWherePlayerHasWys() {
		Wys wys = new Wys(Arrays.asList(CLUBS_QUEEN, CLUBS_KING, CLUBS_JACK), BLATT);
		Set<Wys> wysSet = new HashSet<Wys>();
		wysSet.add(wys);
		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		wysStore.addWys(wysSet);
		verify(wysRuleMock).findWyss(anyListOf(Card.class), any(Ansage.class));
		assertEquals(wysSet, wysStore.getWys(playerTokenMock));
	}

	@Test
	public void testWysWherePlayerDoesNotHaveWys() {
		Set<Wys> setToWys = new HashSet<Wys>();
		setToWys.add(new Wys(Arrays.asList(CLUBS_QUEEN, CLUBS_KING, CLUBS_JACK), BLATT));
		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(new HashSet<Wys>());

		try {
			wysStore.addWys(setToWys);

		} catch (IllegalArgumentException e) {
			// exptected
		}
	}

	@Test
	public void testBlattWysAfterFirstRound() {
		Wys wys = new Wys(Arrays.asList(CLUBS_QUEEN, CLUBS_KING, CLUBS_JACK), BLATT);
		Set<Wys> wysSet = new HashSet<Wys>();
		wysSet.add(wys);
		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		when(matchMock.getRoundsCompleted()).thenReturn(1);
		try {
			wysStore.addWys(wysSet);

		} catch (IllegalArgumentException e) {
			// exptected
		}
	}

	@Test
	public void testStoeckWysAfterFirstRound() {
		List<Card> cards = Arrays.asList(CLUBS_QUEEN, CLUBS_KING);
		Wys wys = new Wys(cards, STOECK);
		Set<Wys> wysSet = new HashSet<Wys>();
		wysSet.add(wys);

		when(matchMock.getCards(playerTokenMock)).thenReturn(new ArrayList<Card>(cards));
		when(matchMock.getRoundsCompleted()).thenReturn(0);
		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		when(matchMock.getRoundsCompleted()).thenReturn(1);

		wysStore.addWys(wysSet);
		assertEquals(playerTokenMock, wysStore.getStoeckFromPlayer());
	}

	@Test
	public void testStoeckOneCardAlreadyPlayed() {
		Set<Wys> wysSet = new HashSet<Wys>();
		wysSet.add(createWys(STOECK, CLUBS_QUEEN, CLUBS_KING));

		List<PlayedCard> playedCards = Arrays.asList(new PlayedCard(CLUBS_QUEEN, playerTokenMock));

		when(matchMock.getCards(playerTokenMock)).thenReturn(Arrays.asList(CLUBS_KING));
		when(matchMock.getRoundsCompleted()).thenReturn(1);
		when(matchMock.getCardsFromRound(0)).thenReturn(playedCards);
		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		when(matchMock.getRoundsCompleted()).thenReturn(1);

		wysStore.addWys(wysSet);
		assertEquals(playerTokenMock, wysStore.getStoeckFromPlayer());
	}

	@Test
	public void testStoeckOneCardAlreadyPlayedRevertedOrder() {
		Set<Wys> wysSet = new HashSet<Wys>();
		wysSet.add(createWys(STOECK, CLUBS_QUEEN, CLUBS_KING));

		List<PlayedCard> playedCards = Arrays.asList(new PlayedCard(CLUBS_KING, playerTokenMock));

		when(matchMock.getCards(playerTokenMock)).thenReturn(Arrays.asList(CLUBS_QUEEN));
		when(matchMock.getRoundsCompleted()).thenReturn(1);
		when(matchMock.getCardsFromRound(0)).thenReturn(playedCards);
		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		when(matchMock.getRoundsCompleted()).thenReturn(1);

		wysStore.addWys(wysSet);
		assertEquals(playerTokenMock, wysStore.getStoeckFromPlayer());
	}

	@Test
	public void testGetBestWysWithNoWys() {
		assertNull(wysStore.getBestWys(new Ansage(HEARTS)));
	}

	@Test
	public void testGetBestWysWithOneWys() {
		Set<Wys> wysSet = new HashSet<Wys>();
		Wys wys = createWys(BLATT, CLUBS_QUEEN, CLUBS_KING, CLUBS_JACK);
		wysSet.add(wys);

		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		when(wysScoreRuleMock.getScoreFore(wys)).thenReturn(123);
		wysStore.addWys(wysSet);

		assertEquals(playerTokenMock, wysStore.getBestWys(new Ansage(HEARTS)));

	}

	@Test
	public void test3BlattVs4Blatt() {
		Set<Wys> wysSet = new HashSet<Wys>();
		Wys wys = createWys(BLATT, CLUBS_QUEEN, CLUBS_KING, CLUBS_JACK);
		wysSet.add(wys);

		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		when(wysScoreRuleMock.getScoreFore(wys)).thenReturn(123);
		wysStore.addWys(wysSet);

		wysSet = new HashSet<Wys>();
		wys = createWys(BLATT, CLUBS_QUEEN, CLUBS_KING, CLUBS_JACK, CLUBS_TEN);
		wysSet.add(wys);

		PlayerToken playerToken = new PlayerToken("winner");
		when(matchMock.getActivePlayer()).thenReturn(playerToken);
		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		when(wysScoreRuleMock.getScoreFore(wys)).thenReturn(456);
		wysStore.addWys(wysSet);

		assertSame(playerToken, wysStore.getBestWys(new Ansage(CLUBS)));

	}

	@Test
	public void test5BlattIsHigherThan4Aces() {
		Set<Wys> wysSet = new HashSet<Wys>();
		Wys wys = createWys(WysTyp.VIER_GLEICHE, CLUBS_ACE, HEARTS_ACE, DIAMONDS_ACE, SPADES_ACE);
		wysSet.add(wys);

		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		when(wysScoreRuleMock.getScoreFore(wys)).thenReturn(100);
		wysStore.addWys(wysSet);

		wysSet = new HashSet<Wys>();
		wys = createWys(BLATT, CLUBS_QUEEN, CLUBS_KING, CLUBS_JACK, CLUBS_TEN, CLUBS_NINE);
		wysSet.add(wys);

		PlayerToken playerToken = new PlayerToken("winner");
		when(matchMock.getActivePlayer()).thenReturn(playerToken);
		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		when(wysScoreRuleMock.getScoreFore(wys)).thenReturn(100);
		wysStore.addWys(wysSet);

		assertSame(playerToken, wysStore.getBestWys(new Ansage(HEARTS)));

	}

	@Test
	public void testHigherBlatt() {
		Set<Wys> wysSet = new HashSet<Wys>();
		Wys wys = createWys(BLATT, CLUBS_QUEEN, CLUBS_KING, CLUBS_JACK, CLUBS_TEN, CLUBS_NINE);
		wysSet.add(wys);

		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		when(wysScoreRuleMock.getScoreFore(wys)).thenReturn(100);
		wysStore.addWys(wysSet);

		wysSet = new HashSet<Wys>();
		wys = createWys(BLATT, CLUBS_ACE, CLUBS_QUEEN, CLUBS_KING, CLUBS_JACK, CLUBS_TEN);
		wysSet.add(wys);

		PlayerToken playerToken = new PlayerToken("winner");
		when(matchMock.getActivePlayer()).thenReturn(playerToken);
		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		when(wysScoreRuleMock.getScoreFore(wys)).thenReturn(100);
		wysStore.addWys(wysSet);

		assertSame(playerToken, wysStore.getBestWys(new Ansage(CLUBS)));

	}

	@Test
	public void testSameBlattNotTrumpf() {
		Set<Wys> wysSet = new HashSet<Wys>();
		Wys wys = createWys(BLATT, CLUBS_QUEEN, CLUBS_KING, CLUBS_JACK, CLUBS_TEN, CLUBS_NINE);
		wysSet.add(wys);

		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		when(wysScoreRuleMock.getScoreFore(wys)).thenReturn(100);
		wysStore.addWys(wysSet);

		wysSet = new HashSet<Wys>();
		wys = createWys(BLATT, HEARTS_QUEEN, HEARTS_KING, HEARTS_JACK, HEARTS_TEN, HEARTS_NINE);
		wysSet.add(wys);

		PlayerToken playerToken = new PlayerToken("winner");
		when(matchMock.getActivePlayer()).thenReturn(playerToken);
		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		when(wysScoreRuleMock.getScoreFore(wys)).thenReturn(100);
		wysStore.addWys(wysSet);

		assertSame(playerTokenMock, wysStore.getBestWys(new Ansage(DIAMONDS)));

	}

	@Test
	public void testSameBlattWithTrumpf() {
		Set<Wys> wysSet = new HashSet<Wys>();
		Wys wys = createWys(BLATT, CLUBS_QUEEN, CLUBS_KING, CLUBS_JACK, CLUBS_TEN, CLUBS_NINE);
		wysSet.add(wys);

		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		when(wysScoreRuleMock.getScoreFore(wys)).thenReturn(100);
		wysStore.addWys(wysSet);

		wysSet = new HashSet<Wys>();
		wys = createWys(BLATT, HEARTS_QUEEN, HEARTS_KING, HEARTS_JACK, HEARTS_TEN, HEARTS_NINE);
		wysSet.add(wys);

		PlayerToken playerToken = new PlayerToken("winner");
		when(matchMock.getActivePlayer()).thenReturn(playerToken);
		when(wysRuleMock.findWyss(anyListOf(Card.class), any(Ansage.class))).thenReturn(wysSet);
		when(wysScoreRuleMock.getScoreFore(wys)).thenReturn(100);
		wysStore.addWys(wysSet);

		assertSame(playerToken, wysStore.getBestWys(new Ansage(HEARTS)));

	}

	private Wys createWys(WysTyp wysTyp, Card... cards) {
		return new Wys(Arrays.asList(cards), wysTyp);
	}
}
