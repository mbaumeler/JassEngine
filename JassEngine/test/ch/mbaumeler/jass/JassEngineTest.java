package ch.mbaumeler.jass;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import ch.mbaumeler.jass.core.JassEngine;

public class JassEngineTest {

	private JassEngine jassEngine;

	@Before
	public void setUp() throws Exception {
		jassEngine = new JassEngine();
	}

	@Test
	public void testGoogleJuiceBootUp() {
		assertNotNull(jassEngine.createJassGame());
	}

}
