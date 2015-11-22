package models


sealed abstract class CType(val name: String)
case object Primary extends CType("bigserial primary key")
case object MInteger extends CType("int") 
case object MBoolean extends CType("boolean")
case object MByte extends CType("bytea")
case object MText extends CType("text")
case object MTimeStamp extends CType("timestamp")


case class Column(name: String, cType: CType, index: Boolean = false) {

	override def toString = s"${name} ${cType.name}"

	/*
		SQL Encoding for generating an index
	*/
	def indexString: String = index match {
		case true => {
			cType match {
				case MInteger => s"(${name});"
				case MText => s"using gin(to_tsvector('english', ${name}));"
				case _ => s"(${name});"
			}
		}
		case false => ""
	}

}
