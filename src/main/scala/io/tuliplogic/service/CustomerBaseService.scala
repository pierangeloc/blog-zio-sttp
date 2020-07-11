package io.tuliplogic.service

import io.tuliplogic.{User, UserId}
import sttp.client.asynchttpclient.zio.SttpClient
import zio.{Has, Task, URLayer, ZIO, ZLayer}

object CustomerBaseService {

  case class Config(baseUrl: String, port: Int)
  type Config = Has[Config]

  trait Service {
    def getCustomer(userId: UserId): Task[Option[User]]
  }

  val live: URLayer[Config, CustomerBaseService] =
    ZLayer.fromServiceM { (config: Config) =>
      import sttp.client._
      import sttp.client.circe._
      import io.circe.

      ZIO.fromFunction[SttpClient, Service] {
        new Service {
          override def getCustomer(userId: UserId): Task[Option[User]] =
            SttpClient.send(
              basicRequest
                .get(uri"${config.baseUrl}:${config.port}/users/${userId.value}")
            )
        }
      }


    }
}
