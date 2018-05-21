package com.madamiak.registry

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.madamiak.registry.dao.DatabaseComponent
import com.madamiak.registry.service.{HealthService, SchemaRegistryService}
import com.typesafe.config.{Config, ConfigFactory}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext

object SchemaRegistryBoot extends App {

  implicit val database: Database                   = Database.forConfig("registry.database")
  implicit val system: ActorSystem                  = ActorSystem("schema-registry-system")
  implicit val executor: ExecutionContext           = system.dispatcher
  implicit val materializer: ActorMaterializer      = ActorMaterializer()
  implicit val databaseComponent: DatabaseComponent = new DatabaseComponent

  val config: Config                         = ConfigFactory.load()
  val registryService: SchemaRegistryService = new SchemaRegistryService()
  val healthService: HealthService           = new HealthService()

  Http().bindAndHandle(registryService.route ~
                       healthService.route,
                       config.getString("registry.host"),
                       config.getInt("registry.port"))

}
