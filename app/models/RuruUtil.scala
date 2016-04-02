package models

import scala.io.Source

object RuruUtil {

  def ruruUrl = "http://ruru-jinro.net/"

  def toSource(villageID: Int) = {
    Source.fromURL(toURL(villageID))("UTF-8")
  }

  def toURL(villageID: Int) = {
    ruruUrl + logX(villageID) + "/log" + villageID + ".html"
  }

  def toWatcherURL(villageID: Int, comment: String) = {
    if (comment.contains("#url=")) {
      comment.substring(comment.indexOf("#url=") + 5)
    } else {
      ruruUrl + logX(villageID) + "/log" + villageID + "_n.html"
    }
  }

  def toURLconsideringComment(villageID: Int, comment: String) = {
    if (comment.contains("#url=")) {
      comment.substring(comment.indexOf("#url=") + 5)
    } else {
      toURL(villageID)
    }
  }

  def logX(villageID: Int) = {
    val modLog = villageID / 100000
    if (modLog > 0) "log" + (modLog + 1) else "log"
  }

  def commentWihoutURL(comment: String) = {
    if (comment.contains("#url=")) {
      comment.substring(0, comment.indexOf("#url="))
    } else {
      comment
    }
  }

}