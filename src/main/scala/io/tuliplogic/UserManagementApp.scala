package io.tuliplogic

import cask.MainRoutes

object UserManagementApp extends MainRoutes {

  lazy val users: Map[UserId, User] = Map(
    UserId("123123") -> User(UserId("123123"), Email("weierstrass@maths.com"), true),
    UserId("456456") -> User(UserId("456456"), Email("banach@maths.com"), true),
    UserId("789789") -> User(UserId("789789"), Email("euler@maths.com"), false)
  )

  @cask.get("/users/:userId")
  def user(userId: String) = {
    users.get(UserId(userId))
      .fold(cask.Abort(404))(u => cask.Response(upickle.default.write(u)))

  }

  initialize()

}
