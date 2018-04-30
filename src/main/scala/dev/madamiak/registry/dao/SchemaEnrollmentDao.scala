package dev.madamiak.registry.dao

import dev.madamiak.registry.model.SchemaEnrollment
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class SchemaEnrollmentDao(implicit val databaseComponent: DatabaseComponent) extends BaseDao {

  val database: Database = databaseComponent.database

  def findAll: Future[Seq[SchemaEnrollment]] = databaseComponent.schemaEnrollmentTable.result

  def findByStrain(strain: String): Future[Seq[SchemaEnrollment]] = {
    databaseComponent.schemaEnrollmentTable
      .filter(x => x.strain === strain)
      .result
  }

  def findById(id: (String, String)): Future[Option[SchemaEnrollment]] = {
    databaseComponent.schemaEnrollmentTable
      .filter(x => x.strain === id._1)
      .filter(x => x.version === id._2)
      .result
      .headOption
  }

  def create(enrollment: SchemaEnrollment): Future[Int] = {
    databaseComponent.schemaEnrollmentTable += enrollment
  }

  def create(enrollments: Seq[SchemaEnrollment]): Future[Option[Int]] = {
    databaseComponent.schemaEnrollmentTable ++= enrollments
  }

  def update(enrollment: SchemaEnrollment): Future[Int] = {
    databaseComponent.schemaEnrollmentTable insertOrUpdate enrollment
  }

  def delete(id: (String, String)): Future[Int] = {
    databaseComponent.schemaEnrollmentTable
      .filter(x => x.strain === id._1)
      .filter(x => x.version === id._2)
      .delete
  }

  def truncate(): Future[Int] = {
    databaseComponent.schemaEnrollmentTable.delete
  }

}
