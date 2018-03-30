package gov.cms.demo

import java.io.{FileNotFoundException, IOException}

import scala.io.Source
import org.log4s.Logger

object IncompletePatMatsMain {

  val logger: Logger = org.log4s.getLogger

  final val INPUT_FILE: String = "resources/sample-file.txt"

  def main(args: Array[String]): Unit = {

    logger.info("Demoing incomplete pattern matches.")

    // Demo pattern matches in try/catch expressions
    tryCatchPatMat(INPUT_FILE)
  }

  def tryCatchPatMat(filename: String): Unit = {

    try {

      val lines: List[String] = Source.fromFile(filename).getLines.toList

    } catch {
      case ioe: IOException =>
        logger.error(ioe)
        None
      case fnf: FileNotFoundException =>
        logger.error(fnf)
        None
    }
  }

}
