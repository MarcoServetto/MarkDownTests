package exampleTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExampleTestMarkDown {
  void run(String s){}
/*START
--CHAPTER-- First
--SECTION-- Sec 1
# A tour of Fearless standard library

# Preface
text

--SECTION-- Sec 2
# Hello world
more text
- some bullet points
- some bullet points
- more bullet points

--CHAPTER-- Second
--SECTION-- Sec 1

  can you see here?
OMIT_START
you can not see this
OMIT_END
  has no impact on the semantics of fearless.
--SECTION-- Sec 2
  Assume in folder 'myFolder' we have a file with the following content:  
-------------------------*/@Test void helloWorld() { run("""
      package test
      alias base.Main as Main,
      a<b
      <foo>
      </code>
      end stuff
      //prints Hello, World!
      """); }/*--------------------------------------------

  The code above is a minimal Hello World program.
  - In the first line we declare that our file belongs to the package 'test'.

OMIT_START
you can not see this code
-------------------------*/@Test void helloWorld22() { run("""
      package test
      alias base.Main as MainCAN NOT SEE ME,
      //prints Hello, World!
      """); }/*--------------------------------------------

OMIT_END


END*/
}
