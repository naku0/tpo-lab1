package task3.models

import task3.interfaces.Visible
import task3.services.Atmosphere

case class CelestialBody(name: String, distance: Long) extends Visible {
  def isVisible(atmosphere: Atmosphere, vision: Int): Boolean = {
    atmosphere.rarefaction() * vision > distance / 1_000
  }
}
