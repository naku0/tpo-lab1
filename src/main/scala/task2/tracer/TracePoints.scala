package task2.tracer

object TracePoints {
  val START = "START"
  val END = "END"
  val BASE_CASE = "BASE_CASE"
  val DIVIDE = "DIVIDE"
  val MERGE_STEP = "MERGE_STEP"

  def baseCase(reason: String): TracePoint =
    TracePoint(BASE_CASE, Some(reason))

  def divide(leftSize: Int, rightSize: Int): TracePoint =
    TracePoint(DIVIDE, Some(s"$leftSize|$rightSize"))

  def mergeStep(action: String, value: Int): TracePoint =
    TracePoint(MERGE_STEP, Some(s"$action:$value"))
}

