package ch.mbaumeler.jass.extended.ai.simple;

import java.util.ArrayList;
import java.util.List;

import ch.mbaumeler.jass.extended.ai.simple.trumpf.FirstPlayerTrumpfStrategy;
import ch.mbaumeler.jass.extended.ai.simple.trumpf.FourthPlayerTrumpfStrategy;
import ch.mbaumeler.jass.extended.ai.simple.trumpf.SecondPlayerTrumpfStrategy;
import ch.mbaumeler.jass.extended.ai.simple.trumpf.ThirdPlayerTrumpfStrategy;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

/* REVIEW NEEDED */ public class SimpleStrategyEngine extends AbstractModule {

	public SimpleStrategy create() {
		Injector injector = Guice.createInjector(this);
		return injector.getInstance(SimpleStrategy.class);
	}

	@Override
	protected void configure() {
		List<SimpleSelectCardStrategy> strategies = new ArrayList<SimpleSelectCardStrategy>();
		strategies.add(new FirstPlayerTrumpfStrategy());
		strategies.add(new SecondPlayerTrumpfStrategy());
		strategies.add(new ThirdPlayerTrumpfStrategy());
		strategies.add(new FourthPlayerTrumpfStrategy());
		bind(new TypeLiteral<List<SimpleSelectCardStrategy>>() {
		}).annotatedWith(Names.named("strategies")).toInstance(strategies);
	}

}
