package task2.tracer

case class TracePoint(name: String, data: Option[String] = None) {
  override def toString: String = data match {
    case Some(d) => s"$name[$d]"
    case None => name
  }
}

