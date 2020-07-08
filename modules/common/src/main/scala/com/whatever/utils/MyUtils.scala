package com.whatever.utils

trait MyUtils {
  def doStuff: Unit = {
    println("Do stuff!")
    ()
  }
}

object MyUtils extends MyUtils