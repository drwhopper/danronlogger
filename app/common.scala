
import scala.concurrent.duration.DurationInt

import akka.actor.Props
import play.api._
import play.api.Play.current
import play.api.libs.concurrent.Akka
import play.api.libs.concurrent.Execution.Implicits._
import services.GetJsonService

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    val actor = Akka.system.actorOf(Props(new GetJsonService), "get")
    Akka.system.scheduler.schedule(0 seconds, 8 minutes, actor, "")
  }
  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }
}