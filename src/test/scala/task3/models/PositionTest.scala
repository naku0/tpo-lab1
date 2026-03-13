package task3.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PositionTest {

  @Test
  def distanceIsCalculatedByEuclideanFormula(): Unit = {
    val p1 = new Position(0, 0)
    val p2 = new Position(3, 4)

    assertEquals(5.0, p1.getDistance(p2))
  }

  @Test
  def distanceToSamePointIsZero(): Unit = {
    val p = new Position(7, -3)

    assertEquals(0.0, p.getDistance(p))
  }
}

