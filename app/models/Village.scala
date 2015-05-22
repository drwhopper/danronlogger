package models

import java.text.SimpleDateFormat

case class Village(no: Int, name: String, capacity: Int, cast: Char,
  rule_cat: Boolean, rule_sp: Boolean, rule_lunatic: Boolean, special: String,
  endTime: String, winner: String, comment: String, mode: String)

object Village {
  def apply2(no: Int, name: String, capacity: Int, cast: String,
    rule_cat: Option[Boolean], rule_sp: Option[Boolean], rule_lunatic: Option[Boolean],
    special: Option[String], special2: Option[String],
    endTime: String, winner: String, comment: Option[String], mode: String) = {
    Village(no, name, capacity, cast.head, rule_cat.getOrElse(false), rule_sp.getOrElse(false), rule_lunatic.getOrElse(false),
      sp(special, special2), endTime, winner, comment.getOrElse(""), mode)
  }
  def unapply2(v: Village) = {
    Some((v.no, v.name, v.capacity, v.cast.toString, Some(v.rule_cat), Some(v.rule_sp), Some(v.rule_lunatic),
      Some(v.special), None, v.endTime, v.winner, Some(v.comment), ""))
  }

  def sp(special: Option[String], special2: Option[String]) = {
    if (special.getOrElse("") == "その他") special2.getOrElse("") else special.getOrElse("")
  }
}
