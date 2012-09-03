package ch.mbaumeler.jass.extended.ui;

import static ch.mbaumeler.jass.core.card.CardSuit.CLUBS;
import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.BLATT;
import static ch.mbaumeler.jass.extended.ui.ObserverableMatch.Event.ANSAGE;
import static ch.mbaumeler.jass.extended.ui.ObserverableMatch.Event.PLAYED_CARD;
import static ch.mbaumeler.jass.extended.ui.ObserverableMatch.Event.WYS;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_ACE;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
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
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.wys.Wys;
import ch.mbaumeler.jass.extended.ui.ObserverableMatch.Event;

public class ObserverableMatchTest {

	private ObserverableMatch observerableMatch;
	private Match delegateMock = mock(Match.class);
	private JassModelObserver observerMock = mock(JassModelObserver.class);

	@Before
	public void setUp() throws Exception {
		List<JassModelObserver> observers = new ArrayList<JassModelObserver>();
		observers.add(observerMock);
		observerableMatch = new ObserverableMatch(delegateMock, observers);
	}

	@Test
	public void testGetCards() {
		observerableMatch.getCards(null);
		verify(delegateMock).getCards(null);
		verify(observerMock, times(0)).updated(any(Event.class), any(PlayerToken.class), anyObject());
	}

	@Test
	public void testGetCardsOnTable() {
		observerableMatch.getCardsOnTable();
		verify(delegateMock).getCardsOnTable();
		verify(observerMock, times(0)).updated(any(Event.class), any(PlayerToken.class), anyObject());
	}

	@Test
	public void testGetTrumpf() {
		observerableMatch.getAnsage();
		verify(delegateMock).getAnsage();
		verify(observerMock, times(0)).updated(any(Event.class), any(PlayerToken.class), anyObject());
	}

	@Test
	public void testSetTrumpf() {
		Ansage ansage = new Ansage(CLUBS);
		PlayerToken playerToken = new PlayerToken("mab");
		when(delegateMock.getActivePlayer()).thenReturn(playerToken);
		observerableMatch.setAnsage(ansage);
		verify(delegateMock).setAnsage(ansage);
		verify(observerMock).updated(ANSAGE, playerToken, ansage);
	}

	@Test
	public void testGetActivePlayer() {
		observerableMatch.getActivePlayer();
		verify(delegateMock).getActivePlayer();
		verify(observerMock, times(0)).updated(any(Event.class), any(PlayerToken.class), anyObject());
	}

	@Test
	public void testIsCardPlayable() {
		observerableMatch.isCardPlayable(null);
		verify(delegateMock).isCardPlayable(null);
		verify(observerMock, times(0)).updated(any(Event.class), any(PlayerToken.class), anyObject());
	}

	@Test
	public void testGetCardFromRound() {
		observerableMatch.getCardsFromRound(4);
		verify(delegateMock).getCardsFromRound(4);
		verify(observerMock, times(0)).updated(any(Event.class), any(PlayerToken.class), anyObject());
	}

	@Test
	public void testGetPlayCard() {
		PlayerToken playerToken = new PlayerToken("mab");
		when(delegateMock.getActivePlayer()).thenReturn(playerToken);
		observerableMatch.playCard(HEARTS_ACE);
		verify(delegateMock).playCard(HEARTS_ACE);
		verify(observerMock).updated(PLAYED_CARD, playerToken, HEARTS_ACE);
	}

	@Test
	public void testIsComplete() {
		observerableMatch.isComplete();
		verify(delegateMock).isComplete();
		verify(observerMock, times(0)).updated(any(Event.class), any(PlayerToken.class), anyObject());
	}

	@Test
	public void testGetScore() {
		observerableMatch.getScore();
		verify(delegateMock).getScore();
		verify(observerMock, times(0)).updated(any(Event.class), any(PlayerToken.class), anyObject());
	}

	@Test
	public void testGetRoundsCompleted() {
		observerableMatch.getRoundsCompleted();
		verify(delegateMock).getRoundsCompleted();
		verify(observerMock, times(0)).updated(any(Event.class), any(PlayerToken.class), anyObject());
	}

	@Test
	public void testWys() {

		PlayerToken playerToken = new PlayerToken("mab");
		when(delegateMock.getActivePlayer()).thenReturn(playerToken);
		Set<Wys> wysSet = new HashSet<Wys>();
		wysSet.add(new Wys(Arrays.asList(CLUBS_KING, CLUBS_QUEEN, CLUBS_JACK), BLATT));

		observerableMatch.wys(wysSet);
		verify(delegateMock).wys(wysSet);
		verify(observerMock, times(1)).updated(WYS, playerToken, wysSet);
	}

}
