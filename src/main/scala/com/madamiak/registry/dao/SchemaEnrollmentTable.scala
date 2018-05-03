package com.madamiak.registry.dao

import com.madamiak.registry.model.SchemaEnrollment
import slick.jdbc.PostgresProfile.api._
import spray.json._

class SchemaEnrollmentTable(tag: Tag) extends Table[SchemaEnrollment](tag, "schema_enrollment") {

  def pk = primaryKey("id", (strain, version))

  def strain = column[String]("strain")

  def version = column[String]("version")

  override def * =
    (strain, version, schema) <> ({ row =>
      SchemaEnrollment(row._1, row._2, row._3.map(_.toChar).mkString.parseJson)
    }, { enrollment: SchemaEnrollment =>
      Some(enrollment.strain, enrollment.version, enrollment.schema.toString.getBytes)
    })

  def schema = column[Array[Byte]]("schema")
}
