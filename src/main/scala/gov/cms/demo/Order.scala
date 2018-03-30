package gov.cms.demo

object Order {

  sealed trait EntryOption

  case object EmptyEntry extends EntryOption

  trait Entry extends EntryOption

  def isEmpty(entryOption: EntryOption): Boolean = {

    entryOption match {

      case EmptyEntry =>
        true

      /* Removing the default case causes a non-exhaustive pattern matching warning
      case _: Entry =>
        false
      */
    }

  }
}
