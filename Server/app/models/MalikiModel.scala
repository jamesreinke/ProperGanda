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
	val table: Table = new Table(name, columns)
}
/* SQL Command Objects */
sealed trait Command
case object Create extends Command
case object Delete extends Command
case object Insert extends Command
case object Update extends Command
case object Select extends Command


object Statement {
	def apply(t: Table, command: Command, item: Maliki): Unit = {
		DB.withConnection {
			implicit session => command match {
				case Create => SQL(s"""
					create table if not exist ${t.name} 
						(${item.columns mkString ","})""").execute()
				case Delete => SQL(s"""
					delete from ${t.name} 
						where ${t.name}.id = ${item.id}""").execute()
				case Insert => SQL(s"""
					insert into ${t.name} 
						(${item.columns map {x => x.name} mkString ""})""").executeInsert()
				case Update => SQL(s"""
					update ${t.name} 
						set ${item.values map { case(col, item) => "${col} = ${item}"} mkString ","}""").executeUpdate()
				case Select => SQL(s"""
					select * from ${t.name}
						where ${t.name}.id = ${item.id}""").execute()
			}
		}
	}
}

class Table(val name: String, val columns: List[Column]) {

	/* Table generation statement */
	def createTable: Unit = {
		DB.withConnection {
			implicit session => {
				SQL(s"""
						create table if not exists ${name} (${columns mkString ","});
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
					""").execute()
			}
		}
	}

	def add(item: Maliki): Unit = Statement(this, Create, item)

	def delete(item: Maliki): Unit = Statement(this, Delete, item)

	def update(item: Maliki): Unit = Statement(this, Update, item)


	

	/* Generates SQL index statements given columns */
	def indexString: String = {
		val zipped: List[(Column, Int)] = columns.zipWithIndex
		zipped map 
			{ case (column, index) => 
				s"create index ${name}-${index} on ${name} ${column.indexString}" 
			} mkString ";"
	}



}