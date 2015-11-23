sealed trait CType[A] {
	val value: A
}
case class Number(value: Int) extends CType[Int]
case class Boolean(value: Boolean) extends CType[Boolean]
case class Text(value: String) extends CType[String]

case class Cell[A](contents: A)