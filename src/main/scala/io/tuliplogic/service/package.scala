package io.tuliplogic


import zio.Has

package object service {
  type CustomerDataService = Has[CustomerDataService.Service]
  type Config = Has[io.tuliplogic.Config]

}
