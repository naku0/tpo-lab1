package task1

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._

class ArctgTest {

  val EPSILON = 1e-10
  val TEST_EPSILON = 1e-8

  @Test
  def testZero(): Unit = {
    val result = ArctgSeries.arctg(0)
    assertEquals(0.0, result, EPSILON)
  }

  @Test
  def testPositiveValues(): Unit = {
    val testCases = Seq(0.1, 0.2, 0.3, 0.4, 0.5)

    testCases.foreach { x =>
      val expected = math.atan(x)
      val actual = ArctgSeries.arctg(x)

      assertEquals(expected, actual, TEST_EPSILON,
        s"arctg($x) не совпадает с math.atan")
    }
  }

  @Test
  def testNegativeValues(): Unit = {
    val testCases = Seq(-0.1, -0.2, -0.3, -0.4, -0.5)

    testCases.foreach { x =>
      val expected = math.atan(x)
      val actual = ArctgSeries.arctg(x)

      assertEquals(expected, actual, TEST_EPSILON,
        s"arctg($x) не совпадает с math.atan")
    }
  }

  @Test
  def testLargeValues(): Unit = {
    val testCases = Seq(0.6, 0.7, 0.8, 0.9, 0.95, 0.99)

    testCases.foreach { x =>
      val expected = math.atan(x)
      val actual = ArctgSeries.arctg(x)

      assertEquals(expected, actual, 1e-6,
        s"arctg($x) не совпадает с math.atan")
    }
  }

  @Test
  def testBoundaryValues(): Unit = {
    val testCases = Seq(1.0, -1.0)

    testCases.foreach { x =>
      val expected = math.atan(x)
      val actual = ArctgSeries.arctg(x, 1e-8)

      assertEquals(expected, actual, 1e-4,
        s"arctg($x) на границе области сходимости")
    }
  }

  @Test
  def testSymmetry(): Unit = {
    val x = 0.5
    val pos = ArctgSeries.arctg(x)
    val neg = ArctgSeries.arctg(-x)

    assertEquals(pos, -neg, TEST_EPSILON,
      s"Свойство нечетности не выполняется")
  }

  @Test
  def testConvergence(): Unit = {
    val x = 0.5
    val expected = math.atan(x)

    val with5Terms = ArctgSeries.arctgWithTerms(x, 5)
    val with10Terms = ArctgSeries.arctgWithTerms(x, 10)
    val with20Terms = ArctgSeries.arctgWithTerms(x, 20)

    val error5 = math.abs(with5Terms - expected)
    val error10 = math.abs(with10Terms - expected)
    val error20 = math.abs(with20Terms - expected)

    assertTrue(error10 < error5, "10 членов должны быть точнее 5")
    assertTrue(error20 < error10, "20 членов должны быть точнее 10")
  }

  @Test
  def testDifferentPrecision(): Unit = {
    val x = 0.3
    val expected = math.atan(x)

    val lowPrecision = ArctgSeries.arctg(x, 1e-3)
    val highPrecision = ArctgSeries.arctg(x, 1e-12)

    assertTrue(math.abs(lowPrecision - expected) < 1e-3)
    assertTrue(math.abs(highPrecision - expected) < 1e-10)
  }

  @Test
  def testInvalidInput(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      ArctgSeries.arctg(1.5)
    })

    assertThrows(classOf[IllegalArgumentException], () => {
      ArctgSeries.arctg(-1.5)
    })

    assertThrows(classOf[IllegalArgumentException], () => {
      ArctgSeries.arctgWithTerms(1.5, 10)
    })
  }

  @Test
  def testInvalidEpsilon(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      ArctgSeries.arctg(0.5, -1e-5)
    })
  }

  @Test
  def testInvalidTerms(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      ArctgSeries.arctgWithTerms(0.5, 0)
    })
  }

  @Test
  def testKnownValues(): Unit = {
    val testCases = Map(
      0.0 -> 0.0,
      1.0 -> math.Pi / 4
    )

    testCases.foreach { case (x, expected) =>
      val actual = ArctgSeries.arctg(x, 1e-8)
      assertEquals(expected, actual, 1e-6,
        s"arctg($x) должно быть $expected")
    }
  }
}