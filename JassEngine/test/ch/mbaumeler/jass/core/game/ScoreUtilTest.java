package ch.mbaumeler.jass.core.game;

import static ch.mbaumeler.jass.core.card.CardSuit.CLUBS;
import static ch.mbaumeler.jass.core.card.CardSuit.DIAMONDS;
import static ch.mbaumeler.jass.core.card.CardSuit.HEARTS;
import static ch.mbaumeler.jass.core.card.CardSuit.SPADES;
import static ch.mbaumeler.jass.core.game.Ansage.SpielModi.OBENABE;
import static ch.mbaumeler.jass.core.game.Ansage.SpielModi.UNDEUFE;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_NINE;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_SEVEN;
import static ch.mbaumeler.jass.test.util.CardDomain.DIAMONDS_SIX;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_ACE;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_NINE;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_TEN;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_EIGHT;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.SPADES_SIX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.card.CardSuit;
import ch.mbaumeler.jass.core.card.CardValue;
import ch.mbaumeler.jass.core.game.wys.Wys;
import ch.mbaumeler.jass.core.game.wys.WysRules;
import ch.mbaumeler.jass.core.game.wys.WysScoreRule;
import ch.mbaumeler.jass.core.game.wys.WysStore;

public class ScoreUtilTest {

	private ScoreUtil scoreUtil;

	private Ansage ansage;
	private Match matchMock;
	private WysStore wysStoreMock;
	private PlayerTokenRepository playerTokenRepository;

	@Before
	public void setUp() throws Exception {
		matchMock = mock(Match.class);
		wysStoreMock = mock(WysStore.class);
		scoreUtil = new ScoreUtil();
		playerTokenRepository = new PlayerTokenRepository();
		scoreUtil.playerTokenRepository = playerTokenRepository;
		scoreUtil.scoreRules = mock(ScoreRules.class);
		scoreUtil.wysRules = mock(WysRules.class);
		scoreUtil.wysScoreRule = mock(WysScoreRule.class);
		ansage = new Ansage(CLUBS);
	}

	@Test
	public void testNewMatch() {
		Score score = scoreUtil.calculateScore(matchMock, wysStoreMock);
		assertNotNull(score);
		PlayerToken player = playerTokenRepository.getTeam1().get(0);
		assertEquals(0, score.getPlayerScore(player));
		assertEquals(0, score.getOppositeScore(player));
	}

	@Test
	public void testGetStoeck() {
		PlayerToken player = playerTokenRepository.getTeam1().get(0);

		when(wysStoreMock.getStoeckFromPlayer()).thenReturn(player);
		when(scoreUtil.wysScoreRule.getScoreForStoeck()).thenReturn(20);

		Score score = scoreUtil.calculateScore(matchMock, wysStoreMock);

		assertNotNull(score);
		assertEquals(20, score.getPlayerScore(player));
		assertEquals(0, score.getOppositeScore(player));
	}

	@Test
	public void testPlayerWys() {
		PlayerToken player = playerTokenRepository.getTeam1().get(0);

		when(wysStoreMock.getBestWys(any(Ansage.class))).thenReturn(player);
		HashSet<Wys> set = new HashSet<Wys>();
		Wys wysMock = mock(Wys.class);
		set.add(wysMock);
		when(wysStoreMock.getWys(player)).thenReturn(set);
		when(scoreUtil.wysScoreRule.getScoreFore(wysMock)).thenReturn(30);

		Score score = scoreUtil.calculateScore(matchMock, wysStoreMock);

		assertNotNull(score);
		assertEquals(30, score.getPlayerScore(player));
		assertEquals(0, score.getOppositeScore(player));
	}

	@Test
	public void testTeamMemberWys() {
		PlayerToken player = playerTokenRepository.getTeam1().get(0);
		PlayerToken teamPlayer = playerTokenRepository.getTeam1().get(1);

		when(wysStoreMock.getBestWys(any(Ansage.class))).thenReturn(player);
		HashSet<Wys> set = new HashSet<Wys>();
		Wys wysMock = mock(Wys.class);
		set.add(wysMock);
		when(wysStoreMock.getWys(player)).thenReturn(set);
		when(scoreUtil.wysScoreRule.getScoreFore(wysMock)).thenReturn(30);

		set = new HashSet<Wys>();
		Wys wysTeamMemberMock = mock(Wys.class);
		set.add(wysTeamMemberMock);
		when(wysStoreMock.getWys(teamPlayer)).thenReturn(set);
		when(scoreUtil.wysScoreRule.getScoreFore(wysTeamMemberMock)).thenReturn(50);

		Score score = scoreUtil.calculateScore(matchMock, wysStoreMock);

		assertNotNull(score);
		assertEquals(80, score.getPlayerScore(player));
		assertEquals(0, score.getOppositeScore(player));
	}

