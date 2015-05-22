package models

case class VillageQuery(
  capacityFrom: Option[Int], capacityTo: Option[Int], cast: Option[String],
  rule_cat: Option[Boolean], rule_sp: Option[Boolean], rule_lunatic: Option[Boolean],
  winner: Option[String], special: Option[String],
  page: Option[Int], year: Option[Int], month: Option[Int], order: Option[Boolean]) {

  def unapplyMap() = {
    Map('capacityFrom -> capacityFrom, 'capacityTo -> capacityTo, 'vcast -> cast,
      'rule_cat -> rule_cat, 'rule_sp -> rule_sp, 'rule_lunatic -> rule_lunatic,
      'winner -> winner, 'special -> special, 'year -> year, 'month -> month, 'order -> order)
  }

}
 
