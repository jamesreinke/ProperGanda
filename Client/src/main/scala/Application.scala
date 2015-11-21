package lola

import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.JSApp
import org.scalajs.dom._

import org.scalajs.jquery.{jQuery, JQuery}

object Application extends JSApp {

	def main = {}
	
	@JSExport
	def initiate(url: String): Unit = {
		lola.js.Lola.get(url)
	}



}