	@Test
	public void testScoreForAnsage() {
		Ansage ansage = new Ansage(HEARTS);
		PlayerToken player = playerTokenRepository.getTeam1().get(0);
		List<Card> cardsFromRound = new ArrayList<Card>();
		Card winnerCard = CLUBS_JACK;
		cardsFromRound.add(winnerCard);

		when(matchMock.getRoundsCompleted()).thenReturn(1);
		when(matchMock.getCardsFromRound(0)).thenReturn(cardsFromRound);
		when(matchMock.getAnsage()).thenReturn(ansage);
		when(scoreUtil.scoreRules.getScore(winnerCard, ansage)).thenReturn(70);

		Score score = scoreUtil.calculateScore(matchMock, wysStoreMock);

		assertNotNull(score);
		assertEquals(70, score.getPlayerScore(player));
		assertEquals(0, score.getOppositeScore(player));
	}

	@Test
	public void testScoreForLastRound() {
		Ansage ansage = new Ansage(HEARTS);
		PlayerToken player = playerTokenRepository.getTeam1().get(0);
		List<Card> cardsFromRound = new ArrayList<Card>();
		Card winnerCard = CLUBS_JACK;
		cardsFromRound.add(winnerCard);

		when(matchMock.getRoundsCompleted()).thenReturn(1);
		when(matchMock.getCardsFromRound(0)).thenReturn(cardsFromRound);
		when(matchMock.getAnsage()).thenReturn(ansage);
		when(scoreUtil.scoreRules.getScore(winnerCard, ansage)).thenReturn(70);
		when(matchMock.isComplete()).thenReturn(true);

		Score score = scoreUtil.calculateScore(matchMock, wysStoreMock);

		assertNotNull(score);
		assertEquals(75, score.getPlayerScore(player));
		assertEquals(0, score.getOppositeScore(player));
	}

	@Test
	public void testScoreForAMatch() {
		Ansage ansage = new Ansage(DIAMONDS);

		when(matchMock.getRoundsCompleted()).thenReturn(9);
		List<Card> cards = createSortedDeck();

		for (int i = 0; i < 9; i++) {
			List<Card> cardsFromRound = new ArrayList<Card>();
			cardsFromRound.add(cards.get(0 + i));
			cardsFromRound.add(cards.get(9 + i));
			cardsFromRound.add(cards.get(18 + i));
			cardsFromRound.add(cards.get(27 + i));
			when(matchMock.getCardsFromRound(i)).thenReturn(cardsFromRound);
		}

		when(matchMock.getAnsage()).thenReturn(ansage);
		scoreUtil.scoreRules = new ScoreRules();
		when(matchMock.isComplete()).thenReturn(true);

		Score score = scoreUtil.calculateScore(matchMock, wysStoreMock);
		PlayerToken player = playerTokenRepository.getAll().get(0);
		assertEquals(257, score.getPlayerScore(player));
		assertEquals(0, score.getOppositeScore(player));

	}

	@Test
	public void testScoreForAMatchWithTeam2() {
		Ansage ansage = new Ansage(SPADES);

		when(matchMock.getRoundsCompleted()).thenReturn(9);
		List<Card> cards = createSortedDeck();

		for (int i = 0; i < 9; i++) {
			List<Card> cardsFromRound = new ArrayList<Card>();
			cardsFromRound.add(cards.get(0 + i));
			cardsFromRound.add(cards.get(9 + i));
			cardsFromRound.add(cards.get(18 + i));
			cardsFromRound.add(cards.get(27 + i));
			when(matchMock.getCardsFromRound(i)).thenReturn(cardsFromRound);
		}

		when(matchMock.getAnsage()).thenReturn(ansage);
		scoreUtil.scoreRules = new ScoreRules();
		when(matchMock.isComplete()).thenReturn(true);

		Score score = scoreUtil.calculateScore(matchMock, wysStoreMock);
		PlayerToken player = playerTokenRepository.getAll().get(1);
		assertEquals(257, score.getPlayerScore(player));
		assertEquals(0, score.getOppositeScore(player));

	}

	private List<Card> createSortedDeck() {
		List<Card> result = new ArrayList<Card>(36);
		result.addAll(sortedDeck(DIAMONDS, PlayerToken.PLAYER0));
		result.addAll(sortedDeck(SPADES, PlayerToken.PLAYER1));
		result.addAll(sortedDeck(HEARTS, PlayerToken.PLAYER2));
		result.addAll(sortedDeck(CLUBS, PlayerToken.PLAYER3));
		return result;
	}

