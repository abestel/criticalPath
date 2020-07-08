package com.whatever.service

case object MyServiceImpl extends MyService {
  override def doSomething(input: Int): String = {
    doStuff
    input.toString
  }
}
