package ch.mbaumeler.jass.core.game;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/* REVIEW NEEDED */public class ScoreTest {

	private PlayerToken team1Player1;
	private PlayerToken team2Player1;
	private PlayerToken team1Player2;
	private PlayerToken team2Player2;

	@Before
	public void setup() {
		team1Player1 = PlayerToken.PLAYER0;
		team2Player1 = PlayerToken.PLAYER1;
		team1Player2 = PlayerToken.PLAYER2;
		team2Player2 = PlayerToken.PLAYER3;
	}

	@Test
	public void testGetScore() {
		Score score = new Score();
		score.addScore(team1Player1, 20);
		score.addScore(team2Player1, 30);
		assertEquals(20, score.getPlayerScore(team1Player1));
		assertEquals(20, score.getPlayerScore(team1Player2));
		assertEquals(30, score.getPlayerScore(team2Player1));
		assertEquals(30, score.getPlayerScore(team2Player2));
	}

	@Test
	public void testGetOppositeScore() {
		Score score = new Score();
		score.addScore(team1Player1, 20);
		score.addScore(team2Player1, 30);
		assertEquals(30, score.getOppositeScore(team1Player1));
		assertEquals(30, score.getOppositeScore(team1Player2));
		assertEquals(20, score.getOppositeScore(team2Player1));
		assertEquals(20, score.getOppositeScore(team2Player2));
	}

	@Test
	public void testMerge() {
		Score score = new Score();
		score.addScore(team1Player1, 20);
		score.addScore(team2Player1, 30);

		Score scoreToAdd = new Score();
		scoreToAdd.addScore(team1Player1, 20);
		scoreToAdd.addScore(team2Player1, 30);
		score.add(scoreToAdd);
		assertEquals(40, score.getPlayerScore(team1Player1));
		assertEquals(40, score.getPlayerScore(team1Player2));
		assertEquals(60, score.getPlayerScore(team2Player1));
		assertEquals(60, score.getPlayerScore(team2Player2));
	}
}
