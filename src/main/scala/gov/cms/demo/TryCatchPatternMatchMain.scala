package gov.cms.demo

import java.io.{FileNotFoundException, IOException}

import scala.io.Source
import org.log4s.Logger

object TryCatchPatternMatchMain {

  val logger: Logger = org.log4s.getLogger

  final val INPUT_FILE: String = "resources/sample-file.txt"

  def main(args: Array[String]): Unit = {
    // Demo pattern matches in try/catch expressions
    tryCatchPatMat(INPUT_FILE)
  }

  def tryCatchPatMat(filename: String): Unit = {

    try {

      val lines: List[String] = Source.fromFile(filename).getLines.toList

    } catch {

      case ioe: IOException =>
        logger.error(ioe.toString)
        None

      case fnf: FileNotFoundException =>
        logger.error(fnf.toString)
        None

      /* Removing the default case does not throw a non-exhaustive pattern match warning.*/
      /*case _ =>
        logger.error("General IO error.")
        None*/
    }
  }

}
