package task2.tracer

class Tracer {
  private var points: List[TracePoint] = List.empty

  def add(point: TracePoint): Unit = {
    points = points :+ point
  }

  def add(name: String, data: String): Unit = {
    add(TracePoint(name, Some(data)))
  }

  def getTrace: List[String] = points.map(_.toString)

  def reset(): Unit = {
    points = List.empty
  }

  def printTrace(): Unit = {
    println("\nТрассировка: ")
    points.zipWithIndex.foreach { case (p, i) => println(s"$i: $p") }
  }
}