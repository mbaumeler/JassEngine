package ch.mbaumeler.jass.core;

import ch.mbaumeler.jass.core.bootstrap.JassModule;
import ch.mbaumeler.jass.core.game.impl.GameImpl;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class JassEngine {

	public Game createJassGame() {
		Injector injector = Guice.createInjector(new JassModule());
		return injector.getInstance(GameImpl.class);
	}
}
