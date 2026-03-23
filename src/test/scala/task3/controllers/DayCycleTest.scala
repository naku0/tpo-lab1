package task3.controllers

import java.time.LocalTime
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import task3.enums.WeatherType
import task3.services.{Atmosphere, Clock}

class DayCycleTest {

	private class SpyClock(startHour: Int, deltaHours: Int = 1) extends Clock(0, LocalTime.of(startHour, 0, 0)) {
		var addRandomTimeCalled: Boolean = false
		override def addRandomTime(): Unit = {
			addRandomTimeCalled = true
			addHour(deltaHours)
		}
	}

	@Test
	def runReturnsFalseAtEndOfDayAndSkipsUpdates(): Unit = {
		val clock = SpyClock(23)
		val atmosphere = Atmosphere(0.5f, 25.0f, WeatherType.SUNNY)
		val dayCycle = DayCycle(clock = clock, atmosphere = atmosphere)

		assertFalse(dayCycle.run())
		assertEquals((23, 0, 0), dayCycle.clock.time())
		assertFalse(clock.addRandomTimeCalled)
		assertEquals(WeatherType.SUNNY, atmosphere.weather)
	}

	@Test
	def runAppliesNightAndSnow(): Unit = {
		val clock = SpyClock(0)
		val atmosphere = Atmosphere(0.5f, 25.0f, WeatherType.SUNNY)
		val dayCycle = DayCycle(clock = clock, atmosphere = atmosphere)

		assertTrue(dayCycle.run())
		assertEquals("NIGHT", dayCycle.dayStage())
		assertEquals(0.0f, atmosphere.rarefaction())
		assertEquals(WeatherType.SNOWY, atmosphere.weather)
		assertTrue(clock.addRandomTimeCalled)
	}

	@Test
	def runStartsMorningAndSunny(): Unit = {
		val clock = SpyClock(5)
		val atmosphere = Atmosphere(0.2f, 25.0f, WeatherType.RAINY)
		val dayCycle = DayCycle(clock = clock, atmosphere = atmosphere)

		assertTrue(dayCycle.run())
		assertEquals("MORNING", dayCycle.dayStage())
		assertEquals(1.0f, atmosphere.rarefaction())
		assertEquals(WeatherType.SUNNY, atmosphere.weather)
	}

	@Test
	def constructorWithNulls(): Unit = {
		assertThrows(classOf[IllegalArgumentException], () => DayCycle(null, Atmosphere()))
		assertThrows(classOf[IllegalArgumentException], () => DayCycle(Clock(), null))
	}

}
