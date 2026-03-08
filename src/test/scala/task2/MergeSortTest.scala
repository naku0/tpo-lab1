package task2

import org.junit.jupiter.api.Assertions.{assertEquals, assertTrue}
import org.junit.jupiter.api.{BeforeEach, Test}
import task2.mergeSort.MergeSort
import task2.tracer.Tracer

class MergeSortTest {

  var tracer: Tracer = _
  var mergeSort: MergeSort = _

  @BeforeEach
  def setUp(): Unit = {
    tracer = Tracer()
    mergeSort = MergeSort(tracer)
  }

  @Test
  def testEmptyListTrace(): Unit = {
    val input = List.empty[Int]
    val expectedTrace = List(
      "START[size=0]",
      "BASE_CASE[empty]",
      "END[result=List()]"
    )

    val result = mergeSort.sort(input)
    assertEquals(List.empty, result)
    assertEquals(expectedTrace, tracer.getTrace)
  }

  @Test
  def testSingleElementTrace(): Unit = {
    val input = List(42)
    val expectedTrace = List(
      "START[size=1]",
      "BASE_CASE[single]",
      "END[result=List(42)]"
    )

    val result = mergeSort.sort(input)

    assertEquals(List(42), result)
    assertEquals(expectedTrace, tracer.getTrace)
  }

  @Test
  def testTwoElementsSortedTrace(): Unit = {
    val input = List(1, 2)
    val expectedTrace = List(
      "START[size=2]",
      "DIVIDE[1|1]",
      "START[size=1]",
      "BASE_CASE[single]",
      "END[result=List(1)]",
      "START[size=1]",
      "BASE_CASE[single]",
      "END[result=List(2)]",
      "MERGE_STEP[L:1]",
      "END[result=List(1, 2)]"
    )

    val result = mergeSort.sort(input)

    assertEquals(List(1, 2), result)
    assertEquals(expectedTrace, tracer.getTrace)
  }

  @Test
  def testTwoElementsUnsortedTrace(): Unit = {
    val input = List(2, 1)
    val expectedTrace = List(
      "START[size=2]",
      "DIVIDE[1|1]",
      "START[size=1]",
      "BASE_CASE[single]",
      "END[result=List(2)]",
      "START[size=1]",
      "BASE_CASE[single]",
      "END[result=List(1)]",
      "MERGE_STEP[R:1]",
      "END[result=List(1, 2)]"
    )

    val result = mergeSort.sort(input)

    assertEquals(List(1, 2), result)
    assertEquals(expectedTrace, tracer.getTrace)
  }

  @Test
  def testThreeElementsTrace(): Unit = {
    val input = List(3, 1, 2)

    val result = mergeSort.sort(input)

    assertEquals(List(1, 2, 3), result)

    val trace = tracer.getTrace
    println("\nТрассировка для [3,1,2]: ")
    trace.zipWithIndex.foreach { case (t, i) => println(s"$i: $t") }

    val expectedSequence = List(
      "START[size=3]",
      "DIVIDE[1|2]",
      "START[size=1]",
      "BASE_CASE[single]",
      "END[result=List(3)]",
      "START[size=2]",
      "DIVIDE[1|1]",
      "START[size=1]",
      "BASE_CASE[single]",
      "END[result=List(1)]",
      "START[size=1]",
      "BASE_CASE[single]",
      "END[result=List(2)]",
      "MERGE_STEP[L:1]",
      "END[result=List(1, 2)]",
      "MERGE_STEP[R:1]",
      "MERGE_STEP[R:2]",
      "END[result=List(1, 2, 3)]"
    )

    var lastIndex = -1
    expectedSequence.foreach { point =>
      val pointType = point.split('[').head
      val index = trace.indexWhere(_.startsWith(pointType), lastIndex + 1)
      assertTrue(index > lastIndex, s"Точка $point должна идти после предыдущей (найдена на позиции $index, ожидалось после $lastIndex)")
      lastIndex = index
    }
  }

  @Test
  def testFourElementsTrace(): Unit = {
    val input = List(4, 2, 3, 1)

    val result = mergeSort.sort(input)

    assertEquals(List(1, 2, 3, 4), result)

    val trace = tracer.getTrace
    println("\nТрассировка для [4,2,3,1]: ")
    trace.zipWithIndex.foreach { case (t, i) => println(s"$i: $t") }

    val expectedSequence = List(
      "START[size=4]",
      "DIVIDE[2|2]",
      "START[size=2]",
      "DIVIDE[1|1]",
      "START[size=1]",
      "BASE_CASE[single]",
      "END[result=List(4)]",
      "START[size=1]",
      "BASE_CASE[single]",
      "END[result=List(2)]",
      "MERGE_STEP[R:2]",
      "END[result=List(2, 4)]",
      "START[size=2]",
      "DIVIDE[1|1]",
      "START[size=1]",
      "BASE_CASE[single]",
      "END[result=List(3)]",
      "START[size=1]",
      "BASE_CASE[single]",
      "END[result=List(1)]",
      "MERGE_STEP[R:1]",
      "END[result=List(1, 3)]",
      "MERGE_STEP[R:1]",
      "MERGE_STEP[L:2]",
      "MERGE_STEP[R:3]",
      "END[result=List(1, 2, 3, 4)]"
    )

    var lastIndex = -1
    expectedSequence.foreach { point =>
      val pointType = point.split('[').head
      val index = trace.indexWhere(_.startsWith(pointType), lastIndex + 1)
      assertTrue(index > lastIndex, s"Точка $point должна идти после предыдущей (найдена на позиции $index, ожидалось после $lastIndex)")
      lastIndex = index
    }
  }
}