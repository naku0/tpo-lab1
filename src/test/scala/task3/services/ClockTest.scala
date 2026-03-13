package task3.services

import java.time.LocalTime
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import scala.util.Random

class ClockTest {

  @Test
  def timeInTimezoneUsesUtcOffsetAndWrapsAroundDay(): Unit = {
    val clock = Clock(utc = 3, _time = LocalTime.of(23, 20, 30))

    assertEquals((1, 20, 30), clock.time(5))
    assertEquals((20, 20, 30), clock.time(0))
  }

  @Test
  def addMethodsAccumulateTimeWithRollover(): Unit = {
    val clock = Clock(utc = 0, _time = LocalTime.of(23, 59, 59))

    clock.addSecond(2)
    clock.addMinute(1)
    clock.addHour(1)

    assertEquals((1, 1, 1), clock.time())
  }

  @Test
  def addRandomTimeAlwaysMovesClockForward(): Unit = {
    val clock = Clock(utc = 0, _time = LocalTime.of(0, 0, 0), random = Random(42))

    clock.addRandomTime()

    val (h, m, s) = clock.time()
    assertTrue(h >= 1 && h <= 11)
    assertTrue(m >= 1 && m <= 29)
    assertTrue(s >= 1 && s <= 29)
  }

}
