package dev.madamiak.registry.dao

import slick.jdbc.PostgresProfile.api._
import slick.jdbc.meta.MTable
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.language.implicitConversions

class DatabaseComponent(implicit val database: Database) {

  implicit val session: Session = database.createSession()

  private[dao] val schemaEnrollmentTable = TableQuery[SchemaEnrollmentTable]

  /**
    * Create schema if does not exist
    */
  private val schemaCreation: Future[List[Unit]] = database
    .run(MTable.getTables)
    .map(v => v.map(mt => mt.name.name))
    .map(
      names =>
        List(schemaEnrollmentTable)
          .filter(t => !names.contains(t.baseTableRow.tableName))
          .map(_.schema.create)
    )
    .flatMap(c => database.run(DBIO.sequence(c)))

  Await.result(schemaCreation, Duration.Inf)
}
