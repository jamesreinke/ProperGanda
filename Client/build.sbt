enablePlugins(ScalaJSPlugin)

name := "Maliki"

version := "0.1"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scala-js" 		%%% 	"scalajs-dom" 			% "0.8.0",
  "be.doeraene" 		%%% 	"scalajs-jquery" 		% "0.8.0",
  "com.lihaoyi" 		%% 		"upickle" 				% "0.3.6",
  "com.lihaoyi" 		%%% "upickle" 					% "0.3.6"
)



