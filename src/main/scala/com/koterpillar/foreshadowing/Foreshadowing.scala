package com.koterpillar.foreshadowing

sealed trait Foreshadowing[Result, A] {
  def result(result: Result): A

  def map[B](fn: A => B): Foreshadowing[Result, B]
  def flatMap[B](fn: A => Foreshadowing[Result, B]): Foreshadowing[Result, B]
}

case class Divination[Result, A](fn: Result => A)
    extends Foreshadowing[Result, A] {
  def result(result: Result): A = fn(result)

  def map[B](fn: A => B): Foreshadowing[Result, B] =
    Divination(result => fn(this.fn(result)))
  def flatMap[B](fn: A => Foreshadowing[Result, B]): Foreshadowing[Result, B] =
    Divination { result =>
      fn(this.fn(result)) match {
        case Divination(nextFn) => nextFn(result)
      }
    }
}
