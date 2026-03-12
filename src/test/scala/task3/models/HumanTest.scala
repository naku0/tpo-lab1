package task3.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import task3.services.Atmosphere

class HumanTest {

	@Test
	def clashIncrementsHitCounter(): Unit = {
		val arthur = Human("Arthur", 10)
		val oldMan = Human("Old man", 4)

		arthur.clash(oldMan)

		assertEquals(1, oldMan.hit)
	}

	@Test
	def visibilityRespectsConditions(): Unit = {
		val human = Human("Arthur", 10)

		assertFalse(human.isVisible(Atmosphere(0.8f), 5), "5 * 0.8 == 4, но условие > 4")
		assertTrue(human.isVisible(Atmosphere(1.0f), 5), "5 * 1.0 > 4, цель должна быть видима")
	}
	
}
