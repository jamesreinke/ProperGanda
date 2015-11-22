package models

import play.api.Play.current

import anorm._
import anorm.SqlParser._

import play.api.db.DB

// A case class which represents the data from one table or many
abstract class Maliki(name: String) {
	val id: Int
	val values: List[Any]
	val columns: List[Column]
	val table: Table = new Table(columns)
}

case class Person(id: Int, name: String, age: Int) extends Maliki("Persons") {

	val values = List(id, name, age)
	val i = new Column("id", Primary)
	val n = new Column("name", MText)
	val a = new Column("age", MInteger)
	val columns = List(i, n, a)
	private val table = new Table("Persons", columns)
	def create: Boolean = {

	}

}
sealed trait Command
case object Create extends Command
case object Delete extends Command
case object Insert extends Command
case object Update extends Command


object Statement {
	def apply(t: Table, x: Command, item: Maliki): Unit = {
		DB.withConnection {
			implicit session => x match {
				case Create => SQL(s"""create table if not exist ${t.name} (${item.columns mkString ","})""")
				case Delete => SQL(s"""delete from ${name} where ${t.name}.id = ${item.id}""")
				case Insert => SQL(s"""insert into $[name} (${item.columns map {x => x.name} mkString ""})""")
			}
		}
	}
}

class Table(name: String, columns: List[Column]) {

	/* Table generation statement */
	def createTable: Unit = {
		val statement = s"""
							create table if not exists ${name} (${columns mkString ","});
							${indexString}
						"""
		println(statement)
		DB.withConnection {
			implicit session => {
				SQL(statement).execute()
			}
		}
	}

	def deleteTable: Unit = {
		val statement = s"""
							drop table ${name} if exists;
						"""
		println(statement)
		DB.withConnection {
			implicit session => {
				SQL(statement).execute()
			}
		}
	}

	def add(item: Maliki): Unit = {
		val statement = s"""
							insert into ${name}
							 	(${columns map {x => x.name} mkString ""})
							 values 
							 	(${item.values mkString ","})
						"""
		println(statement)
		DB.withConnection {
			implicit session => {
				SQL(statement).execute()
			}
		}
	}

	def delete(item: Maliki): Unit = {
		val statement = 
					s"""
						delete from 
							${name}
						where
							${name}.id = ${item.id}
					"""
		println(statement)
		DB.withConnection {
			implicit session => {
				SQL(statement).execute()
			}
		}
	}

	def update(item: Maliki): Unit = {
		val statement = s"""
							update
								${name}
							set
								${item.values map { case(col, item) => "${col} = ${item}"} mkString ","}
							where
								id = {id}
						"""
		println(statement)
		DB.withConnection {
			implicit session => {
				SQL(statement).execute()
			}
		}

	}

	/* Generates SQL index statements given columns */
	private def indexString: String = {
		val zipped: List[(Column, Int)] = columns.zipWithIndex
		zipped map 
			{ case (column, index) => 
				s"create index ${name}-${index} on ${name} ${column.indexString}" 
			} mkString ","
	}



}