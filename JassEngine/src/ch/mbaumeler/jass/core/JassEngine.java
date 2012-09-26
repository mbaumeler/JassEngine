package ch.mbaumeler.jass.core;

import ch.mbaumeler.jass.core.bootstrap.JassModule;
import ch.mbaumeler.jass.core.game.MatchFactory;
import ch.mbaumeler.jass.core.game.impl.GameImpl;

import com.google.inject.Guice;
import com.google.inject.Injector;

/* REVIEW NEEDED */ public class JassEngine {

	private Injector injector;
	private MatchFactory matchFactory;

	public JassEngine() {
		injector = Guice.createInjector(new JassModule());
		matchFactory = injector.getInstance(MatchFactory.class);
	}

	public Game createJassGame() {
		return injector.getInstance(GameImpl.class);
	}

	public Match createMatchFromMatchState(MatchState matchState) {
		return matchFactory.createMatch(matchState);
	}
}
