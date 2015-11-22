package models


sealed abstract class CType(val name: String)
sealed case class Primary() extends CType("bigserial primary key")
sealed case class MInteger() extends CType("int") 
sealed case class MBoolean() extends CType("boolean")
sealed case class MByte() extends CType("bytea")
sealed case class MText() extends CType("text")
sealed case class MTimeStamp() extends CType("timestamp")


case class Column(name: String, cType: CType, index: Boolean = false) {

	override def toString = s"${name} ${cType.name}"

	/*
		SQL Encoding for generating an index
	*/
	def indexString: String = {
		cType match {
			case MInteger() => s"(${name});"
			case MText() => s"using gin(to_tsvector('english', ${name}));"
			case _ => s"(${name});"
		}
	}

}
