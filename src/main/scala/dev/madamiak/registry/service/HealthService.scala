package dev.madamiak.registry.service

import akka.http.scaladsl.model.{HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

class HealthService {

  val route: Route = get {
    path("health") {
      complete(
        HttpResponse(StatusCodes.OK, entity = HttpEntity.Empty)
      )
    }
  }
  
}
