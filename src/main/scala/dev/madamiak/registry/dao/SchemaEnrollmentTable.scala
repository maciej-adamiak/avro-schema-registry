package dev.madamiak.registry.dao

import dev.madamiak.registry.model.SchemaEnrollment
import slick.jdbc.H2Profile.api._
import spray.json._

class SchemaEnrollmentTable(tag: Tag) extends Table[SchemaEnrollment](tag, "schemaEnrollment") {

  def pk = primaryKey("id", (strain, version))

  def strain = column[String]("strain")

  def version = column[String]("version")

  def idx = index("schema_unique", (strain, version), unique = true)

  override def * = (strain, version, schema) <> ( { row =>
    SchemaEnrollment(row._1, row._2, row._3.map(_.toChar).mkString.parseJson)
  }, { enrollment: SchemaEnrollment =>
    Some(enrollment.strain, enrollment.version, enrollment.schema.toString.getBytes)
  })

  def schema = column[Array[Byte]]("schema")
}
