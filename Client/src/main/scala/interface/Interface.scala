package lola.interface

object Lola {

	/*
		Assign permenant ID's to all nodes
	*/
	import scala.util.Random
	val r = new Random()
	def assign = {
		val s = r.alphanumeric
		s take 10 mkString ""  // returns a pseudo-random string of 10 characters (negligible chance of collision)
	}

}

sealed case class Node(
	var tag: String,
	var attributes: Map[String, String],
	var style: Map[String, String],
	var text: String,
	var value: String,
	var items: List[Node],
	var id: String)

object el {
	def apply(
		tag: String = "",
		attributes: Map[String,String] = Map(),
		style: Map[String,String] = Map(),
		text: String = "",
		value: String = "",
		items: List[Node] = List(),
		id: String = Lola.assign): Node = {
		new Node(tag, attributes, style, text, value, items, id)
	}
}

sealed trait Command

object Clear {
	def apply() = {
		new Clear("body")
	}
	def apply(s: String) = {
		new Clear(s)
	}
	sealed case class Clear(s: String) extends Command
}

object OnClick {
	def apply(n: Node, cms: List[Command]) = {
		new OnClick(n, cms)
	}
	def apply(n: Node, cm: Command) = {
		new OnClick(n, List(cm))
	}
	def apply(n: Node, cm: Command, cm2: Command) = {
		new OnClick(n, List(cm, cm2))
	}
	def apply(n: Node, cm: Command, cm2: Command, cm3: Command) = {
		new OnClick(n, List(cm, cm2, cm3))
	}
	def apply(n: Node, cm: Command, cm2: Command, cm3: Command, cm4: Command) = {
		new OnClick(n, List(cm, cm2, cm3, cm4))
	}
	sealed case class OnClick(n: Node, cm: List[Command]) extends Command
}

object OnHover {
	def apply(n: Node, cm: Command, cm2: Command) = {
		new OnHover(n, cm, cm2)
	}
	sealed case class OnHover(n: Node, cm: Command, cm2: Command) extends Command
}


object OnKeyUp {
	def apply(n: Node, cms: List[Command]) = {
		new OnKeyUp(n, cms)
	}
	def apply(n: Node, cm: Command) = {
		new OnKeyUp(n, List(cm))
	}
	def apply(n: Node, cm: Command, cm2: Command) = {
		new OnKeyUp(n, List(cm, cm2))
	}
	def apply(n: Node, cm: Command, cm2: Command, cm3: Command) = {
		new OnKeyUp(n, List(cm, cm2, cm3))
	}
	def apply(n: Node, cm: Command, cm2: Command, cm3: Command, cm4: Command) = {
		new OnKeyUp(n, List(cm, cm2, cm3, cm4))
	}
	sealed case class OnKeyUp(n: Node, cm: List[Command]) extends Command
}

object Create {
	def apply(n: Node) = {
		new Create(n)
	}
	sealed case class Create(n: Node) extends Command
}

object Delete {
	def apply(n: Node) = {
		new Delete(n)
	}
	sealed case class Delete(n: Node) extends Command
}
object Update {
	def apply(n: Node) = {
		new Update(n)
	}
	sealed case class Update(n: Node) extends Command
}
object Get {
	def apply(url: String) = {
		new Get(url)
	}
	sealed case class Get(url: String) extends Command
}
object Post {
	def apply(url: String, n: List[Node]) = {
		new Post(url, n)
	}
	def apply(url: String, n: Node) = {
		new Post(url, List(n))
	}
	def apply(url: String, n: Node, n2: Node) = {
		new Post(url, List(n, n2))
	}
	def apply(url: String, n: Node, n2: Node, n3: Node) = {
		new Post(url, List(n, n2, n3))
	}
	def apply(url: String, n: Node, n2: Node, n3: Node, n4: Node) = {
		new Post(url, List(n, n2, n3, n4))
	}
	sealed case class Post(url: String, n: List[Node]) extends Command
}
object SlideUp {
	def apply(n: Node, mili: Int = 400) = {
		new SlideUp(n, mili)
	}
	sealed case class SlideUp(n: Node, mili: Int = 400) extends Command
}
object SlideDown {
	def apply(n: Node, mili: Int = 400) = {
		new SlideDown(n, mili)
	}
	sealed case class SlideDown(n: Node, mili: Int = 400) extends Command
}
object FadeOut {
	def apply(n: Node, mili: Int = 400) = {
		new FadeOut(n, mili)
	}
	sealed case class FadeOut(n: Node, mili: Int = 400) extends Command
}
object FadeIn {
	def apply(n: Node, mili: Int = 400) = {
		new FadeIn(n, mili)
	}
	sealed case class FadeIn(n: Node, mili: Int = 400) extends Command
}
object GetValue {
	def apply(n: Node) = {
		new GetValue(n)
	}
	sealed case class GetValue(n: Node) extends Command
}
object SetText {
	def apply(n: Node, s: String) = {
		new SetText(n, s)
	}
	sealed case class SetText(n: Node, s: String) extends Command
}
object If {
	def apply(b: Boolean, c: Command) = {
		new If(b: Boolean, c: Command)
	}
	sealed case class If(b: Boolean, c: Command) extends Command
}

