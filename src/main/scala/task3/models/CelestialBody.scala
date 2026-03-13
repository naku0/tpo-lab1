package task3.models

import task3.interfaces.Viewable
import task3.services.Atmosphere

case class CelestialBody(name: String, position: Position) extends Viewable(position)
