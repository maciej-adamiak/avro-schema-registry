package com.madamiak.registry.dao

import slick.dbio.{Effect, NoStream}
import slick.jdbc.PostgresProfile.api._
import slick.sql.{FixedSqlStreamingAction, SqlAction}

import scala.concurrent.Future
import scala.language.implicitConversions

trait BaseDao {

  val database: Database

  implicit def exec[A](action: FixedSqlStreamingAction[Seq[A], A, _ <: Effect]): Future[Seq[A]] = database.run(action)

  implicit def exec[A](action: SqlAction[A, NoStream, _ <: Effect]): Future[A] = database.run(action)

}
