package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import models.LogHtml
import models.VillageService
import models.VillageQuery
import models.Village
import java.util.Date
import models.RuruReader

object Edit extends Controller {

  case class VNo(number: Int)

  val noInput = Form(mapping("number" -> number)(VNo.apply)(VNo.unapply))

  val villageInfo = Form(
    mapping(
      "number" -> number,
      "name" -> text,
      "capacity" -> number.verifying(min(4), max(30)),
      "cast" -> text.verifying(maxLength(1)),
      "rc" -> optional(boolean),
      "rs" -> optional(boolean),
      "rl" -> optional(boolean),
      "special" -> optional(text),
      "special2" -> optional(text),
      "endTime" -> text,
      "winner" -> text,
      "comment" -> optional(text),
      "mode" -> text)(Village.apply2)(Village.unapply2))

  def add = Action {
    Ok(views.html.add(Some(null), false, false, 0))
  }

  def addMain = Action { implicit request =>
    val no = noInput.bindFromRequest.get.number
    val vd = VillageService.findById(no)
    if (vd isDefined) {
      Ok(views.html.add(Some(villageInfo.fill(vd.get)), true, true, no))
    } else {
      val v = LogHtml.readLog(no)
      println(v)
      if (v isEmpty) {
        Ok(views.html.add(None, false, true, no))
      } else {
        val vs = v.get
        val z = villageInfo.fill(vs)
        Ok(views.html.add(Some(villageInfo.fill(vs)), false, RuruReader.isDanronName(vs.name), no))
      }
    }
  }

  def adl = Action { implicit request =>
    val v = villageInfo.bindFromRequest.get
    v.mode match {
      case "delete" =>
        VillageService.delete(v.no) > 0
        Ok(views.html.add(Some(null), false, false, 0))
      case "edit" =>
        VillageService.update(v) > 0
        Ok(views.html.add(Some(null), false, false, 0))
      case "add" =>
        VillageService.entry(v)
        Ok(views.html.add(Some(null), false, false, 0))
      case _ => Ok(views.html.add(Some(null), false, false, 0))
    }
  }
}