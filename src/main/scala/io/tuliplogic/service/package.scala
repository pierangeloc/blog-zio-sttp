package io.tuliplogic


import zio.Has

package object service {
  type CustomerBaseService = Has[CustomerBaseService.Service]
  type Config = Has[io.tuliplogic.Config]

}
