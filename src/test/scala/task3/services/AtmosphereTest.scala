package task3.services

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AtmosphereTest {

	@Test
	def darknessSetsNightState(): Unit = {
		val atmosphere = Atmosphere(0.5f)

		atmosphere.darkness()

		assertEquals(0.0f, atmosphere.rarefaction())
		assertEquals(Atmosphere.GREEN, atmosphere.color())
	}

	@Test
	def lightSetsDayState(): Unit = {
		val atmosphere = Atmosphere(0.5f)

		atmosphere.light()

		assertEquals(1.0f, atmosphere.rarefaction())
		assertEquals(Atmosphere.RED, atmosphere.color())
	}

	@Test
	def changeKeepsRarefactionInExpectedRange(): Unit = {
		val atmosphere = Atmosphere(0.5f)

		atmosphere.change()

		assertTrue(atmosphere.rarefaction() >= 0.0f)
		assertTrue(atmosphere.rarefaction() < 1.0f)
	}

}
