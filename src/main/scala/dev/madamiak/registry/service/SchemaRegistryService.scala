package dev.madamiak.registry.service

import akka.actor._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import dev.madamiak.registry.dao.{DatabaseComponent, SchemaEnrollmentDao}
import dev.madamiak.registry.model._
import org.apache.avro.Schema.Parser
import org.apache.avro.SchemaParseException
import spray.json._

import scala.concurrent.ExecutionContext

class SchemaRegistryService(
    implicit val actorSystem: ActorSystem,
    implicit val executionContext: ExecutionContext,
    implicit val databaseConfig: DatabaseComponent
) {

  val schemaEnrollmentDao: SchemaEnrollmentDao = new SchemaEnrollmentDao

  val exceptionHandler = ExceptionHandler {
    case _: SchemaEnrollmentNotFoundException =>
      complete(StatusCodes.NotFound, None)
    case _: SchemaParseException =>
      complete(StatusCodes.BadRequest, None)
  }

  val enrollmentRoute: Route =
    handleExceptions(exceptionHandler) {
      get {
        path("enrollment") {
          complete(
            schemaEnrollmentDao.findAll
              .map(_.toJson)
          )
        } ~
        path("enrollment" / Segment) { strain =>
          complete(
            schemaEnrollmentDao
              .findByStrain(strain)
              .map(_.toJson)
          )
        } ~
        path("enrollment" / Segment / Segment) { (strain, version) =>
          complete {
            schemaEnrollmentDao
              .findById(strain, version)
              .map { row =>
                row
                  .map(_.toJson)
                  .getOrElse(throw new SchemaEnrollmentNotFoundException)
              }
          }
        }
      } ~
      put {
        path("enrollment" / Segment / Segment) { (strain, version) =>
          {
            entity(as[JsValue]) { enrollment =>
              complete {
                new Parser().parse(enrollment.toString)
                schemaEnrollmentDao.update(SchemaEnrollment(strain, version, enrollment))
                (StatusCodes.OK, None)
              }
            }
          }
        }
      } ~
      delete {
        path("enrollment" / Segment / Segment) { (strain, version) =>
          complete {
            schemaEnrollmentDao.delete(strain, version)
            (StatusCodes.NoContent, None)
          }
        }
      }
    }

  class SchemaEnrollmentNotFoundException extends RuntimeException

}
