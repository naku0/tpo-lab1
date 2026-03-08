package task2

import task2.mergeSort.MergeSort
import task2.tracer.Tracer

@main
def main(): Unit = {
    val list: List[Int] = List(1,23,43,3)
    val tracer = Tracer()
    val mergeSort = MergeSort(tracer)

    println(mergeSort.sort(list))

    tracer.getTrace.foreach(println)
}