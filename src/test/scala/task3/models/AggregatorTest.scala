package task3.models

import org.junit.jupiter.api.{BeforeEach, Test}
import org.junit.jupiter.api.Assertions.*
import task3.enums.State

class AggregatorTest {

  @BeforeEach
  def reset(): Unit = {
    Aggregator.list = Nil
  }

  @Test
  def addStoresHumanAndGetReturnsItForSamePositionInstance(): Unit = {
    val position = new Position(1, 2)
    val human = new Human("Arthur", 10, position = position, state = State.HEALTHY)

    Aggregator.add(human)

    assertTrue(Aggregator.get(position).contains(human))
  }

  @Test
  def lookupUsesReferenceEqualityForPosition(): Unit = {
    val storedPos = new Position(10, 20)
    val lookupPos = new Position(10, 20)
    val human = new Human("Ford", 8, position = storedPos, state = State.HEALTHY)

    Aggregator.add(human)

    assertTrue(Aggregator.get(storedPos).isDefined)
    assertTrue(Aggregator.get(lookupPos).isEmpty)
  }
}

