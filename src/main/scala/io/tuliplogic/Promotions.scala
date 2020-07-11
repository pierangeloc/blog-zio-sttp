package io.tuliplogic

import zio.{App, ExitCode, UIO, URIO}

object Promotions extends App {

  def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    UIO(()).exitCode
}



