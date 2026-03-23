package task3.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import task3.enums.WeatherType
import task3.services.Atmosphere

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

  @Test
  def canBeObservedDependsOnVisibility(): Unit = {
    val moon = CelestialBody("Moon", new Position(0, 0))
    val star = CelestialBody("Star", new Position(0, 0))

    val clearAtmosphere = Atmosphere(0.5f, 20.0f, WeatherType.SUNNY)
    assertTrue(moon.canBeObserved(clearAtmosphere))
    assertTrue(star.canBeObserved(clearAtmosphere))

    val rainyAtmosphere = Atmosphere(0.5f, 20.0f, WeatherType.RAINY)
    assertTrue(moon.canBeObserved(rainyAtmosphere))
    assertFalse(star.canBeObserved(rainyAtmosphere))
  }

  @Test
  def canBeObservedRejectsNullAtmosphere(): Unit = {
    val moon = CelestialBody("Moon", new Position(0, 0))
    assertThrows(classOf[IllegalArgumentException], () => moon.canBeObserved(null))
  }

}
