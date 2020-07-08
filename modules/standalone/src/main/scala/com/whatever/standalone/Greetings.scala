package com.whatever.standalone

case class Greetings(name: String) {
  def greet: Unit =
    println(s"Hello $name")
}