	private List<Card> sortedDeck(CardSuit cardSuit, PlayerToken playerToken) {
		List<Card> result = new ArrayList<Card>();

		for (CardValue value : CardValue.values()) {
			result.add(new Card(cardSuit, value, playerToken));
		}
		return result;
	}

	@Test
	public void testGetWinnerCardTrumpf() {
		Card card0 = CLUBS_KING;
		Card card1 = HEARTS_ACE;
		Card card2 = CLUBS_NINE;
		Card card3 = DIAMONDS_SIX;
		List<Card> cards = Arrays.asList(card0, card1, card2, card3);
		assertEquals(card3, scoreUtil.getWinnerCard(cards, new Ansage(CardSuit.DIAMONDS)));
	}

	@Test
	public void testGetWinnerCardNotTrumpf() {
		Card card0 = CLUBS_KING;
		Card card1 = CLUBS_ACE;
		Card card2 = CLUBS_NINE;
		Card card3 = CLUBS_SIX;
		List<Card> cards = Arrays.asList(card0, card1, card2, card3);
		assertEquals(card1, scoreUtil.getWinnerCard(cards, new Ansage(CardSuit.SPADES)));
	}

	@Test
	public void testGetWinnerOvertrumpfed() {
		Card card0 = DIAMONDS_SEVEN;
		Card card1 = CLUBS_ACE;
		Card card2 = CLUBS_NINE;
		Card card3 = DIAMONDS_EIGHT;
		List<Card> cards = Arrays.asList(card0, card1, card2, card3);
		assertEquals(card2, scoreUtil.getWinnerCard(cards, ansage));
	}

	@Test
	public void testGetWinnerWithTrumpf() {
		Card card0 = DIAMONDS_SEVEN;
		Card card1 = CLUBS_ACE;
		Card card2 = CLUBS_NINE;
		Card card3 = DIAMONDS_EIGHT;
		List<Card> cards = Arrays.asList(card0, card1, card2, card3);
		assertEquals(card3, scoreUtil.getWinnerCard(cards, new Ansage(HEARTS)));
	}

	@Test
	public void testGetWinnerWithTrumpf2() {
		Card card0 = SPADES_JACK;
		Card card1 = SPADES_EIGHT;
		Card card2 = SPADES_SIX;
		Card card3 = CLUBS_SIX;
		List<Card> cards = Arrays.asList(card0, card1, card2, card3);
		assertEquals(card3, scoreUtil.getWinnerCard(cards, ansage));
	}

	@Test
	public void testGetWinnerWithTrumpfAtSecondPosition() {
		Card card0 = SPADES_EIGHT;
		Card card1 = HEARTS_QUEEN;
		Card card2 = SPADES_SIX;
		Card card3 = CLUBS_JACK;
		List<Card> cards = Arrays.asList(card0, card1, card2, card3);
		assertEquals(card1, scoreUtil.getWinnerCard(cards, new Ansage(HEARTS)));
	}

	@Test
	public void testGetWinnerWithTrumpfJack() {
		Card card0 = HEARTS_NINE;
		Card card1 = HEARTS_JACK;
		Card card2 = SPADES_SIX;
		Card card3 = HEARTS_ACE;
		List<Card> cards = Arrays.asList(card0, card1, card2, card3);
		assertEquals(card1, scoreUtil.getWinnerCard(cards, new Ansage(HEARTS)));
	}

	@Test
	public void testGetWinnerHeartsButNotTrumpf() {
		Card card0 = HEARTS_QUEEN;
		Card card1 = CLUBS_EIGHT;
		Card card2 = HEARTS_ACE;
		Card card3 = HEARTS_TEN;
		List<Card> cards = Arrays.asList(card0, card1, card2, card3);
		assertEquals(card1, scoreUtil.getWinnerCard(cards, ansage));
	}

	@Test
	public void testGetWinnerUndeUfe() {
		Card card0 = HEARTS_QUEEN;
		Card card1 = CLUBS_EIGHT;
		Card card2 = HEARTS_ACE;
		Card card3 = HEARTS_TEN;
		List<Card> cards = Arrays.asList(card0, card1, card2, card3);
		assertEquals(card3, scoreUtil.getWinnerCard(cards, new Ansage(UNDEUFE)));
	}

	@Test
	public void testGetObenAbeUndeUfe() {
		Card card0 = HEARTS_QUEEN;
		Card card1 = CLUBS_EIGHT;
		Card card2 = HEARTS_ACE;
		Card card3 = HEARTS_TEN;
		List<Card> cards = Arrays.asList(card0, card1, card2, card3);
		assertEquals(card2, scoreUtil.getWinnerCard(cards, new Ansage(OBENABE)));
	}
}
