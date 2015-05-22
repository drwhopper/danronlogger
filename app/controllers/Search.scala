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

object Search extends Controller {

  val searchForm = Form(
    mapping(
      "capacityFrom" -> optional(number verifying (min(4), max(30))),
      "capacityTo" -> optional(number verifying (min(4), max(30))),
      "cast" -> optional(text verifying maxLength(1)),
      "rc" -> optional(boolean),
      "rs" -> optional(boolean),
      "rl" -> optional(boolean),
      "winner" -> optional(text),
      "special" -> optional(text),
      "page" -> optional(number),
      "y" -> optional(number),
      "m" -> optional(number),
      "on" -> optional(boolean))(
        (capacityFrom, capacityTo, cast, rc, rs, rl, winner, special, page, y, m, on) =>
          VillageQuery(capacityFrom, capacityTo, cast, rc, Some(rs getOrElse false),
            Some(rl getOrElse false), winner, special, page, y, m, on))(VillageQuery.unapply))

  val pager: Int = 40

  def fit(villages: Seq[Village], page: Int) = {
    //println(villages.size)
    val limitedVillages: Seq[Village] = if (villages.size < pager * (page - 1)) Seq.empty else
      villages.slice(pager * (page - 1), math.min(villages.size, pager * page))
    val maxPage: Int = (villages.size - 1) / pager
    // (VillageService.findTogami ++: limitedVillages, maxPage + 1, villages.size)
    (limitedVillages, maxPage + 1, villages.size)
  }

  def pageCut(url: String) = {
    val purl = url.replaceFirst("\\&?page=[0-9]+", "")
    purl + (if (!purl.contains('?') && purl.last != '?') '?' else '&')
  }

  def index = Action { implicit request =>
    val query = searchForm.bindFromRequest.get
    val now = query.page.getOrElse(1)
    val f = fit(VillageService.find(query), now)
    Ok(views.html.search(f._1, f._2, f._3, searchForm.fill(query), pageCut(request.uri), now))
  }

  def default12b = Action { implicit request =>
    val query = VillageQuery(Some(12), None, Some("B"), Some(false), Some(false), Some(false), None, Some("false"), None, None, None, Some(true))
    val now = searchForm.bindFromRequest.get.page.getOrElse(1)
    val f = fit(VillageService.find(query), now)
    Ok(views.html.search(f._1, f._2, f._3, searchForm.fill(query), pageCut(request.uri), now))
  }

  def default17a = Action { implicit request =>
    val query = VillageQuery(Some(17), None, Some("A"), Some(false), Some(false), Some(false), None, Some("false"), None, None, None, Some(true))
    val now = searchForm.bindFromRequest.get.page.getOrElse(1)
    val f = fit(VillageService.find(query), now)
    Ok(views.html.search(f._1, f._2, f._3, searchForm.fill(query), pageCut(request.uri), now))
  }

  def default18a = Action { implicit request =>
    val query = VillageQuery(Some(18), None, Some("A"), Some(true), Some(false), Some(false), None, Some("false"), None, None, None, Some(true))
    val now = searchForm.bindFromRequest.get.page.getOrElse(1)
    val f = fit(VillageService.find(query), now)
    Ok(views.html.search(f._1, f._2, f._3, searchForm.fill(query), pageCut(request.uri), now))
  }

  def default19d = Action { implicit request =>
    val query = VillageQuery(Some(19), None, Some("D"), Some(true), Some(false), Some(false), None, Some("false"), None, None, None, Some(true))
    val now = searchForm.bindFromRequest.get.page.getOrElse(1)
    val f = fit(VillageService.find(query), now)
    Ok(views.html.search(f._1, f._2, f._3, searchForm.fill(query), pageCut(request.uri), now))
  }

  def defaultMany = Action { implicit request =>
    val query = VillageQuery(Some(20), Some(30), None, None, None, None, None, None, None, None, None, Some(true))
    val now = searchForm.bindFromRequest.get.page.getOrElse(1)
    val f = fit(VillageService.find(query), now)
    Ok(views.html.search(f._1, f._2, f._3, searchForm.fill(query), pageCut(request.uri), now))
  }

  def defaultBeginner = Action { implicit request =>
    val query = VillageQuery(None, None, None, None, None, None, None, Some("初心者"), None, None, None, Some(true))
    val now = searchForm.bindFromRequest.get.page.getOrElse(1)
    val f = fit(VillageService.find(query), now)
    Ok(views.html.search(f._1, f._2, f._3, searchForm.fill(query), pageCut(request.uri), now))
  }

  def defaultDeserted = Action { implicit request =>
    val f = fit(VillageService.findDeserted, searchForm.bindFromRequest.get.page.getOrElse(1))
    Ok(views.html.search(f._1, f._2, f._3, searchForm, pageCut(request.uri), searchForm.bindFromRequest.get.page.getOrElse(1)))
  }

  def defaultSpecial = Action { implicit request =>
    val query = VillageQuery(None, None, None, None, None, None, None, Some("（特殊全て）"), None, None, None, Some(true))
    val f = fit(VillageService.findSpecial, searchForm.bindFromRequest.get.page.getOrElse(1))
    Ok(views.html.search(f._1, f._2, f._3, searchForm.fill(query), pageCut(request.uri), searchForm.bindFromRequest.get.page.getOrElse(1)))
  }

  def defaultAll = Action { implicit request =>
    val f = fit(VillageService.findAll, searchForm.bindFromRequest.get.page.getOrElse(1))
    Ok(views.html.search(f._1, f._2, f._3, searchForm, pageCut(request.uri), searchForm.bindFromRequest.get.page.getOrElse(1)))
  }
}