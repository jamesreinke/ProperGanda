package models

import play.api.Play.current

import anorm._

import play.api.db.DB

// A case class which represents the data from one table or many
abstract class Maliki

abstract class Table(name: String, columns: List[Column], model: Maliki) {

	def createTable: Unit = {
		DB.withConnection {
			implicit session => {
				SQL(
					s"""
						create table if not exists (${columns mkString ","})
					""")
			}
		}
	}

}