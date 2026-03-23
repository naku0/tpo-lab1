package task3.services

import task3.enums.WeatherType

class Atmosphere(private var _rarefaction: Float = 0.5,
                 var temp: Float = 25.0,
                 var weather: WeatherType = WeatherType.SUNNY) {
  require(!_rarefaction.isNaN && _rarefaction >= 0.0f && _rarefaction <= 1.0f)
  require(!temp.isNaN && temp >= -100.0f && temp <= 100.0f)
  require(weather != null)

  def color(): (Int, Int, Int) = {
    if (_rarefaction > 0.8 || temp > 50.0) {
      Atmosphere.RED
    } else if (_rarefaction < 0.2) {
      Atmosphere.GREEN
    } else {
      Atmosphere.BLUE
    }
  }

  def setWeather(w: WeatherType): Unit = {
    require(w != null)
    weather = w
    println(s"Погода изменилась на $weather.")
  }

  def setTemperature(t: Float): Unit = {
    require(!t.isNaN && t >= -100.0f && t <= 100.0f)
    temp = t
    println(s"Температура изменилась на $temp.")
  }

  def setRarefaction(r: Float): Unit = {
    require(!r.isNaN && r >= 0.0f && r <= 1.0f)
    _rarefaction = r
    println(s"Разреженность атмосферы изменилась на $_rarefaction.")
  }
  
  def rarefaction(): Float = {
    _rarefaction
  }
  
  def darkness(): Unit = {
    _rarefaction = 0.0f
    println("Наступила ночь.")
  }
  
  def light(): Unit = {
    _rarefaction = 1.0f
    println("Наступил день.")
  }

  def visibilityFactor(): Float = {
    _rarefaction * (weather match {
      case WeatherType.SUNNY => 1.0f
      case WeatherType.RAINY => 0.4f
      case WeatherType.SNOWY => 0.7f
    })
  }
}

object Atmosphere {
  val RED: (Int, Int, Int) = (255, 0, 0)
  val GREEN: (Int, Int, Int) = (0, 255, 0)
  val BLUE: (Int, Int, Int) = (0, 0, 255)
}