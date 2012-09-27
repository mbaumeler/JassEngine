package ch.mbaumeler.jass.core.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PlayerToken {
	PLAYER0, PLAYER1, PLAYER2, PLAYER3;

	private static final List<PlayerToken> team1 = createPlayerList(PLAYER0, PLAYER2);
	private static final List<PlayerToken> team2 = createPlayerList(PLAYER1, PLAYER3);

	public static List<PlayerToken> getTeam1() {
		return team1;
	}

	public static List<PlayerToken> getTeam2() {
		return team2;
	}

	public static boolean isTeam1(PlayerToken player) {
		return team1.contains(player);
	}

	public static PlayerToken getTeamPlayer(PlayerToken player) {
		List<PlayerToken> team = isTeam1(player) ? team1 : team2;
		return player == team.get(0) ? team.get(1) : team.get(0);
	}

	public static boolean isTeam2(PlayerToken player) {
		return team2.contains(player);
	}

	private static List<PlayerToken> createPlayerList(PlayerToken... players) {
		return Collections.unmodifiableList(Arrays.asList(players));
	}

}
