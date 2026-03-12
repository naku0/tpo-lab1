package task3.models

import task3.interfaces.{Hitable, Visible}
import task3.services.Atmosphere

class Human(val name: String, val vision: Int, var hit: Int = 0) extends Visible with Hitable {
  override def clash(h: Human): Unit = {
    h.getHit
    println(s"$name сталкивается с ${h.name}.")
  }
  
  override def isVisible(a: Atmosphere, v: Int): Boolean = v * a.rarefaction() > 4
}
