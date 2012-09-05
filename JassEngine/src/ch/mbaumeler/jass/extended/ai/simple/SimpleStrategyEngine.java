package ch.mbaumeler.jass.extended.ai.simple;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

public class SimpleStrategyEngine extends AbstractModule {

	public SimpleStrategy create() {
		Injector injector = Guice.createInjector(this);
		return injector.getInstance(SimpleStrategy.class);
	}

	@Override
	protected void configure() {
		List<SimpleCardStrategy> strategies = new ArrayList<SimpleCardStrategy>();
		strategies.add(new FirstPlayerStrategy());
		strategies.add(new SecondPlayerStrategy());
		strategies.add(new ThirdPlayerStrategy());
		strategies.add(new FourthPlayerStrategy());
		bind(new TypeLiteral<List<SimpleCardStrategy>>() {
		}).annotatedWith(Names.named("strategies")).toInstance(strategies);
	}

}
