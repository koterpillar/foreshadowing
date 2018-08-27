package com.koterpillar.foreshadowing

import scala.annotation.tailrec

object Eventually {
  @tailrec
  def unapply[Result](result: Foreshadowing[Result, Result])(implicit guess: Guess[Result]): Option[Result] = {
    val currentResult = guess.value
    val nextResult = result.result(currentResult)
    if (currentResult == nextResult)
      Some(nextResult)
    else
      unapply(result)(Guess(nextResult))
  }
}
