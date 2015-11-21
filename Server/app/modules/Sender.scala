package modules

import play.api.mvc.Controller
import lola.interface._


object Send extends Controller {
	def apply(c: Command): play.api.mvc.Result = {
		Ok(Encode(List(c)))
	}
	def apply(cms: List[Command]) = {
		Ok(Encode(cms))
	}
	def apply(c: Command, c1: Command) = {
		Ok(Encode(List(c, c1)))
	}
	def apply(c: Command, c1: Command, c2: Command) = {
		Ok(Encode(List(c, c1, c2)))
	}
	def apply(c: Command, c1: Command, c2: Command, c3: Command) = {
		Ok(Encode(List(c, c1, c2, c3)))
	}
	def apply(c: Command, c1: Command, c2: Command, c3: Command, c4: Command) = {
		Ok(Encode(List(c, c1, c2, c3, c4)))
	}
	def apply(c: Command, c1: Command, c2: Command, c3: Command, c4: Command, c5: Command) = {
		Ok(Encode(List(c, c1, c2, c3, c4, c5)))
	}
	def apply(c: Command, c1: Command, c2: Command, c3: Command, c4: Command, c5: Command, c6: Command) = {
		Ok(Encode(List(c, c1, c2, c3, c4, c5, c6)))
	}
	def apply(c: Command, c1: Command, c2: Command, c3: Command, c4: Command, c5: Command, c6: Command, c7: Command) = {
		Ok(Encode(List(c, c1, c2, c3, c4, c5, c6, c7)))
	}
	def apply(c: Command, c1: Command, c2: Command, c3: Command, c4: Command, c5: Command, c6: Command, c7: Command, c8: Command) = {
		Ok(Encode(List(c, c1, c2, c3, c4, c5, c6, c7, c8)))
	}
}