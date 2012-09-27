package ch.mbaumeler.jass.core.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

/* REVIEW NEEDED */public class PlayerTokenRepositoryTest {

	@Test
	public void testGetTeam1() {
		List<PlayerToken> team1 = PlayerToken.getTeam1();
		assertNotNull(team1);
		assertEquals(2, team1.size());
	}

	@Test
	public void testGetTeam2() {
		List<PlayerToken> team2 = PlayerToken.getTeam2();
		assertNotNull(team2);
		assertEquals(2, team2.size());
	}

	@Test
	public void testAll() {
		List<PlayerToken> all = PlayerToken.getAll();
		List<PlayerToken> team1 = PlayerToken.getTeam1();
		List<PlayerToken> team2 = PlayerToken.getTeam2();
		assertEquals(4, all.size());
		assertTrue(all.containsAll(team1));
		assertTrue(all.containsAll(team2));
	}

	@Test
	public void testIsTeam1() {
		List<PlayerToken> team1 = PlayerToken.getTeam1();
		List<PlayerToken> team2 = PlayerToken.getTeam2();
		assertTrue(PlayerToken.isTeam1(team1.get(0)));
		assertTrue(PlayerToken.isTeam1(team1.get(1)));
		assertFalse(PlayerToken.isTeam1(team2.get(0)));
		assertFalse(PlayerToken.isTeam1(team2.get(1)));
	}

	@Test
	public void testIsTeam2() {
		List<PlayerToken> team1 = PlayerToken.getTeam1();
		List<PlayerToken> team2 = PlayerToken.getTeam2();
		assertTrue(PlayerToken.isTeam2(team2.get(0)));
		assertTrue(PlayerToken.isTeam2(team2.get(1)));
		assertFalse(PlayerToken.isTeam2(team1.get(0)));
		assertFalse(PlayerToken.isTeam2(team1.get(1)));
	}

	@Test
	public void testGetTeamPlayer() {
		List<PlayerToken> team1 = PlayerToken.getTeam1();
		List<PlayerToken> team2 = PlayerToken.getTeam2();
		assertEquals(team1.get(1), PlayerToken.getTeamPlayer(team1.get(0)));
		assertEquals(team1.get(0), PlayerToken.getTeamPlayer(team1.get(1)));
		assertEquals(team2.get(1), PlayerToken.getTeamPlayer(team2.get(0)));
		assertEquals(team2.get(0), PlayerToken.getTeamPlayer(team2.get(1)));
	}

	@Test
	public void testUnmodifiableAll() {
		try {
			PlayerToken.getAll().set(0, PlayerToken.PLAYER0);
			fail();
		} catch (UnsupportedOperationException e) {
			// exptected
		}
	}

	@Test
	public void testUnmodifiableTeam1() {
		try {
			PlayerToken.getTeam1().set(0, PlayerToken.PLAYER0);
			fail();
		} catch (UnsupportedOperationException e) {
			// exptected
		}
	}

	@Test
	public void testUnmodifiableTeam2() {
		try {
			PlayerToken.getTeam2().set(0, PlayerToken.PLAYER0);
			fail();
		} catch (UnsupportedOperationException e) {
			// exptected
		}
	}

}
