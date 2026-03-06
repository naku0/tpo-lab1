package task2

import task2.mergeSort.MergeSort
import task2.tracer.Tracer

@main
def main(): Unit = {
    val list: List[Int] = List(1,23,43,1,3,2,2,4)
    val tracer = Tracer()
    val mergeSort = MergeSort(tracer)

    print(mergeSort.sort(list))
}