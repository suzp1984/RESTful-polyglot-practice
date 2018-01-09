package io.github.suzp1984.rest

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

trait HelloRoutes {
  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[HelloRoutes])

  lazy val helloRoutes: Route =
    pathPrefix("hello") {
      concat(
        pathEnd {
          concat(
            get { ctx =>
              val header = ctx.request.headers.map { httpHeader =>
                "| " + httpHeader.name() + " -> " + httpHeader.value()
              }.mkString("\n")

              ctx.complete(header)
            },
            parameterMap { params =>
              params.mkString(";")
              complete(" --- " + params.mkString(";"))
            },
            post {
              complete("post")
            }
          )
        }
      )
    }
}
