package task3.controllers

import java.time.LocalTime
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import scala.util.Random
import task3.services.{Atmosphere, Clock}

class DayCycleTest {

	@Test
	def runReturnsFalseAtEndOfDay(): Unit = {
		val dayCycle = DayCycle(
			clock = Clock(utc = 0, _time = LocalTime.of(23, 0, 0), random = Random(1)),
			atmosphere = Atmosphere(0.5f)
		)

		assertFalse(dayCycle.run())
		assertEquals((23, 0, 0), dayCycle.clock.time())
	}

	@Test
	def runAdvancesClockBeforeEndOfDay(): Unit = {
		val dayCycle = DayCycle(
			clock = Clock(utc = 0, _time = LocalTime.of(0, 0, 0), random = Random(1)),
			atmosphere = Atmosphere(0.5f)
		)

		assertTrue(dayCycle.run())
		assertNotEquals((0, 0, 0), dayCycle.clock.time())
	}

}
