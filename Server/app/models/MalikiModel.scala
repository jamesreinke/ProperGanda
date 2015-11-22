package models

import play.api.Play.current

import anorm._
import anorm.SqlParser._

import play.api.db.DB

// A case class which represents the data from one table or many
abstract class Maliki {
	val id: Int
	def values: List[(Column, Any)]
	def columns = values map { x => x._1 }
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
		DB.withConnection {
			implicit session => {
				SQL(
					s"""
						create table if not exists (${columns mkString ","});
						${indexString}
					""").execute()
			}
		}
	}

	def deleteTable: Unit = {
		DB.withConnection {
			implicit session => {
				SQL(
					s"""
						drop table ${name} if exists;
					"""
					).execute()
			}
		}
	}

	def add(item: Maliki): Unit = {
		DB.withConnection {
			implicit session => {
				SQL(
					s"""
						insert into ${name}
						 	(${columns map {x => x.name}})
						 values 
						 	(${item.values mkString ","})
					"""
					).execute()
			}
		}
	}

	def delete(item: Maliki): Unit = {
		DB.withConnection {
			implicit session => {
				SQL(
					s"""
					delete from 
						${name}
					where
						${name}.id = ${item.id}
					""").execute()
			}
		}
	}

	def update(item: Maliki): Unit = {
		DB.withConnection {
			implicit session => {
				SQL(
					s"""
						update
							${name}
						set
							${item.values map { case(col, item) => "${col} = ${item}"} mkString ","}
						where
							id = {id}
					""").execute()
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