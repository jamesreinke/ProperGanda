package models


sealed abstract class CType(val name: String)
sealed abstract class Primary extends CType("bigserial primary key")
sealed abstract class MInteger extends CType("int") 
sealed abstract class MBoolean extends CType("boolean")
sealed abstract class MByte extends CType("bytea")
sealed abstract class MText extends CType("text")
sealed abstract class MTimeStamp extends CType("timestamp")


case class Column(name: String, cType: CType) {

	override def toString = s"${name} ${cType.name}"

}





