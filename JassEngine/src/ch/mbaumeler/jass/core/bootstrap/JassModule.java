package ch.mbaumeler.jass.core.bootstrap;

import ch.mbaumeler.jass.core.CardFactory;
import ch.mbaumeler.jass.core.game.PlayerTokenRepository;
import ch.mbaumeler.jass.core.game.impl.CardFactoryImpl;

import com.google.inject.AbstractModule;

/* REVIEW NEEDED */ public class JassModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PlayerTokenRepository.class).toInstance(new PlayerTokenRepository());
		bindCardFactoryOverride();
	}

	public void bindCardFactoryOverride() {
		bind(CardFactory.class).to(CardFactoryImpl.class);
	}
}
