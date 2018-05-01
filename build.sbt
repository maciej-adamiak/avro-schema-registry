name := "avro-schema-registry"
organization := "dev.madamiak"
version := "0.3.0-SNAPSHOT"

scalaVersion := "2.12.6"

enablePlugins(JavaAppPackaging)
maintainer in Docker := "Maciej Adamiak <adamiak.maciek@gmail.com>"
dockerExposedPorts := Seq(8000)
dockerRepository := Some("hub.docker.com")
dockerUsername := Some("madamiak")

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.github.cb372" %% "scalacache-core" % "0.23.0",
  "com.github.cb372" %% "scalacache-caffeine" % "0.23.0",
  "com.github.ben-manes.caffeine" % "caffeine" % "2.6.2",
  "com.h2database" % "h2" % "1.4.196",
  "com.lightbend" %% "kafka-streams-scala" % "0.1.0",
  "org.postgresql" % "postgresql" % "42.2.2",
  "com.typesafe.akka" %% "akka-actor" % "2.5.11",
  "com.typesafe.akka" %% "akka-http" % "10.1.0",
  "com.typesafe.akka" %% "akka-http-caching" % "10.1.0",
  "com.typesafe.akka" %% "akka-http-core" % "10.1.0",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.0",
  "com.typesafe.akka" %% "akka-stream" % "2.5.11",
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3",
  "org.apache.kafka" % "kafka-streams" % "1.0.0",
  "org.apache.avro" % "avro" % "1.8.2",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.0" % Test,
  "org.scalatest" %% "scalatest" % "3.0.4" % Test,
  "org.scalamock" %% "scalamock" % "4.1.0" % Test
)