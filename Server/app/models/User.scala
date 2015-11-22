package models



case class Person(id: Int, name: String, age: Int) extends Maliki("Persons") {

	val values = List(id, name, age)
	val i = new Column("id", Primary)
	val n = new Column("name", MText)
	val a = new Column("age", MInteger)
	val columns = List(i, n, a)

}

