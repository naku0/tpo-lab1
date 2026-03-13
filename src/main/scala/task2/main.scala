package task2

import task2.mergeSort.MergeSort
import task2.tracer.Tracer

@main
def main(): Unit = {
  val tr = Tracer()
  val mer = MergeSort(tr)
  val l = List(1,46,24,434,4,10)

  mer.sort(l)
  tr.printTrace()
}