package task3.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import task3.services.Atmosphere

class CelestialBodyTest {

  @Test
  def isVisibleWithLowDistance(): Unit = {
    val moon = CelestialBody("Moon", 2_000)
    assertTrue(moon.isVisible(Atmosphere(1.0f), 3))
  }

  @Test
  def isNotVisibleWithBigDistance(): Unit = {
    val moon = CelestialBody("Moon", 5_000)
    assertFalse(moon.isVisible(Atmosphere(1.0f), 4))
  }

}
