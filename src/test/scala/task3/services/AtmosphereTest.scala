package task3.services

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AtmosphereTest {

	@Test
	def colorRedWhenRarefactionIsHigh(): Unit = {
		val atmosphere = Atmosphere(_rarefaction = 0.81f, temp = 20.0f)
		assertEquals(Atmosphere.RED, atmosphere.color())
	}

	@Test
	def colorGreenWhenRarefactionIsLow(): Unit = {
		val atmosphere = Atmosphere(_rarefaction = 0.1f, temp = 25.0f)
		assertEquals(Atmosphere.GREEN, atmosphere.color())
	}

	@Test
	def colorRedWhenTemperatureIsHigh(): Unit = {
		val atmosphere = Atmosphere(_rarefaction = 0.1f, temp = 60.0f)
		assertEquals(Atmosphere.RED, atmosphere.color())
	}

	@Test
	def darknessAndLightSetExpectedRarefaction(): Unit = {
		val atmosphere = Atmosphere(0.5f)

		atmosphere.darkness()
		assertEquals(0.0f, atmosphere.rarefaction())
		assertEquals(Atmosphere.GREEN, atmosphere.color())

		atmosphere.light()
		assertEquals(1.0f, atmosphere.rarefaction())
		assertEquals(Atmosphere.RED, atmosphere.color())
	}

	@Test
	def changeKeepsValuesInExpectedRanges(): Unit = {
		val atmosphere = Atmosphere(0.5f)

		atmosphere.change()

		assertTrue(atmosphere.rarefaction() >= 0.0f)
		assertTrue(atmosphere.rarefaction() < 1.0f)
		assertTrue(atmosphere.temp >= 0.0f)
		assertTrue(atmosphere.temp < 100.0f)
	}

}
