package ch.mbaumeler.jass.core.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PlayerTokenRepositoryTest {

	private PlayerTokenRepository playerTokenRepository;

	@Before
	public void setUp() throws Exception {
		playerTokenRepository = new PlayerTokenRepository();
	}

	@Test
	public void testGetTeam1() {
		List<PlayerToken> team1 = playerTokenRepository.getTeam1();
		assertNotNull(team1);
		assertEquals(2, team1.size());
	}

	@Test
	public void testGetTeam2() {
		List<PlayerToken> team2 = playerTokenRepository.getTeam2();
		assertNotNull(team2);
		assertEquals(2, team2.size());
	}

	@Test
	public void testAll() {
		List<PlayerToken> all = playerTokenRepository.getAll();
		List<PlayerToken> team1 = playerTokenRepository.getTeam1();
		List<PlayerToken> team2 = playerTokenRepository.getTeam2();
		assertEquals(4, all.size());
		assertTrue(all.containsAll(team1));
		assertTrue(all.containsAll(team2));
	}

	@Test
	public void testIsTeam1() {
		List<PlayerToken> team1 = playerTokenRepository.getTeam1();
		List<PlayerToken> team2 = playerTokenRepository.getTeam2();
		assertTrue(playerTokenRepository.isTeam1(team1.get(0)));
		assertTrue(playerTokenRepository.isTeam1(team1.get(1)));
		assertFalse(playerTokenRepository.isTeam1(team2.get(0)));
		assertFalse(playerTokenRepository.isTeam1(team2.get(1)));
	}

	@Test
	public void testIsTeam2() {
		List<PlayerToken> team1 = playerTokenRepository.getTeam1();
		List<PlayerToken> team2 = playerTokenRepository.getTeam2();
		assertTrue(playerTokenRepository.isTeam2(team2.get(0)));
		assertTrue(playerTokenRepository.isTeam2(team2.get(1)));
		assertFalse(playerTokenRepository.isTeam2(team1.get(0)));
		assertFalse(playerTokenRepository.isTeam2(team1.get(1)));
	}

	@Test
	public void testGetTeamPlayer() {
		List<PlayerToken> team1 = playerTokenRepository.getTeam1();
		List<PlayerToken> team2 = playerTokenRepository.getTeam2();
		assertEquals(team1.get(1), playerTokenRepository.getTeamPlayer(team1.get(0)));
		assertEquals(team1.get(0), playerTokenRepository.getTeamPlayer(team1.get(1)));
		assertEquals(team2.get(1), playerTokenRepository.getTeamPlayer(team2.get(0)));
		assertEquals(team2.get(0), playerTokenRepository.getTeamPlayer(team2.get(1)));
	}

	@Test
	public void testUnmodifiableAll() {
		try {
			playerTokenRepository.getAll().set(0, new PlayerToken("abc"));
		} catch (UnsupportedOperationException e) {
			// exptected
		}
	}

	@Test
	public void testUnmodifiableTeam1() {
		try {
			playerTokenRepository.getTeam1().set(0, new PlayerToken("abc"));
		} catch (UnsupportedOperationException e) {
			// exptected
		}
	}

	@Test
	public void testUnmodifiableTeam2() {
		try {
			playerTokenRepository.getTeam2().set(0, new PlayerToken("abc"));
		} catch (UnsupportedOperationException e) {
			// exptected
		}
	}

}
