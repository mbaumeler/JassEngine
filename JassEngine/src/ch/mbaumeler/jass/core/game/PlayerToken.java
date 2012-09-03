package ch.mbaumeler.jass.core.game;

public class PlayerToken {

	private final String name;

	public PlayerToken(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
