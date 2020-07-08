package com.whatever.app

import com.whatever.service.MyServiceImpl
import com.whatever.standalone.Greetings

object MyApp extends App {
  private val greetings = Greetings("World")
  greetings.greet

  private val service = MyServiceImpl
  service.doSomething(123)
}
