package com.koterpillar.foreshadowing

import org.scalatest.FunSpec

class ForeshadowingTest extends FunSpec {
  describe("Foreshadowing") {
    it("should work in a for comprehension") {
      val foreshadowing: Foreshadowing[Int, Int] = for {
        x <- Certainity[Int, Int](2)
        y <- Certainity[Int, Int](x + 1)
      } yield x * y

      val Certainity(result) = foreshadowing
      assert(result == 6)
    }

    it("should produce a result") {
      val Eventually(result) = for {
        len <- Divination[String, Int](_.length)
      } yield s"This string is $len characters long."

      val withoutDigits = result.filter(!_.isDigit)
      assert(result.filter(_.isDigit).toInt == result.length)

      val removeDigits = "[0-9]+".r
      assert(removeDigits.replaceAllIn(result, "XX") == "This string is XX characters long.")
    }
  }
}
