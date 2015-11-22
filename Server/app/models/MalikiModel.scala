package models

import play.api.Play.current

import anorm._
import anorm.SqlParser._

import play.api.db.DB

// A case class which represents the data from one table or many
abstract class Maliki {
	val id: Int
	def values: List[Any]
	def columns: List[Column]
	val table: Table
}

case class Person(val id: Int, val name: String, age: Int) extends Maliki {
	val i = new Column("id", new Primary())
	val n = new Column("name", new MText())
	val a = new Column("age", new MInteger())

	def values = List((i, 1), (n, name), (a, age))

	val table = new Table("Persons", columns)
}

class Table(name: String, columns: List[Column]) {

	/* Table generation statement */
	def createTable: Unit = {
		val statement = s"""
							create table if not exists (${columns mkString ","});
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
		DB.withConnection {
			implicit session => {
				SQL(statement).execute()
			}
		}
		println(statement)
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
		DB.withConnection {
			implicit session => {
				SQL(statement).execute()
			}
		}
		println(statement)

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