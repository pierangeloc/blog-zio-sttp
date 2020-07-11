package io.tuliplogic.service

import io.tuliplogic.{Customer, CustomerId}
import sttp.client.asynchttpclient.zio.SttpClient
import sttp.model.StatusCode
import zio.{IO, URLayer, ZIO, ZLayer}

object CustomerBaseService {

  case class Error(message: String, override val getCause: Throwable) extends Exception

  trait Service {
    def getCustomer(customerId: CustomerId): IO[Error, Option[Customer]]
  }

  def getCustomer(customerId: CustomerId): ZIO[CustomerBaseService, Error, Option[Customer]] =
    ZIO.accessM(_.get.getCustomer(customerId))



  val live: URLayer[SttpClient with Config, CustomerBaseService] =
    ZLayer.fromServiceM[io.tuliplogic.Config, SttpClient, Nothing, Service] { config =>
      import sttp.client._
      import sttp.client.circe._
      import io.circe.generic.auto._

      ZIO.fromFunction[SttpClient, Service] { sttpClient =>
        new Service {
          override def getCustomer(customerId: CustomerId): IO[Error, Option[Customer]] = {
            val request = basicRequest
              .get(uri"${config.baseUrl}:${config.port}/customers/${customerId.value}")
              .response(asJson[Customer])

            (
              for {
                resp <- SttpClient.send(request)
                  .mapError(t => Error("Connection Error", t))
                res <- (resp.code, resp.body) match {
                  case (StatusCode.NotFound, _) => ZIO.none
                  case (_, Right(user))         => ZIO.some(user)
                  case (_, Left(e))             => ZIO.fail(Error("API error", e))
                }
              } yield res
            ).provide(sttpClient)

          }
        }
      }
    }
}
