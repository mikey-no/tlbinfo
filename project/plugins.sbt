// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.15")

// Setup to work with eclipse - latest version 5.2.4 not 5.2.2 as per
// https://github.com/sbt/sbteclipse
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.4")


// Set up for Ebean
// https://github.com/playframework/play-ebean#releases
addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "4.1.3")