package lola

import upickle.default._
import org.scalajs.jquery.{jQuery, JQuery}

object Parse {


	def jsToScala(ns: List[js.Node]): List[interface.Node] = {
		(for(n <- ns) yield Parse(n)) toList
	} 

	def scalaToJs(ns: List[interface.Node]): List[js.Node] = {
		(for(n <- ns) yield Parse(n)) toList
	} 

	/*
		Javascript Node -> Interface Node
	*/

	def apply(n: js.Node): interface.Node = {
		/*
			We need to update this for all values.  I don't think there will be much of a client performance problem
				doing so many node lookups for a single Ajax request.
		*/
		n.value = n.jqSelect.value().toString
		new interface.Node(n.tag, n.attributes, n.style, n.text, n.value, n.items map { x => Parse(x) }, n.id)
	}

	/*
		Interface Node -> Javascript Node
	*/
	def apply(n: interface.Node): js.Node = lola.js.Lola.getById(n.id) match {
		case Some(node) => node
		case None => new js.Node(n.tag, n.attributes, n.style, n.text, n.value, n.items map { x => Parse(x) }, n.id)
	}


	/*
		Interface Command -> Javascript Unit Execution -> Interface Node
	*/
	def apply(c: interface.Command): Unit = c match {
		case interface.Create.Create(n: interface.Node) => Parse(n).create()
		case interface.Delete.Delete(n: interface.Node) => Parse(n).remove
		case interface.OnClick.OnClick(n: interface.Node, c: List[interface.Command]) => Parse(n).onClick(() => Parse(c))
		case interface.OnHover.OnHover(n: interface.Node, c: interface.Command, c2: interface.Command) => Parse(n).onHover(() => Parse(c), () => Parse(c2))
		case interface.OnKeyUp.OnKeyUp(n: interface.Node, c: List[interface.Command]) => Parse(n).onKeyUp(() => Parse(c))
		case interface.SlideUp.SlideUp(n: interface.Node, mili: Int) => Parse(n).slideUp(mili)
		case interface.SlideDown.SlideDown(n: interface.Node, mili: Int) => Parse(n).slideDown(mili)
		case interface.FadeIn.FadeIn(n: interface.Node, mili: Int) => Parse(n).fadeIn(mili)
		case interface.FadeOut.FadeOut(n: interface.Node, mili: Int) => Parse(n).fadeOut(mili)
		case interface.Get.Get(url: String) => js.Lola.get(url)
		case interface.Post.Post(url: String, n: List[interface.Node]) => js.Lola.post(url, Parse.scalaToJs(n))
		case interface.Update.Update(n: interface.Node) => {
			val neu = n.copy()
			neu.id = lola.interface.Lola.assign
			Parse(n).update(Parse(neu))
		}
		case interface.Clear.Clear(s: String) => jQuery(s).empty()
	}

	def apply(cms: List[interface.Command]): Unit = {
		for(c <- cms) Parse(c)
	}

}