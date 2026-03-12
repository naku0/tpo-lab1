package task3.interfaces

import task3.services.Atmosphere

trait Visible {
  def isVisible(atmosphere: Atmosphere, vision: Int): Boolean
}
