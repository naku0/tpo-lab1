package task3.interfaces

import task3.models.Human

trait Hitable {
  def clash(h: Human): Unit
  def getHit: Unit 
}
