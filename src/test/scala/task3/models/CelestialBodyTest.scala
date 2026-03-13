package task3.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CelestialBodyTest {

  @Test
  def keepsGivenNameAndPosition(): Unit = {
    val position = new Position(1200, 350)
    val moon = CelestialBody("Moon", position)

    assertEquals("Moon", moon.name)
    assertSame(position, moon.position)
  }

  @Test
  def exposesSamePositionThroughViewableTrait(): Unit = {
    val position = new Position(10, 20)
    val moon = CelestialBody("Moon", position)

    assertSame(position, moon.pos)
  }

}
