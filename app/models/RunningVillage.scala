package models

import scala.collection.mutable.Map

object RunningVillage {
  val bRun = Map.empty[Int, Int]

  def check(run: List[Int]) = {

   // println("INPUT")
    run.foreach(v => print(v))

   // println("BEFORE")
    bRun.foreach(v => print(v))

    val fin = bRun.filterNot(v => run.contains(v._1))
   // println("FILTERNOT")
    fin.foreach(v => {
      val log = LogHtml.readLog(v._1)
      if (log.isDefined) {
        VillageService.entry(log.get)
        bRun.remove(v._1)
      } else {
        bRun.put(v._1, bRun.get(v._1).getOrElse(0) + 1)
      }
    })
    bRun.retain((k, v) => v < 4)
    run.foreach(v => bRun.put(v, 1))

   // println("AFTER")
    bRun.foreach(v => print(v))
  }
}