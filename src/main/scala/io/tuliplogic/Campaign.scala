package io.tuliplogic

import io.tuliplogic.service.CustomerBaseService
import sttp.client.asynchttpclient.zio.AsyncHttpClientZioBackend
import zio.console.Console
import zio.{App, ExitCode, URIO, ZIO, ZLayer, console}

object Campaign extends App {

  def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    program.provideSomeLayer[Console](
      (ZLayer.succeed(Config("http://localhost", 8080)) ++ AsyncHttpClientZioBackend.layer()) >>> CustomerBaseService.live
    ).orDie.exitCode

  val customerIds: List[CustomerId] = List(
    CustomerId("123123"),
    CustomerId("456456"),
    CustomerId("789789"),
    CustomerId("000000")
  )

  val program: ZIO[Console with CustomerBaseService, CustomerBaseService.Error, Unit] =
    ZIO.foreach(customerIds) { customerId =>
      CustomerBaseService.getCustomer(customerId).flatMap {
        _.fold(console.putStrLn(s"Customer $customerId not found"))( cust =>
          if (cust.promotionOptIn) sendEmail(cust.email)
          else console.putStrLn(s"Not sending email to $customerId")
        )
      }
    }.unit

  def sendEmail(email: Email): URIO[Console, Unit] =
    console.putStrLn(s"Sending promotion email to ${email.value}")

}



