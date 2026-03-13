package task3

import java.time.LocalTime
import org.junit.jupiter.api.{BeforeEach, Test}
import org.junit.jupiter.api.Assertions.*
import task3.controllers.DayCycle
import task3.enums.State
import task3.models.{Aggregator, CelestialBody, Human, Position}
import task3.services.{Atmosphere, Clock}
import scala.util.Random

class StoryTest {

  var arthur: Human = _
  var oldman: Human = _
  var moon: CelestialBody = _
  var atmosphere: Atmosphere = _

  @BeforeEach
  def setup(): Unit = {
    Aggregator.list = Nil

    arthur = new Human("Arthur", 10, position = new Position(0, 0), state = State.HEALTHY)
    oldman = new Human("Old", 5, position = new Position(300, 0), state = State.HEALTHY)

    moon = CelestialBody("Moon", new Position(1200, 0))

    atmosphere = Atmosphere(_rarefaction = 0.5f, temp = 25.0f)
  }

  @Test
  def dayCycleEventuallyStops(): Unit = {
    val day = DayCycle(
      clock = Clock(utc = 0, _time = LocalTime.of(0, 0, 0), random = Random(1)),
      atmosphere = Atmosphere(0.5f)
    )

    var steps = 0
    while (steps < 30 && day.run()) {
      steps += 1
    }

    assertTrue(steps > 0)
    assertTrue(day.clock.time()._1 >= 23, "День должен закончиться после цикла.")
  }

  @Test
  def visibilityAndCollisionBranchesCanBothHappen(): Unit = {
    assertTrue(arthur.canView(atmosphere, moon))

    val nearMoon = CelestialBody("NearMoon", new Position(500, 0))
    assertTrue(arthur.canView(atmosphere, nearMoon))

    val distantMoon = CelestialBody("DMoon", new Position(5000, 0))
    assertFalse(arthur.canView(atmosphere, distantMoon))

    arthur.clash(oldman)
    assertEquals(1, oldman.hit)
  }

  @Test
  def atmosphereCanAffectHumanState(): Unit = {
    arthur.setState(Atmosphere(_rarefaction = 0.1f, temp = 20.0f))
    assertEquals(State.BLIND, arthur.state)
  }
}
