package task3.models

import task3.interfaces.Viewable
import task3.services.Atmosphere

case class CelestialBody(name: String, position: Position) extends Viewable(position) {
  def canBeObserved(a: Atmosphere): Boolean = {
    require(a != null)
    val threshold = if (name.toLowerCase.contains("moon")) 0.2f else 0.35f
    a.visibilityFactor() >= threshold
  }
}
