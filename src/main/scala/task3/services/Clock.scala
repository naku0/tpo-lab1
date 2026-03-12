package task3.services

import java.time.LocalTime
import scala.util.Random

class Clock(val utc: Int, private var _time: LocalTime, private val random: Random = Random()) {

  def this(utc: Int) = {
    this(utc, LocalTime.of(0, 0, 0))
  }

  def this() = {
    this(0, LocalTime.of(0, 0, 0))
  }

  def time(): (Int, Int, Int) = {
    (_time.getHour, _time.getMinute, _time.getSecond)
  }

  def time(timezone: Int): (Int, Int, Int) = {
    val timeInTimezone = _time.plusHours(timezone - utc)
    (timeInTimezone.getHour, timeInTimezone.getMinute, timeInTimezone.getSecond)
  }

  def addSecond(n: Int): Unit = {
    _time = _time.plusSeconds(n)
  }
  def addMinute(n: Int): Unit = {
    _time = _time.plusMinutes(n)
  }
  def addHour(n: Int): Unit = {
    _time = _time.plusHours(n)
  }

  def addRandomTime(): Unit = {
    val s = random.between(1, 30)
    val m = random.between(1, 30)
    val h = random.between(1, 12)
    addSecond(s)
    addMinute(m)
    addHour(h)

    println(s"Прошло $h часа $m минуты $s секунды.")
  }
}
