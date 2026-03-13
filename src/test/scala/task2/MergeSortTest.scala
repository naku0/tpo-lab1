package task2

import org.junit.jupiter.api.Assertions.{assertEquals, assertTrue}
import org.junit.jupiter.api.{AfterEach, BeforeAll, BeforeEach, Test}
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

  @Test
  def testDuplicateValues(): Unit = {
    val input = List(3, 1, 2, 1, 3, 2)
    val result = mergeSort.sort(input)

    assertEquals(List(1, 1, 2, 2, 3, 3), result)

    val trace = tracer.getTrace
    val mergeSteps = trace.filter(_.startsWith("MERGE_STEP"))

    assertTrue(mergeSteps.exists(_.contains("R:1")), "Должен быть MERGE_STEP с R:1")
    assertTrue(mergeSteps.exists(_.contains("R:2")), "Должен быть MERGE_STEP с R:2")
    assertTrue(mergeSteps.exists(_.contains("R:3")), "Должен быть MERGE_STEP с R:3")
  }

  @Test
  def testAlreadySortedArray(): Unit = {
    val input = List(1, 2, 3, 4, 5)
    val result = mergeSort.sort(input)

    assertEquals(List(1, 2, 3, 4, 5), result)

    val trace = tracer.getTrace
    val mergeSteps = trace.filter(_.startsWith("MERGE_STEP"))

    assertTrue(mergeSteps.forall(_.contains("L:")),
      "Все шаги слияния должны брать элементы слева")
  }

  @Test
  def testReverseSortedArray(): Unit = {
    val input = List(5, 4, 3, 2, 1)
    val result = mergeSort.sort(input)

    assertEquals(List(1, 2, 3, 4, 5), result)

    val trace = tracer.getTrace
    val mergeSteps = trace.filter(_.startsWith("MERGE_STEP"))

    assertTrue(mergeSteps.exists(_.contains("R:")),
      "Должны быть шаги слияния с правыми элементами")
  }

  @Test
  def testOddNumberOfElements(): Unit = {
    val input = List(1, 3, 5, 2, 4)
    val result = mergeSort.sort(input)

    assertEquals(List(1, 2, 3, 4, 5), result)

    val trace = tracer.getTrace

    assertTrue(trace.exists(t => t.startsWith("DIVIDE") && t.contains("2|3")),
      "Должно быть разделение 2|3 или 3|2")
  }

  @Test
  def testLargeList(): Unit = {
    val input = (1 to 100).reverse.toList
    val result = mergeSort.sort(input)

    assertEquals((1 to 100).toList, result)

    val trace = tracer.getTrace
    assertTrue(trace.last.startsWith("END"), "Последняя запись должна быть END")
  }

  @Test
  def testAllEqualElements(): Unit = {
    val input = List(5, 5, 5, 5, 5)
    val result = mergeSort.sort(input)

    assertEquals(List(5, 5, 5, 5, 5), result)

    val trace = tracer.getTrace
    val mergeSteps = trace.filter(_.startsWith("MERGE_STEP"))

    assertTrue(mergeSteps.forall(_.contains("R:5")),
      "Все равные элементы должны браться справа (из-за else ветки)")

    println(s"Количество шагов слияния: ${mergeSteps.size}")
    assertTrue(mergeSteps.size >= 4, "Должно быть минимум 4 шага слияния")
  }

  @Test
  def testNegativeNumbers(): Unit = {
    val input = List(-3, -1, -2, 0, 2, 1)
    val result = mergeSort.sort(input)

    assertEquals(List(-3, -2, -1, 0, 1, 2), result)

    val trace = tracer.getTrace
    val mergeSteps = trace.filter(_.startsWith("MERGE_STEP"))

    assertTrue(mergeSteps.exists(_.contains("L:-3")), "Должен быть шаг с L:-3")
    assertTrue(mergeSteps.exists(_.contains("R:-2")), "Должен быть шаг с R:-2")
  }

  @Test
  def testMergeWithEmptyLists(): Unit = {
    val method = mergeSort.getClass.getDeclaredMethod("merge", classOf[List[Int]], classOf[List[Int]])
    method.setAccessible(true)

    val leftEmpty = List.empty[Int]
    val rightNonEmpty = List(1, 2, 3)
    val result1 = method.invoke(mergeSort, leftEmpty, rightNonEmpty)
    assertEquals(rightNonEmpty, result1)

    val leftNonEmpty = List(1, 2, 3)
    val rightEmpty = List.empty[Int]
    val result2 = method.invoke(mergeSort, leftNonEmpty, rightEmpty)
    assertEquals(leftNonEmpty, result2)

    val result3 = method.invoke(mergeSort, List.empty[Int], List.empty[Int])
    assertEquals(List.empty[Int], result3)
  }

  @Test
  def testTracePointParameters(): Unit = {
    val input = List(10, 5, 7, 3)
    val result = mergeSort.sort(input)
    
    assertEquals(List(3, 5, 7, 10), result)

    val trace = tracer.getTrace

    val startPoints = trace.filter(_.startsWith("START"))
    assertTrue(startPoints.forall(_.matches("""START\[size=\d+\]""")),
      "START должен содержать size")

    val dividePoints = trace.filter(_.startsWith("DIVIDE"))
    assertTrue(dividePoints.forall(_.matches("""DIVIDE\[\d+\|\d+\]""")),
      "DIVIDE должен содержать информацию о размерах")

    val endPoints = trace.filter(_.startsWith("END"))
    assertTrue(endPoints.forall(_.startsWith("END[result=")),
      "END должен содержать результат")
  }

  @Test
  def testDifferentDataTypes(): Unit = {
    val input = List(Int.MinValue, Int.MaxValue, 0, -1, 1)
    val result = mergeSort.sort(input)

    val expected = List(Int.MinValue, -1, 0, 1, Int.MaxValue)
    assertEquals(expected, result)

    val trace = tracer.getTrace
    assertTrue(trace.nonEmpty, "Трассировка не должна быть пустой")
  }

  @Test
  def testPerformanceTracing(): Unit = {
    val input = (1 to 1000).reverse.toList

    val startTime = System.nanoTime()
    val result = mergeSort.sort(input)
    val endTime = System.nanoTime()

    assertEquals((1 to 1000).toList, result)

    val trace = tracer.getTrace
    val startCount = trace.count(_.startsWith("START"))
    val endCount = trace.count(_.startsWith("END"))
    val mergeStepsCount = trace.count(_.startsWith("MERGE_STEP"))

    assertTrue(startCount > 500, "Должно быть много START записей")
    assertTrue(endCount > 500, "Должно быть много END записей")
    assertTrue(mergeStepsCount > 900, "Должно быть много шагов слияния")

    println(s"Время сортировки 1000 элементов: ${(endTime - startTime) / 1_000_000} ms")
  }

  @Test
  def testMergeBranchLeftEmpty(): Unit = {
    val input = List(1)
    val method = mergeSort.getClass.getDeclaredMethod("merge", classOf[List[Int]], classOf[List[Int]])
    method.setAccessible(true)
    val leftEmpty = List.empty[Int]
    val rightNonEmpty = List(1, 2, 3)

    val result1 = method.invoke(mergeSort, leftEmpty, rightNonEmpty)
    assertEquals(rightNonEmpty, result1, "При пустом левом списке должен вернуться правый")
    val trace = tracer.getTrace
    assertTrue(trace.isEmpty || !trace.exists(_.startsWith("MERGE_STEP")),
      "Не должно быть MERGE_STEP при пустом левом списке")
  }

  @Test
  def testMergeBranchRightEmpty(): Unit = {
    val method = mergeSort.getClass.getDeclaredMethod("merge", classOf[List[Int]], classOf[List[Int]])
    method.setAccessible(true)
    val leftNonEmpty = List(1, 2, 3)
    val rightEmpty = List.empty[Int]
    val result2 = method.invoke(mergeSort, leftNonEmpty, rightEmpty)
    assertEquals(leftNonEmpty, result2, "При пустом правом списке должен вернуться левый")
    val trace = tracer.getTrace
    assertTrue(trace.isEmpty || !trace.exists(_.startsWith("MERGE_STEP")),
      "Не должно быть MERGE_STEP при пустом правом списке")
  }

  @Test
  def testMergeBranchBothEmpty(): Unit = {
    val method = mergeSort.getClass.getDeclaredMethod("merge", classOf[List[Int]], classOf[List[Int]])
    method.setAccessible(true)

    val bothEmpty = List.empty[Int]

    val result = method.invoke(mergeSort, bothEmpty, bothEmpty)

    assertEquals(List.empty[Int], result, "Оба списка пусты - результат пустой список")
  }

  @Test
  def testMergeBranchBothNonEmptyLHeadLessRecursive(): Unit = {
    val method = mergeSort.getClass.getDeclaredMethod("merge", classOf[List[Int]], classOf[List[Int]])
    method.setAccessible(true)

    val left = List(1, 4, 6)
    val right = List(2, 3, 5)

    val result = method.invoke(mergeSort, left, right)
    assertEquals(List(1, 2, 3, 4, 5, 6), result)

    val trace = tracer.getTrace
    val mergeSteps = trace.filter(_.startsWith("MERGE_STEP"))

    val expectedSequence = List("L:1", "R:2", "R:3", "L:4", "R:5", "L:6")

    println(s"\n=== Тест lHead < rHead с рекурсией ===")
    mergeSteps.zipWithIndex.foreach { case (step, i) =>
      println(s"$i: $step")
    }

    assertTrue(mergeSteps.head.contains("L:1"),
      "Первый шаг должен быть L:1 при left.head < right.head")

    assertTrue(mergeSteps.exists(_.contains("L:")), "Должны быть левые шаги")
    assertTrue(mergeSteps.exists(_.contains("R:")), "Должны быть правые шаги")
  }

  @Test
  def testMergeBranchBothNonEmptyLHeadGreaterRecursive(): Unit = {
    val method = mergeSort.getClass.getDeclaredMethod("merge", classOf[List[Int]], classOf[List[Int]])
    method.setAccessible(true)

    val left = List(3, 5, 7)
    val right = List(1, 2, 4)

    val result = method.invoke(mergeSort, left, right)
    assertEquals(List(1, 2, 3, 4, 5, 7), result)

    val trace = tracer.getTrace
    val mergeSteps = trace.filter(_.startsWith("MERGE_STEP"))

    println(s"\n=== Тест lHead >= rHead с рекурсией ===")
    mergeSteps.zipWithIndex.foreach { case (step, i) =>
      println(s"$i: $step")
    }

    assertTrue(mergeSteps.head.contains("R:1"),
      "Первый шаг должен быть R:1 при left.head >= right.head")

    assertTrue(mergeSteps.exists(_.contains("L:")), "Должны быть левые шаги")
    assertTrue(mergeSteps.exists(_.contains("R:")), "Должны быть правые шаги")
  }

  @Test
  def testMergeBranchAlternatingPattern(): Unit = {
    val method = mergeSort.getClass.getDeclaredMethod("merge", classOf[List[Int]], classOf[List[Int]])
    method.setAccessible(true)

    val left = List(1, 3, 5, 7, 9)
    val right = List(2, 4, 6, 8, 10)

    val result = method.invoke(mergeSort, left, right)
    assertEquals(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), result)

    val trace = tracer.getTrace
    val mergeSteps = trace.filter(_.startsWith("MERGE_STEP"))

    val pattern = mergeSteps.map { step =>
      if (step.contains("L:")) 'L' else 'R'
    }.mkString

    println(s"\n=== Паттерн слияния ===")
    println(s"Шаги: ${mergeSteps.mkString(", ")}")
    println(s"Паттерн: $pattern")

    assertTrue(pattern.startsWith("L"), "Паттерн должен начинаться с L")

    assertTrue(pattern.contains("LR") || pattern.contains("RL"),
      "Должно быть чередование L и R")
  }

  @Test
  def testMergeBranchEqualElementsRecursive(): Unit = {
    val method = mergeSort.getClass.getDeclaredMethod("merge", classOf[List[Int]], classOf[List[Int]])
    method.setAccessible(true)

    val left = List(2, 2, 4, 6)
    val right = List(2, 3, 5, 7)

    val result = method.invoke(mergeSort, left, right)
    assertEquals(List(2, 2, 2, 3, 4, 5, 6, 7), result)

    val trace = tracer.getTrace
    val mergeSteps = trace.filter(_.startsWith("MERGE_STEP"))

    println(s"\n=== Тест равных элементов ===")
    mergeSteps.zipWithIndex.foreach { case (step, i) =>
      println(s"$i: $step")
    }

    assertTrue(mergeSteps.head.contains("R:2"),
      "Первый шаг должен быть R:2 при равных элементах")

    val twosFromLeft = mergeSteps.count(step => step.contains("L:2"))
    val twosFromRight = mergeSteps.count(step => step.contains("R:2"))

    println(s"Двойки слева: $twosFromLeft, двойки справа: $twosFromRight")
    assertTrue(twosFromLeft == 2, "Должно быть 2 двойки из левого списка")
    assertTrue(twosFromRight == 1, "Должна быть 1 двойка из правого списка")
  }

  @Test
  def testMergeBranchOneElementLeft(): Unit = {
    val method = mergeSort.getClass.getDeclaredMethod("merge", classOf[List[Int]], classOf[List[Int]])
    method.setAccessible(true)

    val left = List(1)
    val right = List(2, 3, 4, 5)

    val result = method.invoke(mergeSort, left, right)
    assertEquals(List(1, 2, 3, 4, 5), result)

    val trace = tracer.getTrace
    val mergeSteps = trace.filter(_.startsWith("MERGE_STEP"))

    mergeSteps.zipWithIndex.foreach { case (step, i) =>
      println(s"$i: $step")
    }

    assertEquals(1, mergeSteps.count(_.contains("L:")),
      "Должен быть только один левый шаг")
    assertTrue(mergeSteps.drop(1).forall(_.contains("R:")),
      "Все последующие шаги должны быть правыми")
  }

  @Test
  def testMergeBranchOneElementRight(): Unit = {
    val method = mergeSort.getClass.getDeclaredMethod("merge", classOf[List[Int]], classOf[List[Int]])
    method.setAccessible(true)

    val left = List(1, 2, 3, 4)
    val right = List(5)

    val result = method.invoke(mergeSort, left, right)
    assertEquals(List(1, 2, 3, 4, 5), result)

    val trace = tracer.getTrace
    val mergeSteps = trace.filter(_.startsWith("MERGE_STEP"))

    println(s"\n=== Тест с одним элементом справа ===")
    mergeSteps.zipWithIndex.foreach { case (step, i) =>
      println(s"$i: $step")
    }

    assertTrue(mergeSteps.forall(_.contains("L:")),
      "Все шаги должны быть левыми, так как правый элемент добавляется через case (Nil, _)")

    assertEquals(4, mergeSteps.size,
      "Должно быть 4 шага (по одному на каждый левый элемент)")

    assertTrue(result.asInstanceOf[List[Int]].contains(5),
      "Результат должен содержать элемент 5")
  }

  @Test
  def testMergeBranchInterleavedWithEmpty(): Unit = {
    val method = mergeSort.getClass.getDeclaredMethod("merge", classOf[List[Int]], classOf[List[Int]])
    method.setAccessible(true)

    val result1 = method.invoke(mergeSort, List.empty, List(1, 2))
    assertEquals(List(1, 2), result1)

    val result2 = method.invoke(mergeSort, List(1, 2), List.empty)
    assertEquals(List(1, 2), result2)

    val result3 = method.invoke(mergeSort, List(1, 4), List(2, 3))
    assertEquals(List(1, 2, 3, 4), result3)

    val result4 = method.invoke(mergeSort, List(3, 4), List(1, 2))
    assertEquals(List(1, 2, 3, 4), result4)

    val result5 = method.invoke(mergeSort, List(2, 4), List(2, 3))
    assertEquals(List(2, 2, 3, 4), result5)
  }
}