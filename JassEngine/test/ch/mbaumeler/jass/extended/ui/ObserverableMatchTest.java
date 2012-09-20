package ch.mbaumeler.jass.extended.ui;

import static ch.mbaumeler.jass.core.card.CardSuit.CLUBS;
import static ch.mbaumeler.jass.core.game.wys.Wys.WysTyp.BLATT;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_JACK;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_KING;
import static ch.mbaumeler.jass.test.util.CardDomain.CLUBS_QUEEN;
import static ch.mbaumeler.jass.test.util.CardDomain.HEARTS_ACE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.Match;
import ch.mbaumeler.jass.core.game.Ansage;
import ch.mbaumeler.jass.core.game.PlayerToken;
import ch.mbaumeler.jass.core.game.wys.Wys;

public class ObserverableMatchTest {

	private ObserverableMatch observerableMatch;
	private Match delegateMock = mock(Match.class);
	private ObserverRepository observerRepository = mock(ObserverRepository.class);

	@Before
	public void setUp() throws Exception {
		observerableMatch = new ObserverableMatch(delegateMock, observerRepository);
	}

	@Test
	public void testGetCards() {
		observerableMatch.getCards(null);
		verify(delegateMock).getCards(null);
		verify(observerRepository, times(0)).notifyObservers();
	}

	@Test
	public void testGetCardsOnTable() {
		observerableMatch.getCardsOnTable();
		verify(delegateMock).getCardsOnTable();
		verify(observerRepository, times(0)).notifyObservers();
	}

	@Test
	public void testGetTrumpf() {
		observerableMatch.getAnsage();
		verify(delegateMock).getAnsage();
		verify(observerRepository, times(0)).notifyObservers();
	}

	@Test
	public void testSetTrumpf() {
		Ansage ansage = new Ansage(CLUBS);
		PlayerToken playerToken = PlayerToken.PLAYER0;
		when(delegateMock.getActivePlayer()).thenReturn(playerToken);
		observerableMatch.setAnsage(ansage);
		verify(delegateMock).setAnsage(ansage);
		verify(observerRepository).notifyObservers();
	}

	@Test
	public void testGetActivePlayer() {
		observerableMatch.getActivePlayer();
		verify(delegateMock).getActivePlayer();
		verify(observerRepository, times(0)).notifyObservers();
	}

	@Test
	public void testIsCardPlayable() {
		observerableMatch.isCardPlayable(null);
		verify(delegateMock).isCardPlayable(null);
		verify(observerRepository, times(0)).notifyObservers();
	}

	@Test
	public void testGetCardFromRound() {
		observerableMatch.getCardsFromRound(4);
		verify(delegateMock).getCardsFromRound(4);
		verify(observerRepository, times(0)).notifyObservers();
	}

	@Test
	public void testGetPlayCard() {
		PlayerToken playerToken = PlayerToken.PLAYER0;
		when(delegateMock.getActivePlayer()).thenReturn(playerToken);
		observerableMatch.playCard(HEARTS_ACE);
		verify(delegateMock).playCard(HEARTS_ACE);
		verify(observerRepository).notifyObservers();
	}

	@Test
	public void testIsComplete() {
		observerableMatch.isComplete();
		verify(delegateMock).isComplete();
		verify(observerRepository, times(0)).notifyObservers();
	}

	@Test
	public void testGetScore() {
		observerableMatch.getScore();
		verify(delegateMock).getScore();
		verify(observerRepository, times(0)).notifyObservers();
	}

	@Test
	public void testGetRoundsCompleted() {
		observerableMatch.getRoundsCompleted();
		verify(delegateMock).getRoundsCompleted();
		verify(observerRepository, times(0)).notifyObservers();
	}

	@Test
	public void testWys() {

		PlayerToken playerToken = PlayerToken.PLAYER0;
		when(delegateMock.getActivePlayer()).thenReturn(playerToken);
		Set<Wys> wysSet = new HashSet<Wys>();
		wysSet.add(new Wys(Arrays.asList(CLUBS_KING, CLUBS_QUEEN, CLUBS_JACK), BLATT));

		observerableMatch.wys(wysSet);
		verify(delegateMock).wys(wysSet);
		verify(observerRepository).notifyObservers();
	}

}
