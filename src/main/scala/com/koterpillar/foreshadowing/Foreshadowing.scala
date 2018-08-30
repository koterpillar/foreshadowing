package com.koterpillar.foreshadowing

sealed trait Foreshadowing[Result, A] {
  def result(result: Result): A

  def map[B](fn: A => B): Foreshadowing[Result, B]
  def flatMap[B](fn: A => Foreshadowing[Result, B]): Foreshadowing[Result, B]
}

case class Certainty[Result, A](value: A) extends Foreshadowing[Result, A] {
  def result(result: Result): A = value

  def map[B](fn: A => B): Foreshadowing[Result, B] = Certainty(fn(value))
  def flatMap[B](fn: A => Foreshadowing[Result, B]): Foreshadowing[Result, B] =
    fn(value)
}

case class Divination[Result, A](fn: Result => A)
    extends Foreshadowing[Result, A] {
  def result(result: Result): A = fn(result)

  def map[B](fn: A => B): Foreshadowing[Result, B] =
    Divination(result => fn(this.fn(result)))
  def flatMap[B](fn: A => Foreshadowing[Result, B]): Foreshadowing[Result, B] =
    Divination { result =>
      fn(this.fn(result)) match {
        case Certainty(value)  => value
        case Divination(nextFn) => nextFn(result)
      }
    }
}
