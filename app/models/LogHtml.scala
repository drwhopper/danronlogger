package models

import java.io.StringReader
import java.nio.file.Files
import java.nio.file.Paths
import scala.collection.JavaConversions.asScalaBuffer
import scala.xml.Node
import scala.xml.parsing.NoBindingFactoryAdapter
import org.xml.sax.InputSource
import nu.validator.htmlparser.common.XmlViolationPolicy
import nu.validator.htmlparser.sax.HtmlParser
import scala.xml.Text
import scala.io.Source
import java.net.URL
import scala.xml.NodeSeq
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import sun.util.resources.CalendarData
import scala.util.matching.Regex

object LogHtml {

  def readLog(id: Int) = {
    // println(id)
    val bf = new StringBuffer
    val rx: Regex = "\\p{Cc}".r
    for (line <- RuruUtil.toSource(id).getLines) bf.append(rx.replaceAllIn(line, ""))
    val nodes = toNode(bf.toString)
    //println(nodes \\ "title")
    if (nodes != null && ((nodes \\ "title").text startsWith "No.")) {
      val odiv = nodes \ "body" \ "div" filter (_ \ "@class" contains Text("d1"))
      val day = dayInfo(odiv \\ "div" filter (_ \ "@class" contains Text("d12150")))
      val winner = result(day._1) {
        odiv \\ "span" filter (_ \ "@class" contains Text("result"))
      }
      val vi = villageInfo(odiv \ "div" filter (_ \ "@class" contains Text("d11")))
      Some(Village(id, vi._1, vi._2, vi._3, vi._4, vi._5, vi._6, Special.special(vi._1), day._2, winner, "", "read"))
    } else None
  }

  def result(rTime: Int)(result: NodeSeq): String = {
    if (result isEmpty) {
      if (rTime == 0) "開始前廃村" else "廃村"
    } else {
      val rspan = result \ "span"
      if (rspan isEmpty) "引分" else (rspan.text.diff("　"))
    }
  }

  def dayInfo(dinfo: NodeSeq): (Int, String) = {
    //println(dinfo.text)
    val end = (dinfo filter (_.text contains "ゲーム終了")).text
    if (end.length() > 20) {
      val rTime = Integer.parseInt(end.charAt(end.indexOf("ゲーム時間") + 6).toString)
      val endDate = end.substring(0, 16)
      (rTime, endDate)
    } else {
      val day = Integer.parseInt(end.substring(0, end indexOf '日'))
      val endDate = "1900/01/01 00:00"
      (day, endDate)
    }
  }

  def villageInfo(vinfo: NodeSeq) = {
    val info = vinfo.text.split('\u00a0')
    val capacity = Integer.parseInt(info(3).substring(3, info(3).length() - 1))
    ((vinfo \ "span" filter (_ \ "@class" contains Text("big"))).text diff "「」", capacity,
      info(5) charAt 3, info(5) contains "猫1", info(9) contains "強化", info(9) contains "聴狂")
  }

  def toNode(str: String): Node = {
    val hp = new HtmlParser
    hp.setNamePolicy(XmlViolationPolicy.ALLOW)

    try {
      val saxer = new NoBindingFactoryAdapter
      hp.setContentHandler(saxer)
      hp.parse(new InputSource(new StringReader(str)))

      saxer.rootElem
    } catch {

      case e: Throwable => {
        println(e)
        return null
      }
    }
  }
}