package io.tuliplogic

import cask.MainRoutes

object UserManagementApp extends MainRoutes {

  lazy val users: Map[CustomerId, Customer] = Map(
    CustomerId("123123") -> Customer(CustomerId("123123"), Email("weierstrass@maths.com"), true),
    CustomerId("456456") -> Customer(CustomerId("456456"), Email("banach@maths.com"), true),
    CustomerId("789789") -> Customer(CustomerId("789789"), Email("euler@maths.com"), false)
  )

  @cask.get("/customers/:customerId")
  def user(customerId: String) = {
    users.get(CustomerId(customerId))
      .fold(cask.Abort(404))(u => cask.Response(upickle.default.write(u)))

  }

  initialize()

}
