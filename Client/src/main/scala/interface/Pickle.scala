package lola.interface

import upickle.default._

object Encode {

	def apply(n: Node): String = {
		write(List(n))
	}

	def apply(c: Command): String = {
		write(List(c))
	}

	def apply(cms: List[Command]): String = {
		write(cms)
	}

}

object DecodeCommands {

	def apply(s: String): List[Command] = {
		read[List[Command]](s)
	}

}

object Decode {
	def apply(s: String): List[Node] = {
		read[List[Node]](s)
	} 
}

