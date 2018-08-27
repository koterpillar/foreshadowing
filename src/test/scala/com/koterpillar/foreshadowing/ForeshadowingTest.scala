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
      def isVowel(c: Char) = c match {
        case 'a' | 'e' | 'i' | 'o' | 'u' => true
        case _                           => false
      }

      val Eventually(result) = for {
        len    <- Divination[String, Int](_.length)
        vowels <- Divination[String, Int](_.count(isVowel))
      } yield s"This string is $len characters long. It has $vowels vowels."

      val digits = "[0-9]+".r
      assert(
        digits.replaceAllIn(result, "XX") == "This string is XX characters long. It has XX vowels.")

      val Seq(length, vowels) = digits.findAllIn(result).toSeq

      assert(length.toInt == result.length)
      assert(vowels.toInt == result.count(isVowel))
    }
  }
}
