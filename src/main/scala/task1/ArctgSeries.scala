package task1

import scala.annotation.tailrec

object ArctgSeries {

  def arctg(x: Double, eps: Double = 1e-10): Double = {
    require(Math.abs(x) <= 1.0, "Ряд сходится только при |x| <= 1")
    require(eps > 0, "Точность должна быть положительной")

    @tailrec
    def loop(n: Int, term: Double, sum: Double): Double = {
      val nextTerm = -term * x * x * (2 * n - 1) / (2 * n + 1)
      val nextSum = sum + term

      if (Math.abs(term) < eps) nextSum
      else loop(n + 1, nextTerm, nextSum)
    }

    if (x == 0) 0
    else loop(1, x, 0)
  }

  def arctgWithTerms(x: Double, terms: Int): Double = {
    require(Math.abs(x) <= 1.0, "Ряд сходится только при |x| <= 1")
    require(terms > 0, "Количество членов должно быть положительным")

    (0 until terms).map { n =>
      val term = math.pow(-1, n) * math.pow(x, 2 * n + 1) / (2 * n + 1)
      term
    }.sum
  }
}
