package services

import akka.actor.Actor
import models.RuruReader
import scala.io.Source

class GetJsonService extends Actor {

  def receive: Actor.Receive = {
    case _ => {
      RuruReader.getInfo
    }
  }
}