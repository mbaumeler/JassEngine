package ch.mbaumeler.jass.core.game;

/* REVIEW NEEDED */public class Score {

	private int scoreTeam1;
	private int scoreTeam2;

	public Score() {
	}

	public void addScore(PlayerToken scorer, int score) {
		if (PlayerToken.isTeam1(scorer)) {
			scoreTeam1 += score;
		} else {
			scoreTeam2 += score;
		}
	}

	public boolean isWinLimitSucceeded(int winLimit) {
		return scoreTeam1 >= winLimit || scoreTeam2 >= winLimit;
	}

	public int getScoreTeam1() {
		return scoreTeam1;
	}

	public int getScoreTeam2() {
		return scoreTeam2;
	}

	@Deprecated
	public int getPlayerScore(PlayerToken player) {
		return PlayerToken.isTeam1(player) ? scoreTeam1 : scoreTeam2;
	}

	@Deprecated
	public int getOppositeScore(PlayerToken player) {
		return PlayerToken.isTeam1(player) ? scoreTeam2 : scoreTeam1;
	}

	public void add(Score score) {
		scoreTeam1 += score.scoreTeam1;
		scoreTeam2 += score.scoreTeam2;
	}
}
