package task1

import org.junit.jupiter.api.{BeforeAll, BeforeEach, Test}
import org.junit.jupiter.api.Assertions.*

class ArctgTest {

  var arctgSeries: ArctgSeries = _
  val EPSILON = 1e-10
  val TEST_EPSILON = 1e-8
  
  @BeforeEach
  def setUp(): Unit = {
    arctgSeries = ArctgSeries()
  }

  @Test
  def testZero(): Unit = {
    val result = arctgSeries.arctg(0)
    assertEquals(0.0, result, EPSILON)
  }

  @Test
  def testPositiveValues(): Unit = {
    val testCases = Seq(0.1, 0.2, 0.3, 0.4, 0.5)

    testCases.foreach { x =>
      val expected = math.atan(x)
      val actual = arctgSeries.arctg(x)

      assertEquals(expected, actual, TEST_EPSILON,
        s"arctg($x) не совпадает с math.atan")
    }
  }

  @Test
  def testNegativeValues(): Unit = {
    val testCases = Seq(-0.1, -0.2, -0.3, -0.4, -0.5)

    testCases.foreach { x =>
      val expected = math.atan(x)
      val actual = arctgSeries.arctg(x)

      assertEquals(expected, actual, TEST_EPSILON,
        s"arctg($x) не совпадает с math.atan")
    }
  }

  @Test
  def testLargeValues(): Unit = {
    val testCases = Seq(0.6, 0.7, 0.8, 0.9, 0.95, 0.99)

    testCases.foreach { x =>
      val expected = math.atan(x)
      val actual = arctgSeries.arctg(x)

      assertEquals(expected, actual, 1e-6,
        s"arctg($x) не совпадает с math.atan")
    }
  }

  @Test
  def testBoundaryValues(): Unit = {
    val testCases = Seq(1.0, -1.0)

    testCases.foreach { x =>
      val expected = math.atan(x)
      val actual = arctgSeries.arctg(x, 1e-8)

      assertEquals(expected, actual, 1e-4,
        s"arctg($x) на границе области сходимости")
    }
  }

  @Test
  def testSymmetry(): Unit = {
    val x = 0.5
    val pos = arctgSeries.arctg(x)
    val neg = arctgSeries.arctg(-x)

    assertEquals(pos, -neg, TEST_EPSILON,
      s"Свойство нечетности не выполняется")
  }

  @Test
  def testConvergence(): Unit = {
    val x = 0.5
    val expected = math.atan(x)

    val with5Terms = arctgSeries.arctgWithTerms(x, 5)
    val with10Terms = arctgSeries.arctgWithTerms(x, 10)
    val with20Terms = arctgSeries.arctgWithTerms(x, 20)

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

    val lowPrecision = arctgSeries.arctg(x, 1e-3)
    val highPrecision = arctgSeries.arctg(x, 1e-12)

    assertTrue(math.abs(lowPrecision - expected) < 1e-3)
    assertTrue(math.abs(highPrecision - expected) < 1e-10)
  }

  @Test
  def testInvalidInput(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      arctgSeries.arctg(1.5)
    })

    assertThrows(classOf[IllegalArgumentException], () => {
      arctgSeries.arctg(-1.5)
    })

    assertThrows(classOf[IllegalArgumentException], () => {
      arctgSeries.arctgWithTerms(1.5, 10)
    })
  }

  @Test
  def testInvalidEpsilon(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      arctgSeries.arctg(0.5, -1e-5)
    })
  }

  @Test
  def testInvalidTerms(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      arctgSeries.arctgWithTerms(0.5, 0)
    })
  }

  @Test
  def testKnownValues(): Unit = {
    val testCases = Map(
      0.0 -> 0.0,
      1.0 -> math.Pi / 4
    )

    testCases.foreach { case (x, expected) =>
      val actual = arctgSeries.arctg(x, 1e-8)
      assertEquals(expected, actual, 1e-6,
        s"arctg($x) должно быть $expected")
    }
  }

  @Test
  def testBranchXEqualsZero(): Unit = {
    val result = arctgSeries.arctg(0)
    assertEquals(0.0, result, EPSILON)
  }

  @Test
  def testBranchXNotZero(): Unit = {
    val result = arctgSeries.arctg(0.5)
    assertNotEquals(0.0, result, EPSILON)
  }

  @Test
  def testBranchTermLessThanEpsilon(): Unit = {
    val result = arctgSeries.arctg(0.9, 1e-2)
    assertEquals(math.atan(0.9), result, 1e-2)
  }

  @Test
  def testBranchTermGreaterThanEpsilon(): Unit = {
    val result = arctgSeries.arctg(0.1, 1e-15)
    assertEquals(math.atan(0.1), result, 1e-14)
  }

  @Test
  def testBranchMultipleIterations(): Unit = {
    val x = 0.99
    val eps = 1e-8
    val result = arctgSeries.arctg(x, eps)
    assertEquals(math.atan(x), result, 1e-6)
  }

  @Test
  def testBranchConvergencePattern(): Unit = {
    val x = 0.5
    val withTerms = arctgSeries.arctgWithTerms(x, 20)
    val withEpsilon = arctgSeries.arctg(x, 1e-12)
    assertEquals(withTerms, withEpsilon, 1e-10)
  }

  @Test
  def testBranchInvalidXArctgWithTerms(): Unit = {
    val invalidValues = Seq(1.1, 1.5, 2.0, -1.1, -1.5, -2.0)

    invalidValues.foreach { x =>
      val exception = assertThrows(classOf[IllegalArgumentException], () => {
        arctgSeries.arctgWithTerms(x, 10)
      })
      assertTrue(exception.getMessage.contains("|x| <= 1"))
    }
  }

  @Test
  def testBranchInvalidTermsArctgWithTerms(): Unit = {
    val invalidTerms = Seq(0, -1, -5)

    invalidTerms.foreach { terms =>
      val exception = assertThrows(classOf[IllegalArgumentException], () => {
        arctgSeries.arctgWithTerms(0.5, terms)
      })
      assertTrue(exception.getMessage.contains("Количество членов должно быть положительным"))
    }
  }

  @Test
  def testBranchEpsilonZero(): Unit = {
    val exception = assertThrows(classOf[IllegalArgumentException], () => {
      arctgSeries.arctg(0.5, 0)
    })
    assertTrue(exception.getMessage.contains("Точность должна быть положительной"))
  }

  @Test
  def testBranchExtremelySmallEpsilon(): Unit = {
    val x = 0.3
    val eps = 1e-300
    val result = arctgSeries.arctg(x, eps)
    val expected = math.atan(x)
    assertEquals(expected, result, 1e-14)
  }

  @Test
  def testBranchDifferentTermsCount(): Unit = {
    val x = 0.7
    (1 to 10).foreach { terms =>
      val result = arctgSeries.arctgWithTerms(x, terms)
      val expected = math.atan(x)
      if (terms > 5) {
        assertTrue(math.abs(result - expected) < 0.1)
      }
    }
  }

  @Test
  def testBranchVeryCloseToBoundary(): Unit = {
    val x = 0.999999
    val result = arctgSeries.arctg(x, 1e-8)
    val expected = math.atan(x)

    assertEquals(expected, result, 1e-4)
  }

  @Test
  def testBranchJustAboveBoundary(): Unit = {
    val x = 1.000001
    val exception = assertThrows(classOf[IllegalArgumentException], () => {
      arctgSeries.arctg(x)
    })
    assertTrue(exception.getMessage.contains("|x| <= 1"))
  }
}