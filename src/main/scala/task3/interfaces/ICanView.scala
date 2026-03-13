package task3.interfaces

import task3.models.Human
import task3.services.Atmosphere

trait ICanView {
  def canView(atmosphere: Atmosphere, v: Viewable): Boolean
}
