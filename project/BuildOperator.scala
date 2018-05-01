import sbt._

object BuildOperator {

  lazy val dockerConf = settingKey[Int]("Typesafe config file with docker settings")

}
