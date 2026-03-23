package task3.services

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import task3.enums.WeatherType

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
	def darknessAndLightSetRarefaction(): Unit = {
		val atmosphere = Atmosphere(0.5f)

		atmosphere.darkness()
		assertEquals(0.0f, atmosphere.rarefaction())
		assertEquals(Atmosphere.GREEN, atmosphere.color())

		atmosphere.light()
		assertEquals(1.0f, atmosphere.rarefaction())
		assertEquals(Atmosphere.RED, atmosphere.color())
	}

	@Test
	def visibilityFactorDependsOnWeather(): Unit = {
		val atmosphere = Atmosphere(0.5f, 25.0f, WeatherType.SUNNY)

		assertEquals(0.5f, atmosphere.visibilityFactor())
		atmosphere.setWeather(WeatherType.RAINY)
		assertEquals(0.2f, atmosphere.visibilityFactor())
		atmosphere.setWeather(WeatherType.SNOWY)
		assertEquals(0.35f, atmosphere.visibilityFactor())
	}

	@Test
	def constructorWithInvalidData(): Unit = {
		assertThrows(classOf[IllegalArgumentException], () => Atmosphere(-0.1f, 20.0f, WeatherType.SUNNY))
		assertThrows(classOf[IllegalArgumentException], () => Atmosphere(0.3f, 101.0f, WeatherType.SUNNY))
		assertThrows(classOf[IllegalArgumentException], () => Atmosphere(0.3f, 20.0f, null))
	}

	@Test
	def setInvalidData(): Unit = {
		val atmosphere = Atmosphere(0.5f, 25.0f, WeatherType.SUNNY)

		assertThrows(classOf[IllegalArgumentException], () => atmosphere.setRarefaction(1.1f))
		assertThrows(classOf[IllegalArgumentException], () => atmosphere.setRarefaction(Float.NaN))
		assertThrows(classOf[IllegalArgumentException], () => atmosphere.setTemperature(-150.0f))
		assertThrows(classOf[IllegalArgumentException], () => atmosphere.setTemperature(Float.NaN))
		assertThrows(classOf[IllegalArgumentException], () => atmosphere.setWeather(null))
	}

}
