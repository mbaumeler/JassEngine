package ch.mbaumeler.jass.core;

public class GameState {

	private final int team1;
	private final int team2;
	private final MatchState matchState;

	public GameState(int team1, int team2, MatchState matchState) {
		this.team1 = team1;
		this.team2 = team2;
		this.matchState = matchState;
	}

	public int getTeam1() {
		return team1;
	}

	public int getTeam2() {
		return team2;
	}

	public MatchState getMatchState() {
		return matchState;
	}

}
