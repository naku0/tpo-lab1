package task2.mergeSort

import task2.tracer.{Tracer, TracePoints}

class MergeSort(tracer: Tracer) {
  def sort(list: List[Int]): List[Int] = {

    tracer.add(TracePoints.START, s"size=${list.length}")

    val res = if (list.length <= 1) {
      tracer.add(TracePoints.BASE_CASE, if (list.isEmpty) "empty" else "single")
      list
    } else {
      val (left, right) = list.splitAt(list.length / 2)
      tracer.add(TracePoints.DIVIDE, s"${left.length}|${right.length}")

      val sortedLeft = sort(left)
      val sortedRight = sort(right)

      merge(sortedLeft, sortedRight)
    }

    tracer.add(TracePoints.END, s"result=${res.take(3)}")
    res
  }

  private def merge(left: List[Int], right: List[Int]): List[Int] = {
    (left, right) match {
      case (Nil, _) => right
      case (_, Nil) => left
      case (lHead::lTail, rHead::rTail) => 
        if (lHead<rHead) {
          tracer.add(TracePoints.MERGE_STEP, s"L:$lHead")
          lHead::merge(lTail, right)
        } else {
          tracer.add(TracePoints.MERGE_STEP, s"R:$rHead")
          rHead::merge(left, rTail)
        }
    }
  }
}
