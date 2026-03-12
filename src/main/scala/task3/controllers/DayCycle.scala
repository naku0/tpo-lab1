package task3.controllers

import task3.services.{Atmosphere, Clock}

class DayCycle(val clock: Clock = Clock(), val atmosphere: Atmosphere = Atmosphere()) {
  def run(): Boolean = {
    if (isEndOfDay) {
      endOfDay()
      false
    } else {
      clock.addRandomTime()
      atmosphere.change()
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
