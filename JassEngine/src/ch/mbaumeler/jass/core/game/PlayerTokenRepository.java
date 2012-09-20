package ch.mbaumeler.jass.core.game;

import static ch.mbaumeler.jass.core.game.PlayerToken.PLAYER0;
import static ch.mbaumeler.jass.core.game.PlayerToken.PLAYER1;
import static ch.mbaumeler.jass.core.game.PlayerToken.PLAYER2;
import static ch.mbaumeler.jass.core.game.PlayerToken.PLAYER3;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayerTokenRepository {

	private List<PlayerToken> team1;
	private List<PlayerToken> team2;
	private List<PlayerToken> all;

	public PlayerTokenRepository() {
		team1 = createPlayerList(PLAYER0, PLAYER2);
		team2 = createPlayerList(PLAYER1, PLAYER3);
		all = createPlayerList(PlayerToken.values());
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
