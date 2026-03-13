package task3.models

object Aggregator {
  var list: List[Human] = List()
  def get(position: Position): Option[Human] = {
    val li = list.filter(h => h.position == position)
    li.headOption
  }
  
  def add(human: Human): Unit = {
    list = (human::list)
  }
}
