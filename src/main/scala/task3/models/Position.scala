package task3.models

class Position(var x: Double, var y: Double) {
  def getDistance(position: Position): Double = {
    val dx = x - position.x
    val dy = y - position.y
    Math.sqrt(dx * dx + dy * dy)
  }
}