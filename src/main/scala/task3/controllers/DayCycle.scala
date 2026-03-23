package task3.controllers

import task3.enums.WeatherType
import task3.services.{Atmosphere, Clock}

class DayCycle(val clock: Clock = Clock(), val atmosphere: Atmosphere = Atmosphere()) {
  require(clock != null)
  require(atmosphere != null)

  def dayStage(): String = {
    val hour = clock.time()._1
    if (hour < 6) "NIGHT"
    else if (hour < 12) "MORNING"
    else if (hour < 18) "DAY"
    else "EVENING"
  }

  def run(): Boolean = {
    if (isEndOfDay) {
      endOfDay()
      false
    } else {
      clock.addRandomTime()
      dayStage() match {
        case "NIGHT" =>
          atmosphere.darkness()
          atmosphere.setWeather(WeatherType.SNOWY)
        case "MORNING" =>
          atmosphere.light()
          atmosphere.setWeather(WeatherType.SUNNY)
        case "DAY" =>
          atmosphere.light()
          atmosphere.setWeather(WeatherType.SUNNY)
        case _ =>
          atmosphere.setWeather(WeatherType.RAINY)
      }
      true
    }
  }

  private def isEndOfDay: Boolean = {
    clock.time()._1 >= 23
  }

  private def endOfDay(): Unit = {
    println("Конец дня.")
    println("Программа умирает.")
  }
}
