package models

import java.text.SimpleDateFormat
import java.util.Date
import scala.collection.mutable.Map
import scala.io.Codec.string2codec
import scala.io.Source
import play.api.libs.json.JsValue
import play.api.libs.json.Json

object RuruReader {
  /*
   * ここをいじると取得する村の種類がかわる
   */
  implicit def jsonIsDanronVillege(s: JsValue) = new {
    def isDanronVillage() = {
      val vname = (s \ "name").as[String];
      !(s \ "rule_family").as[Boolean] && isDanronName(vname)
    }
  }

  def isDanronName(vname: String) = {
    vname.contains("ダン") &&
      vname.contains("ロン") &&
      !vname.contains("ミリしら") &&
      !vname.contains("知ら")
  }

  def getInfo {
    val source = Source.fromURL("http://werewolf.ddo.jp/resource/vinfo/en.json")("UTF-8").mkString
    val json = Json.parse(source).as[List[JsValue]]
    RunningVillage.check(json.collect {
      case s if s.isDanronVillage => {
        ((s \ "no").as[Int])
      }
    })
  }

}