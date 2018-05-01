package dev.madamiak.registry.service

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import dev.madamiak.registry.dao.{DatabaseComponent, SchemaEnrollmentDao}
import dev.madamiak.registry.model.{SchemaEnrollment, _}
import org.scalatest._
import slick.jdbc.H2Profile
import slick.jdbc.H2Profile.api._
import spray.json._

class SchemaRegistryServiceSpec extends WordSpec with Matchers with ScalatestRouteTest {

  implicit val database: H2Profile.backend.DatabaseDef =
    Database.forURL("jdbc:h2:mem:test1;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
  
  implicit val databaseComponent: DatabaseComponent = new DatabaseComponent

  val schemaRegistryService                    = new SchemaRegistryService()
  val enrollmentRoute: Route                   = schemaRegistryService.enrollmentRoute
  val schemaEnrollmentDao: SchemaEnrollmentDao = schemaRegistryService.schemaEnrollmentDao

  val enrollmentA = SchemaEnrollment("testStrainA", "0.0.1", """{ "a": 1 }""".toJson)
  val enrollmentB = SchemaEnrollment("testStrainA", "0.0.2", """{ "a": 2 }""".toJson)
  val enrollmentC = SchemaEnrollment("testStrainA", "0.1.0", """{ "a": 1, "b" : "t" }""".toJson)
  val enrollmentD = SchemaEnrollment("testStrainB", "0.0.1", """{ "c": 1 }""".toJson)

  schemaEnrollmentDao.create(Seq(enrollmentA, enrollmentB, enrollmentC, enrollmentD))

  "enrollment route" should {

    "return empty json array when a specific schema registry enrollment has been defined" in {
      Get("/enrollment/testStrainF") ~> enrollmentRoute ~> check {
        status shouldEqual StatusCodes.OK
        responseAs[Seq[SchemaEnrollment]] shouldBe empty
      }
    }

    "return json array when multiple schema registry enrollments have been defined" in {
      Get("/enrollment") ~> enrollmentRoute ~> check {
        status shouldEqual StatusCodes.OK
        responseAs[Seq[SchemaEnrollment]] should contain allOf (enrollmentA, enrollmentB, enrollmentC, enrollmentD)
      }
    }

    "return json array when a specific schema registry enrollment has been defined" in {
      Get("/enrollment/testStrainA") ~> enrollmentRoute ~> check {
        status shouldEqual StatusCodes.OK
        responseAs[Seq[SchemaEnrollment]] should contain allOf (enrollmentA, enrollmentB, enrollmentC)
      }
    }

    "return json array when a specific schema registry enrollment version has been defined" in {
      Get("/enrollment/testStrainB/0.0.1") ~> enrollmentRoute ~> check {
        status shouldEqual StatusCodes.OK
        responseAs[SchemaEnrollment] shouldEqual enrollmentD
      }
    }

    "return 404 when a specific schema registry enrollment version has been defined" in {
      Get("/enrollment/testStrainB/0.1.0") ~> enrollmentRoute ~> check {
        status shouldEqual StatusCodes.NotFound
        responseAs[String] shouldEqual ""
      }
    }
  }
}
