package io.tuliplogic.service

import io.tuliplogic
import io.tuliplogic.{Config, Customer, CustomerId, Email}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import sttp.client.asynchttpclient.zio.{AsyncHttpClientZioBackend, SttpClient}
import zio.{BootstrapRuntime, ULayer, ZLayer}

class CustomerBaseServiceTest extends AnyWordSpec with Matchers {
  "CustomerBaseService live" should {
    "return Some(Customer) when customer found" in new TestScope {

      val res = unsafeRun(
        CustomerBaseService.getCustomer(CustomerId("123123")).provideLayer(
          (configLayer ++ okSttp  ) >>> CustomerBaseService.live
        ).either
      )

      val expected = Right(Some(Customer(CustomerId("123123"), Email("weierstrass@maths.com"), true)))
      res shouldEqual expected
    }

    "return None when customer not found" in new TestScope {
      val res = unsafeRun(
        CustomerBaseService.getCustomer(CustomerId("123122")).provideLayer(
          (configLayer ++ notFoundSttp  ) >>> CustomerBaseService.live
        ).either
      )

      val expected = Right(None)
      res shouldEqual expected
    }

    "return error when api call returns an error" in new TestScope {
      val res = unsafeRun(
        CustomerBaseService.getCustomer(CustomerId("123123")).provideLayer(
          (configLayer ++ errorSttp  ) >>> CustomerBaseService.live
        ).either
      )

      res.isLeft shouldEqual true
    }


  }

  trait TestScope extends BootstrapRuntime {
    val configLayer: ULayer[tuliplogic.service.Config] = ZLayer.succeed(Config("http://localhost", 8080))

    val okSttp: ULayer[SttpClient] = ZLayer.succeed(
      AsyncHttpClientZioBackend.stub
        .whenRequestMatches(req => req.uri.toString == "http://localhost:8080/customers/123123")
        .thenRespond(
          """{
            |  "customerId": "123123",
            |  "email": "weierstrass@maths.com",
            |  "promotionOptIn": true
            |}""".stripMargin
        )
    )

    val notFoundSttp: ULayer[SttpClient] = ZLayer.succeed(
      AsyncHttpClientZioBackend.stub
        .whenRequestMatches(req => {
          println(req.uri.toString()); req.uri.toString == "http://localhost:8080/customers/123122"
        })
        .thenRespondNotFound()
    )

    val errorSttp: ULayer[SttpClient] = ZLayer.succeed(
      AsyncHttpClientZioBackend.stub
        .whenRequestMatches(req => req.uri.toString == "http://localhost:8080/customers/123123")
        .thenRespondServerError()
    )
  }

}
