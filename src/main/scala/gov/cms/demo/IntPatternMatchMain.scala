package gov.cms.demo

object IntPatternMatchMain {

  def main(args: Array[String]): Unit = {
    // convert an Int to English String equivalent
    val two: String = convertIntToString(2)
  }

  def convertIntToString(num: Int): String = {
    num match {
      case 1 => "one"
      case 2 => "two"
      case 3 => "three"
      case 4 => "four"
      case 5 => "five"
      /* Removing the default case, does not throw a non-exhaustive pattern match warning.*/
      /*case _ => "what?"*/
    }
  }
}
