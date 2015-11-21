package modules

import play.api.mvc._
import lola.interface._

object Extract {

	def apply[A](request: Request[AnyContent]): A = {
		request.body.asText match {
			case Some(text) => {
				Decode(text) match {
					case List(a) => a.asInstanceOf[A]
					case _ => throw new Exception("Cannot decode text as given type") 
				}
			}
			case _ => throw new Exception("No text found in body of message")
		}
	}
	def apply[A,B](request: Request[AnyContent]): (A,B) = {
		request.body.asText match {
			case Some(text) => {
				Decode(text) match {
					case List(a,b) => (a.asInstanceOf[A], b.asInstanceOf[B])
					case _ => throw new Exception("Cannot decode text as given type") 
				}
			}
			case _ => throw new Exception("No text found in body of message")
		}
	}
	def apply[A,B,C](request: Request[AnyContent]): (A,B,C) = {
		request.body.asText match {
			case Some(text) => {
				Decode(text) match {
					case List(a,b,c) => (a.asInstanceOf[A], b.asInstanceOf[B], c.asInstanceOf[C])
					case _ => throw new Exception("Cannot decode text as given type") 
				}
			}
			case _ => throw new Exception("No text found in body of message")
		}
	}
	def apply[A,B,C,D](request: Request[AnyContent]): (A,B,C,D) = {
		request.body.asText match {
			case Some(text) => {
				Decode(text) match {
					case List(a,b,c,d) => (a.asInstanceOf[A], b.asInstanceOf[B], c.asInstanceOf[C], d.asInstanceOf[D])
					case _ => throw new Exception("Cannot decode text as given type") 
				}
			}
			case _ => throw new Exception("No text found in body of message")
		}
	}
}