package ch.mbaumeler.jass.core.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayerTokenRepository {

	private List<PlayerToken> team1;
	private List<PlayerToken> team2;
	private List<PlayerToken> all;

	public PlayerTokenRepository() {
		PlayerToken player1 = new PlayerToken("Team1 - Player1");
		PlayerToken player2 = new PlayerToken("Team2 - Player1");
		PlayerToken player3 = new PlayerToken("Team1 - Player2");
		PlayerToken player4 = new PlayerToken("Team2 - Player2");

		team1 = createPlayerList(player1, player3);
		team2 = createPlayerList(player2, player4);
		all = createPlayerList(player1, player2, player3, player4);
	}

	public List<PlayerToken> getAll() {
		return all;
	}

	public List<PlayerToken> getTeam1() {
		return team1;
	}

	public List<PlayerToken> getTeam2() {
		return team2;
	}

	public boolean isTeam1(PlayerToken player) {
		return team1.contains(player);
	}

	public PlayerToken getTeamPlayer(PlayerToken player) {
		List<PlayerToken> team = isTeam1(player) ? team1 : team2;
		return teamPlayer(team, player);
	}

	private PlayerToken teamPlayer(List<PlayerToken> team, PlayerToken player) {
		return player == team.get(0) ? team.get(1) : team.get(0);
	}

	public boolean isTeam2(PlayerToken player) {
		return team2.contains(player);
	}

	private List<PlayerToken> createPlayerList(PlayerToken... players) {
		return Collections.unmodifiableList(Arrays.asList(players));
	}

}
