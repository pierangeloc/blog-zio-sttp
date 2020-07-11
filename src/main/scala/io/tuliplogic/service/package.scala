package io.tuliplogic

import zio.Has

package object service {
  type CustomerBaseService = Has[CustomerBaseService.Service]
}
