package task3.controllers

import java.time.LocalTime
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import task3.services.{Atmosphere, Clock}

class DayCycleTest {

	private class SpyClock(startHour: Int) extends Clock(0, LocalTime.of(startHour, 0, 0)) {
		var addRandomTimeCalled: Boolean = false
		override def addRandomTime(): Unit = {
			addRandomTimeCalled = true
			addHour(1)
		}
	}

	private class SpyAtmosphere extends Atmosphere(0.5f) {
		var changeCalled: Boolean = false
		override def change(): Unit = {
			changeCalled = true
			temp = 42.0f
		}
	}

	@Test
	def runReturnsFalseAtEndOfDayAndSkipsUpdates(): Unit = {
		val clock = SpyClock(23)
		val atmosphere = SpyAtmosphere()
		val dayCycle = DayCycle(clock = clock, atmosphere = atmosphere)

		assertFalse(dayCycle.run())
		assertEquals((23, 0, 0), dayCycle.clock.time())
		assertFalse(clock.addRandomTimeCalled)
		assertFalse(atmosphere.changeCalled)
	}

	@Test
	def runAdvancesWorldBeforeEndOfDay(): Unit = {
		val clock = SpyClock(22)
		val atmosphere = SpyAtmosphere()
		val dayCycle = DayCycle(clock = clock, atmosphere = atmosphere)

		assertTrue(dayCycle.run())
		assertEquals((23, 0, 0), dayCycle.clock.time())
		assertTrue(clock.addRandomTimeCalled)
		assertTrue(atmosphere.changeCalled)
	}

}
