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
  def addStoresHumanAndGetReturnsIt(): Unit = {
    val position = new Position(1, 2)
    val human = new Human("Arthur", 10, position = position, state = State.HEALTHY)

    Aggregator.add(human)

    assertTrue(Aggregator.get(position).contains(human))
  }

  @Test
  def addHumanWithIncorrectPositionThrows(): Unit = {
    val position1 = new Position(1, 2)
    val position2 = new Position(1, 2)
    val human1 = new Human("Arthur", 10, position = position1)
    val human2 = new Human("Ford", 8, position = position2)

    Aggregator.add(human1)

    assertThrows(classOf[IllegalArgumentException], () => Aggregator.add(human2))
  }

  @Test
  def lookupUsesEqualityForPosition(): Unit = {
    val storedPos = new Position(10, 20)
    val lookupPos = new Position(10, 20)
    val human = new Human("Ford", 8, position = storedPos, state = State.HEALTHY)

    Aggregator.add(human)

    assertTrue(Aggregator.get(storedPos).isDefined)
    assertTrue(Aggregator.get(lookupPos).isDefined)
  }
}

