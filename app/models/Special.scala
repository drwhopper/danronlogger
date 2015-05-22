package models

import scala.collection.mutable.LinkedHashMap

object Special {

  val specialVillage = LinkedHashMap(
    0 -> "",
    //戦歴縛り系
    1 -> "初心者",
    //お題系
    5 -> "グレラン",
    6 -> "アイランド",
    7 -> "グレランアイランド",
    8 -> "NGワード",
    //特殊能力系
    10 -> "オシオキ",
    11 -> "オークション",
    //お祭り系
    15 -> "ジョイ君方式",
    16 -> "ワンナイト",
    17 -> "シックスセンス",
    18 -> "グレ恋",
    19 -> "相思相愛",
    20 -> "グレ恋相思相愛",
    21 -> "ダイス")

  def special(v: String): String = {
    specialVillage(v match {
      case a if (a contains "初心者") && !(a contains "歓迎") => 1
      case a if (a contains "グレラン") => if (a contains "アイランド") 7 else 5
      case a if (a contains "アイランド") => 6
      case a if (a contains "NGワード") => 8
      case a if (a contains "オシオキ") => 10
      case a if (a contains "オークション") => 11
      case a if (a contains "ジョイ") => 15
      case a if (a contains "ワンナイト") => 16
      case a if (a contains "シックスセンス") => 17
      case a if (a contains "相思相愛") => if (a contains "グレ") 19 else 20
      case a if (a contains "グレ恋") => 18
      case a if (a contains "ダイス") => 21
      case _ => 0
    })
  }
}