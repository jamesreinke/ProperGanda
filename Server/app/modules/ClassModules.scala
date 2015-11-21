package modules

import lola.interface._

abstract class Wrapper {

	val node: Node

	def addItem(neu: Node): Unit = {}

}

/*
	Another example of a class style implementation.
*/
class Tableu(h: List[String], b: List[List[String]]) {

	val head = el("thead", items = h map { x => el("th", text = x) })
	val body = el("tbody", items = b map { x => (new Row(x)).node })

	val node = el("table", attributes = Map("class"->"table"), items = List(head, body))

	def filter(s: String, table: Tableu): Unit = {
		for(row <- table.body.items){
			row.style += ("display" -> "none")
			for(col <- row.items) if(col.text contains s) row.style += ("display" -> "")
		}
	} 

	def addRow(table: Node, items: List[String]): Unit = table.items = table.items :+ (new Row(items)).node

	class Row(cols: List[String]) extends Wrapper {

		val node = el("tr", items = (for(col <- cols) yield el("td", text = col)).toList)

	}

}