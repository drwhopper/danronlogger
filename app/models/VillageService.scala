package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import org.postgresql.util.PSQLException
import anorm.NamedParameter.symbol
import java.text.SimpleDateFormat
import java.util.Date

object VillageService {

  private val * = {
    int("no") ~ str("name") ~ int("capacity") ~ str("vcast") ~
      bool("rule_cat") ~ bool("rule_sp") ~ bool("rule_lunatic") ~
      str("special") ~ str("createTime") ~ str("winner") ~ str("comment") map {
        case no ~ name ~ capacity ~ vcast ~ rule_cat ~ rule_sp ~ rule_lunatic ~ special ~ createTime ~ winner ~ comment =>
          Village(no, name, capacity, vcast charAt 0, rule_cat, rule_sp, rule_lunatic, special, createTime, winner, comment, "")
      }
  }

  def findById(no: Int): Option[Village] = {
    DB.withConnection(implicit c =>
      SQL("select * from Villages where no = {no}").on('no -> no).as(*.singleOpt))
  }

  def findAll: Seq[Village] = {
    DB.withConnection(implicit c =>
      SQL("select * from Villages order by no desc ").as(*.*))
  }

  def find(s: VillageQuery): Seq[Village] = {
    val query = s.unapplyMap
    val v = query.collect {
      case s if (s._2.isDefined) => {
        s._1 match {
          case 'order => ""
          case 'year => "createTime like '" + s._2.get +
            (if (query.get('month).get isDefined) { "/" + ("%02d" format query.get('month).get.get) } else "") + "%'"
          case 'month => if (query.get('year).get isEmpty) { "createTime like '" + ("%tY" format new Date) + "/" + ("%02d" format s._2.get) + "%'" }
          else ""
          case 'capacityFrom => "capacity" +
            (if (query.get('capacityTo).get isDefined) " >= " else " = ") + s._2.get
          case 'capacityTo => "capacity" + " <= " + s._2.get
          case 'special => s._2.get match {
            case "false" => "(special = '' or special = '初心者')"
            case "（特殊全て）" => "special <> '' and special <> '初心者'"
            case _ => s._1.name + " = '" + s._2.get + '\''
          }
          case _ => s._2.get match {
            case s2: String => s._1.name + " = '" + s._2.get + '\''
            case _ => s._1.name + " = " + s._2.get
          }
        }
      }
    }
    val cond = v.filterNot(_ == "").mkString(" and ")
    val order = if (query.get('order).get.get == false) "" else " desc"
    // println(cond)
    DB.withConnection(implicit c =>
      SQL("select * from Villages where winner != '開始前廃村' and " + cond + " order by no" + order).as(*.*))
  }
  
  def findTogami: Seq[Village] = {
    DB.withConnection(implicit c =>
      SQL("select * from Villages where no = '263009'").as(*.*))
  }

  def findSpecial: Seq[Village] = {
    DB.withConnection(implicit c =>
      SQL("select * from Villages where winner != '開始前廃村' and special <> '' and special <> '初心者' order by no desc").as(*.*))
  }

  def findDeserted: Seq[Village] = {
    DB.withConnection(implicit c =>
      SQL("select * from Villages where winner = '開始前廃村' order by no desc").as(*.*))
  }

  def entry(v: Village): Boolean = {
    DB.withConnection { implicit c =>
      SQL(""" insert into Villages values({no},{name},{capacity},{vcast},{rule_cat},{rule_sp},{rule_lunatic},{special},{createTime},{winner},{comment})""")
        .on('no -> v.no, 'name -> v.name, 'capacity -> v.capacity, 'vcast -> v.cast, 'rule_cat -> v.rule_cat, 'rule_sp -> v.rule_sp,
          'rule_lunatic -> v.rule_lunatic, 'special -> v.special, 'createTime -> v.endTime, 'winner -> v.winner, 'comment -> v.comment).execute
    }
  }

  def update(v: Village) = {
    DB.withConnection { implicit c =>
      SQL(""" update Villages set name = {name}, capacity = {capacity},vcast = {vcast}, rule_cat = {rule_cat},rule_sp = {rule_sp}, rule_lunatic = {rule_lunatic}, special = {special}, createTime = {createTime},winner = {winner}, comment = {comment} where no = {no}""")
        .on('no -> v.no, 'name -> v.name, 'capacity -> v.capacity, 'vcast -> v.cast, 'rule_cat -> v.rule_cat, 'rule_sp -> v.rule_sp,
          'rule_lunatic -> v.rule_lunatic, 'special -> v.special, 'createTime -> v.endTime, 'winner -> v.winner, 'comment -> v.comment).executeUpdate
    }
  }

  def delete(no: Int) = {
    DB.withConnection(implicit c =>
      SQL("delete from Villages where no = {no}").on('no -> no).executeUpdate)
  }

}