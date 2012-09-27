package ch.mbaumeler.jass.extended.ui;

import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.Game;
import ch.mbaumeler.jass.core.Match;

/* REVIEW NEEDED */public class ObservableGameTest {

	private ObservableGame observerableGame;
	private Game gameMock = mock(Game.class);
	private JassModelObserver observerMock = mock(JassModelObserver.class);
	private Match matchMock = mock(Match.class);

	@Before
	public void setUp() throws Exception {
		observerableGame = new ObservableGame(gameMock);
		observerableGame.addObserver(observerMock);
		when(gameMock.getCurrentMatch()).thenReturn(matchMock);
	}

	@Test
	public void testGetTotalScore() {
		observerableGame.getTotalScore();
		verify(gameMock).getTotalScore();
		verify(observerMock, times(0)).jassModelChanged();
	}

	@Test
	public void testNotifyObservers() {
		observerableGame.notifyObservers();
		verify(observerMock).jassModelChanged();
	}

	@Test
	public void testCurrentMatch() {
		Match currentMatch = observerableGame.getCurrentMatch();
		assertNotSame(currentMatch, matchMock);
		verify(gameMock).getCurrentMatch();
		verify(observerMock, times(0)).jassModelChanged();
	}
}
