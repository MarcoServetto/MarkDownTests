package tour;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ZH_001Preface {
  void run(String s){}
/*START
--CHAPTER-- Preface
--SECTION-- Fearless: Zero to Hero

# Fearless: Zero to Hero

### Preface: What is Fearless?
Fearless is a minimalist, nominally-typed, object-oriented
programming language.
Fearless gives the programmer fine control of side effects
using Reference and Object Capabilities instead of an Effect System.

If you are new to programming, the sentence above was probably a
little difficult to understand.

In this guide we are going to take a step by step approach
to Fearless programming.
Instead of focusing on writing short working programs,
we are going to focus on the basics of programming as a way to organize
concepts and thought.

### Fearless: code, compilation, and execution

A Fearless program consists of a specific form of text, called code.
We use parenthesis `(..)`, `[..]` and `{..}` to group
things together.
Those three kinds of parenthesis have different roles.
We will demonstrate these roles at a later stage.

Fearless source code is text designed to be read by both humans
and a program called the Fearless compiler.

From the point of view of the fearless compiler
newlines and spaces are irrelevant; any amount of spacing is equally effective at
separating pieces of code.
This means that we can freely use more or less spacing to make
the code more understandable for humans.
Next we will see our first example of code.


OMIT_START
-------------------------*/@Test void helloWorldPreface() { run("""
      package test
      alias base.Main as Main,
      Test:Main {sys -> base.Debug.println("Hello, World!")}//OK
      //prints Hello, World!
      /*single and multiline comments exits*/
      """); }/*--------------------------------------------

OMIT_END

END*/
}
