package ch.mbaumeler.jass.core;

import ch.mbaumeler.jass.core.bootstrap.JassModule;
import ch.mbaumeler.jass.core.game.MatchFactory;
import ch.mbaumeler.jass.core.game.impl.GameImpl;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class JassEngine {

	private Injector injector = Guice.createInjector(new JassModule());

	public Game createJassGame() {
		return injector.getInstance(GameImpl.class);
	}

	public Match createMatchFromMatchState(MatchState matchState) {
		MatchFactory matchFactory = injector.getInstance(MatchFactory.class);
		return matchFactory.createMatch(matchState);
	}
}
