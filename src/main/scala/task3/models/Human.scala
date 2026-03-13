package task3.models

import task3.enums.State
import task3.enums.State.{BLIND, HEALTHY, SICK, TIRED}
import task3.interfaces.{Hitable, ICanView, Viewable}
import task3.services.Atmosphere

import scala.annotation.tailrec

class Human(val name: String,
            val vision: Int,
            var hit: Int = 0,
            var position: Position,
            var state: State,
            var moves: Int=0) extends ICanView with Hitable with Viewable (position) {
  
  override def getHit: Unit = {
    hit += 1
  }

  override def clash(h: Human): Unit = {
    h.getHit
    println(s"$name сталкивается с ${h.name}.")
  }

  override def canView(a: Atmosphere, v: Viewable): Boolean = {
    vision * a.rarefaction() * (position.getDistance(v.pos) / 1000) > 4
  }

  def move(newPos: Position, a: Atmosphere): Unit = {
    position = state match {
      case HEALTHY =>{
        moves += 1
        newPos
      }
      case TIRED => {
        moves += 1
        Position(position.x + (position.x - newPos.x) / 2, position.y + (position.y - newPos.y) / 2)
      }
      case BLIND | SICK => position
    }
    
    if (moves > 5) {
      state = TIRED
    }
    
    def func():Unit = {
      Aggregator.get(position) match {
        case Some(h) => {
          clash(h)
          position.x = position.x - 1
          func()
        }
        case None => ()
      }
    }
    
    func()
  }

  def setState(a: Atmosphere) = a.color() match {
    case Atmosphere.RED | Atmosphere.GREEN => state = BLIND
    case _ =>
  }
}
