package task3.models

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import task3.enums.State
import task3.enums.WeatherType
import task3.services.Atmosphere

class HumanTest {

	@BeforeEach
	def resetAggregator(): Unit = {
		Aggregator.list = Nil
	}

	@Test
	def clashIncrementsTargetHitCounter(): Unit = {
		val arthur = new Human("Arthur", 10, position = new Position(0, 0), state = State.HEALTHY)
		val oldMan = new Human("Old man", 4, position = new Position(1, 0), state = State.HEALTHY)

		arthur.clash(oldMan)

		assertEquals(1, oldMan.hit)
	}

	@Test
	def visibilityStrictRules(): Unit = {
		val observer = new Human("Arthur", 5, position = new Position(0, 0), state = State.HEALTHY)
		val target = CelestialBody("Moon", new Position(1000, 0))

		assertFalse(observer.canView(Atmosphere(0.8f), target))
		assertTrue(observer.canView(Atmosphere(0.81f), target))
	}

	@Test
	def healthyMoveChangesPositionAndIncrementsMoves(): Unit = {
		val human = new Human("Arthur", 10, position = new Position(0, 0), state = State.HEALTHY)
		val newPos = new Position(5, 2)

		human.move(newPos, Atmosphere())

		assertEquals(newPos, human.position)
		assertEquals(1, human.moves)
	}

	@Test
	def tiredMove(): Unit = {
		val human = new Human("Arthur", 10, position = new Position(0, 0), state = State.TIRED)

		human.move(new Position(10, 10), Atmosphere())

		assertEquals(-5.0, human.position.x)
		assertEquals(-5.0, human.position.y)
		assertEquals(1, human.moves)
	}

	@Test
	def blindAndSickDoNotMove(): Unit = {
		val blind = new Human("Blind", 10, position = new Position(1, 1), state = State.BLIND)
		val sick = new Human("Sick", 10, position = new Position(2, 2), state = State.SICK)

		blind.move(new Position(10, 10), Atmosphere())
		sick.move(new Position(10, 10), Atmosphere())

		assertEquals(1.0, blind.position.x)
		assertEquals(1.0, blind.position.y)
		assertEquals(2.0, sick.position.x)
		assertEquals(2.0, sick.position.y)
		assertEquals(0, blind.moves)
		assertEquals(0, sick.moves)
	}

	@Test
	def stateBecomesTiredAfterFiveMoves(): Unit = {
		val human = new Human("Arthur", 10, position = new Position(0, 0), state = State.HEALTHY)

		(1 to 6).foreach(i => human.move(new Position(i, i), Atmosphere()))

		assertEquals(State.TIRED, human.state)
		assertEquals(6, human.moves)
	}

	@Test
	def setStateSwitchesToBlindForRedOrGreenAtmosphere(): Unit = {
		val human = new Human("Arthur", 10, position = new Position(0, 0), state = State.HEALTHY)

		human.setState(Atmosphere(_rarefaction = 0.1f, temp = 20.0f))
		assertEquals(State.BLIND, human.state)

		human.state = State.HEALTHY
		human.setState(Atmosphere(_rarefaction = 0.5f, temp = 20.0f))
		assertEquals(State.HEALTHY, human.state)
	}

	@Test
	def moveClashesAndShiftsWhenPositionOccupied(): Unit = {
		val mover = new Human("Arthur", 10, position = new Position(0, 0), state = State.HEALTHY)
		val blocker = new Human("Ford", 8, position = new Position(5, 2), state = State.HEALTHY)

		Aggregator.add(blocker)
		mover.move(new Position(5, 2), Atmosphere())

		assertEquals(1, blocker.hit)
		assertEquals(4.0, mover.position.x)
		assertEquals(2.0, mover.position.y)
	}

	@Test
	def rainyWeatherReduceVisibility(): Unit = {
		val observer = new Human("Arthur", 10, position = new Position(0, 0), state = State.HEALTHY)
		val target = CelestialBody("Moon", new Position(1000, 0))

		assertTrue(observer.canView(Atmosphere(0.5f, 20.0f, WeatherType.SUNNY), target))
		assertFalse(observer.canView(Atmosphere(0.5f, 20.0f, WeatherType.RAINY), target))
	}

	@Test
	def nullAtmosphereThrows(): Unit = {
		val observer = new Human("Arthur", 10, position = new Position(0, 0), state = State.HEALTHY)
		val target = CelestialBody("Moon", new Position(1000, 0))

		assertThrows(classOf[NullPointerException], () => observer.setState(null))
		assertThrows(classOf[NullPointerException], () => observer.canView(null, target))
	}

}
