package task3.services

import scala.util.Random

class Atmosphere(private var _rarefaction: Float = 0.5,
                 var temp: Float = 25.0) {
  def color(): (Int, Int, Int) = {
    if (_rarefaction > 0.8 | temp > 50.0) {
      Atmosphere.RED
    } else if (_rarefaction < 0.2) {
      Atmosphere.GREEN
    } else {
      Atmosphere.BLUE
    }
  }
  
  def change(): Unit = {
    val old = _rarefaction
    _rarefaction = Random().nextFloat()
    temp = Random().nextFloat() * 100
    
    println(s"Атмосфера изменилась с $old на $_rarefaction; Температура составляет: $temp" )
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
}

object Atmosphere {
  val RED: (Int, Int, Int) = (255, 0, 0)
  val GREEN: (Int, Int, Int) = (0, 255, 0)
  val BLUE: (Int, Int, Int) = (0, 0, 255)
}