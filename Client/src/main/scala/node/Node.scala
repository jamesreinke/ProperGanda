package lola.js

import lola.Parse
import upickle.default._

import lola.interface.{Encode, DecodeCommands, Decode}
import scala.scalajs.js.JSApp
import org.scalajs.dom
import dom._
import org.scalajs.jquery.{jQuery, JQuery}

/* 
	Master object used for controlling communication logic between the client and the server.
*/
object Lola {

	/* Permenant nodes, referenced by id */
	var nodes: Map[String, Node] = Map()

	def register(n: Node): Unit = nodes += n.id -> n

	def remove(n: Node): Unit = nodes -= n.id

	def getById(id: String): Option[Node] = nodes get id

	import org.scalajs.dom.ext.Ajax
	import scala.concurrent._
	import scala.scalajs
                .concurrent
                .JSExecutionContext
                .Implicits
                .runNow

	/*
		Send data, wait for a response... decode the message and execute the abstract syntax tree.
	*/
	
	def get(url: String, timeout: Int = 0, headers: Map[String, String] = Map(), withCredentials: Boolean = false): Unit = {
		Ajax.get(url, "", timeout, headers, withCredentials).onSuccess {
			case xhr => {
				val c = DecodeCommands(xhr.responseText)
				Parse(c)
			}
		}
	}
	
	def post(url: String, n: List[Node], timeout: Int = 0, headers: Map[String, String] = Map(), withCredentials: Boolean = false): Unit = {
		Ajax.post(url, write(Parse.jsToScala(n)), timeout, headers, withCredentials).onSuccess {
			case xhr => {
				val cms = DecodeCommands(xhr.responseText)
				Parse(cms)
			}
		}
	}

}
/*
	JQuery selection
*/
sealed trait JQSelect {

	var id: String

	var cache: Option[JQuery] = None

	def jqSelect: JQuery = cache match {
		case Some(jQuery) => jQuery
		case None => {
			val _jqCache = jQuery(s"#$id")
			cache = Some(_jqCache)
			_jqCache
		}
	}
}
/*
	Javascript Selection
*/
sealed trait Select extends JQSelect {

	def jsSelect: html.Element = {
		jqSelect.get(0).asInstanceOf[html.Element]
	}
}

sealed trait JQAnimation extends JQSelect {

	def slideDown(mili: Int = 400): Unit = {
		jqSelect.slideDown(mili)
	}

	def slideUp(mili: Int = 400): Unit = {
		jqSelect.slideUp(mili)
	}

	def fadeIn(mili: Int = 400): Unit = {
		jqSelect.fadeIn(mili)
	}

	def fadeOut(mili: Int = 400): Unit = {
		jqSelect.fadeOut(mili)
	}
	
}

sealed trait Reaction extends Select {

	def onClick(f: () => Unit): Unit = {
		jsSelect.onclick = (e: dom.MouseEvent) => {
			f()
		}
	}

	def onHover(enter: () => Unit, exit: () => Unit) = {
		jsSelect.onmouseenter = (e: dom.MouseEvent) => {
			enter()
		}
		jsSelect.onmouseleave = (e: dom.MouseEvent) => {
			exit()
		}
	}

	def onKeyUp(f: () => Unit): Unit = {
		jsSelect.onkeyup = (e: dom.KeyboardEvent) => {
			f()
		}
	}
}

sealed trait Attributes extends Select {

	var attributes: Map[String,String]
	var style: Map[String,String]
	var value: String
	var text: String
	var items: List[Node]

	/*
		Update a node to mirror the contents of an interface node.

		TODO: Fix this function!!!  Maybe we can just delete it and recreate it in the same position?
	*/
	def update(n: Node): Unit = {
		jqSelect.html(n.render)
	}
	/*
		Attributes
	*/
	def setAttribute(k: String, v: String): Unit = {
		attributes += (k -> v)
		jqSelect.attr(k, v)
	}
	/*
		Values
	*/
	def setValue(s: String): Unit = {
		jqSelect.value(s)
	}
	/*
		Text
	*/
	def setText(s: String): Unit = {
		text = s
		jqSelect.text(s)
	}
	/*
		Css
	*/
	def setCss(style: Map[String,String]): Unit = {
		jqSelect.attr("style", "")
		for((k,v) <- style) jqSelect.css(k,v)
	}

}

sealed trait Position extends Select with Attributes {

	sealed class Point(val x: Double, val y: Double) {

		def +(p: Point): Point = {
			new Point(x + p.x, y + p.y)
		}

		def avg(p: Point): Point = {
			new Point(x + p.x / 2, y + p.y / 2)
		}
	}

	def position: Point = new Point(jsSelect.offsetLeft, jsSelect.offsetTop)

	def setPosition(p: Point): Unit = {
		val style = Map("position" -> "absolute", "left" -> (p.x.toString + "px"), "top" -> (p.y.toString + "px"))
		setCss(style)
	}
}
/*
	A unit object which encodes javascript and html/css logic in a singleton class object.
*/
sealed class Node(
	var tag: String, 
	var attributes: Map[String, String], 
	var style: Map[String,String], 
	var text: String,
	var value: String,
	var items: List[Node],
	var id: String) extends JSApp with Select with JQAnimation with Attributes with Reaction {

	private val atrStr = attributes map { x => x._1 + "=" + "'" + x._2 + "'" } mkString " "
	private val styleStr = style map { x => x._1 + ":" + x._2 + ";" } mkString ""
		
	override def toString = render

	def render = {
		s"""
		<${tag} id=${id} ${atrStr} style="${styleStr}">
			${text}
			${items mkString ""}
		</${tag}>
		"""
	}
	/*
		Returns true or false if this html element contains the matched string
	*/
	def contains(s: String, n: Node = this): Boolean = {
		n.text contains s match {
			case true => true
			case false => {
				for(item <- items) contains(s, item) match {
					case true => true
					case false => {}
				}
				false
			}
		}
	}

	private def self = this

	def remove: Unit = {
		jqSelect.remove
		Lola.remove(self)
		cache = None
	}

	object create {
		def apply(): Unit = {
			remove
			jQuery("body").append(render)
			Lola.register(self)
		}
		def apply(item: Node): Unit = {
			item.jqSelect.append(render)
			Lola.register(self)
		}
	}

	def main: Unit = {}

}
