package org.dedkov.research.scala

import org.dedkov.research.scala.ParameterizedTraitTestApp.MyObj.{IntToString, IntToUnit}

/**
  * Created by dedkov-va on 2016-07-01.
  */
object ParameterizedTraitTestApp extends App {
  trait MyTrait[T] {
    def someMethod: T
  }

  object MyObj {
    type IntToUnit = Int => Unit
    type IntToString = Int => String
  }



  /*  Warning:(23, 7) a pure expression does nothing in statement position; you may be omitting necessary parentheses
      "s"
      ^*/
  trait MyTrait01 extends MyTrait[IntToUnit] {
    override def someMethod = { i =>
      i + 1
      "s"
    }
  }

  /*  Warning:(23, 7) a pure expression does nothing in statement position; you may be omitting necessary parentheses
    "s"
    ^*/
  trait MyTrait02 extends MyTrait[IntToUnit] {
    override def someMethod: IntToUnit = { i =>
      i + 1
      "s"
    }
  }

  //Error:(34, 18) overriding method someMethod in trait MyTrait of type => MyObj.IntToUnit;
  //method someMethod has incompatible type
  //override def someMethod: IntToString = { i =>
  //         ^
/*  trait MyTrait03 extends MyTrait[IntToUnit] {
    override def someMethod: IntToString = { i =>
      i + 1
      "s"
    }
  }*/

  trait MyTrait04 extends MyTrait[IntToString] {
    override def someMethod: IntToString = { i =>
      i + 1
      "s"
    }
  }

  //not compiled
/*  Error:(55, 14) type mismatch;
  found   : String("s")
  required: MyObj.IntToString
  (which expands to)  Int => String
  return "s"
       ^*/
/*  trait MyTrait05 extends MyTrait[IntToString] {
    override def someMethod: IntToString = { i =>
      i + 1
      return "s"
    }
  }*/

  trait MyTrait06 extends MyTrait[IntToString] {
    override def someMethod: IntToString = { i =>
      i + 1
      "s"
    }
  }

  trait MyTrait07 extends MyTrait[IntToString] {
    override def someMethod(): IntToString = { i =>
      i + 1
      "s"
    }
  }

  trait MyTrait08 extends MyTrait[IntToString] {
    override def someMethod: IntToString = { return { i =>
        i + 1
        "s"
      }
    }
  }



  object MainObj01 extends MyTrait01 {
    def printSomeMethod() = {
      println(this.getClass.getSimpleName + ": " + someMethod(1))
    }
  }

  object MainObj02 extends MyTrait02 {
    def printSomeMethod() = {
      println(this.getClass.getSimpleName + ": " + someMethod(1))
    }
  }

  object MainObj04 extends MyTrait04 {
    def printSomeMethod() = {
      println(this.getClass.getSimpleName + ": " + someMethod(1))
    }
  }

  object MainObj06 extends MyTrait06 {
    def printSomeMethod() = {
      println(this.getClass.getSimpleName + ": " + someMethod(1))
    }
  }

  object MainObj07 extends MyTrait07 {
    def printSomeMethod() = {
      println(this.getClass.getSimpleName + ": " + someMethod()(1))
    }
  }

  object MainObj08 extends MyTrait08 {
    def printSomeMethod() = {
      println(this.getClass.getSimpleName + ": " + someMethod(1))
    }
  }



  MainObj01.printSomeMethod()
  MainObj02.printSomeMethod()
  MainObj04.printSomeMethod()
  MainObj06.printSomeMethod()
  MainObj07.printSomeMethod()
  MainObj08.printSomeMethod()


  /*
  MainObj01$: ()
  MainObj02$: ()
  MainObj04$: s
  MainObj06$: s
  MainObj07$: s
  MainObj08$: s*/
}
