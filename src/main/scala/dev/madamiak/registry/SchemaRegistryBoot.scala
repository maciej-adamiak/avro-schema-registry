package dev.madamiak.registry

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory.load
import dev.madamiak.registry.dao.DatabaseComponent
import dev.madamiak.registry.service.SchemaRegistryService
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext

object SchemaRegistryBoot extends App {

  implicit val database: Database                   = Database.forConfig("registry.database")
  implicit val system: ActorSystem                  = ActorSystem("schema-registry-system")
  implicit val executor: ExecutionContext           = system.dispatcher
  implicit val materializer: ActorMaterializer      = ActorMaterializer()
  implicit val databaseComponent: DatabaseComponent = new DatabaseComponent

  val registryService: SchemaRegistryService = new SchemaRegistryService()

  Http().bindAndHandle(registryService.enrollmentRoute,
                       load().getString("registry.host"),
                       load().getInt("registry.port"))

}
