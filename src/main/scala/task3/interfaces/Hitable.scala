package task3.interfaces

import task3.models.Human

trait Hitable {
  var hit: Int
  
  def clash(h: Human): Unit
  def getHit: Unit = {
    hit += 1
  }
}
