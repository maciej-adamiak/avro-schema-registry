package dev.madamiak.registry

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

package object model extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val eventFormats: RootJsonFormat[SchemaEnrollment] = jsonFormat3(SchemaEnrollment)

}
