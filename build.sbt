name := """tlbinfo"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += "com.h2database" % "h2" % "1.4.192"
libraryDependencies ++= Seq(evolutions, jdbc)

// Compile the project before generating Eclipse files, so that generated .scala 
// or .class files for views and routes are present
EclipseKeys.preTasks := Seq(compile in Compile, compile in Test)
