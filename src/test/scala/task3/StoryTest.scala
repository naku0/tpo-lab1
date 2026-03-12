package task3

import org.junit.jupiter.api.{BeforeEach, Test}
import org.junit.jupiter.api.Assertions.*
import task3.controllers.DayCycle
import task3.models.{CelestialBody, Human}
import task3.services.Atmosphere

class StoryTest {

  var arthur: Human = _
  var oldman: Human = _
  var moon: CelestialBody = _
  var atmosphere: Atmosphere = _

  @BeforeEach
  def setup(): Unit = {
    arthur = Human("Arthur", 10)
    oldman = Human("old", 5)

    moon = CelestialBody("Moon", 5_000)

    atmosphere = Atmosphere()
  }

  @Test
  def storyTest(): Unit = {

    val day = DayCycle()
    while (day.run()) {
      if (moon.isVisible(day.atmosphere, arthur.vision)) {
        println(s"${arthur.name} видит ${moon.name}.")
      } else if (!arthur.isVisible(day.atmosphere, oldman.vision)) {
        println(s"${arthur.name} сталкивается со стариком ${oldman.name}.")
        arthur.clash(oldman)
      }
    }
    assertTrue(day.clock.time()._1 >= 23, "День должен закончиться после цикла.")
  }

  @Test
  def atmosphereDarknessTest(): Unit = {
    atmosphere.darkness()
    assertFalse(moon.isVisible(atmosphere, arthur.vision), s"${arthur.name} не должен видеть ${moon.name} в темноте.")
    assertFalse(arthur.isVisible(atmosphere, oldman.vision), s"${oldman.name} не должен видеть ${arthur.name} в темноте.")

    assertEquals(atmosphere.color(), Atmosphere.GREEN, "Цвет атмосферы должен быть зеленым в темноте.")
  }

  @Test
  def atmosphereLightTest(): Unit = {
    atmosphere.light()
    assertTrue(moon.isVisible(atmosphere, arthur.vision), s"${arthur.name} должен видеть ${moon.name} при ярком свете.")
    assertTrue(arthur.isVisible(atmosphere, oldman.vision), s"${oldman.name} должен видеть ${arthur.name} при ярком свете.")

    assertEquals(atmosphere.color(), Atmosphere.RED, "Цвет атмосферы должен быть красным при ярком свете.")
  }
}
