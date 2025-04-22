package exTourExample;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ZH_003Chapter01Tanks {
  void run(String s){}
/*START
--CHAPTER-- Chapter 1
--SECTION-- Basic code reuse

### How to Avoid Repetitive Code in Fearless
You may already have noticed  that even relatively simple programs
have the potential to be very long.
Programs which  you may use daily (such as Steam, Zoom, TikTok, and Instagram)
are composed of millions of lines of code. Some programming languages
force the user to write very repetitive code; either by literally
repeating the same text over and over again, or by repeating similar but
slightly different code. Repetitive code is bad and Fearless has ways to
avoid repeated code and promote code reuse.

We have already seen three ways fearless avoids repetitive and redundant code.

- One way is simply defining and calling methods: by calling methods we can
avoid repeating their body over and over again. Since methods can call other
methods, this can produce a massive reduction in code.
- Another way is to implement existing types and to inherit their methods.
This imports/reuses all of the methods from the implemented types without
mentioning them one by one.
In the `Direction` example, `.reverse` is implicitly and automatically
inherited by all of the directions.
- We have also seen inference; where we can omit type informations that
are clear from the context. For example, when implementing method `Direction.turn` in 
`North` we could write `.turn->East` instead of `.turn:Direction->East`.
The return type `Direction` is clear from the context.

We now introduce the concept of 
**syntactic sugar**.
This additional concept is like method calls, method inheritance and
type inference because it
is designed to avoid redundant code;
Syntactic sugar allows representing specific well known coding patterns
using more concise and more readable syntax.

We will now see how a combination of syntactic sugar and inference can make the
code for `Direction` even more compact.
The only abstract method in `Direction` is `.turn`,
so when implementing direction it is obvious that we want to implement `.turn`.
In this way, the syntactic sugar allows us to write the following,
shorter version of the code we have seen before.
We can omit `.turn->` when implementing a single method
to satisfy `Direction`, as shown below.
`````
Direction: {
  .turn: Direction,
  .reverse: Direction ->this.turn.turn,
  }
North: Direction {East}
East : Direction {South}
South: Direction {West}
West : Direction {North}
`````

### Understanding the code above

 1. `North: Direction {.turn->East,}` is a type declaration.
 2. `North: Direction {East}` is a more compact form of the
same type declaration.
 3. `.turn:Direction` is a method declaration.
 4. `.turn->East,` is a method implementation.
 5. `East` is an example of an object literal expression.
 6. `North.turn` is an example of a method call expression.
 7. `this` is an example of a parameter, the third kind of expression.
 8.  `North` and `East` are valid objects because they have
no abstract types.
 9. `Direction` denotes an abstract type and thus is not a valid object.

We will later see that both method calls and object literals
can be much more involved than the ones shown in our examples so far.

### Object literal: inheritance plus syntactic sugar
Object literals work as shortcuts to avoid repeating the full code
they represent.
Using literals as names referring to a type declaration is another way
to reuse code. When writing `North` we are making heavy use of syntactic sugar.
`North` actually stands for `SomeName147:North{}`, where `SomeName147` is
some name chosen by the syntactic sugar to be different from any other name
present anywhere else in the code.

Thus, when writing `North` we are talking about a version of `North`,
called for example `SomeName147` that inherits all of the methods of `North`
and does not add any new methods.

While object literals can simply be used as names referring to
type declarations,
they can be any kind of type declarations without abstract methods.

### Example: Tank with turret
Now that we have the abstract type `Direction` we can make a simple
`Tank` object. This tank will have two Directions;
- `.heading`: the direction the tank is moving, and
- `.aiming`: the direction the tank gun is aiming.

`````
Tank: {
  .heading: Direction,
  .aiming: Direction,
  }
TankNN: Tank{.heading-> North, .aiming-> North,}
TankNE: Tank{.heading-> North, .aiming-> East, }
TankNS: Tank{.heading-> North, .aiming-> South,}
TankNW: Tank{.heading-> North, .aiming-> West, }
TankEN: Tank{.heading-> East,  .aiming-> North,}
...//16 cases in total!
TankWW: Tank{.heading-> West,  .aiming-> West, }
`````
As you can see, while it is possible to manually list all sixteen cases
as valid Fearless code, the development process is boring,
repetitive and error prone.
Can we somehow take two `Direction` objects and produce a `Tank` object? Yes.

Instead of writing the verbose code above, we can declare a type `Tanks`
that makes tank objects. This idea of having a type that makes an instance
of another type is quite common in Fearless, and we conventionally call those
types with the name of the created type but ending with 's' to represent
'plurality'.
We could use other conventions, like `MakeTank`;
but we prefer `Tanks` because it is more concise and does not restrict
its role to just make instances, as we will see later.
`````
Tanks: { .of(heading: Direction, aiming: Direction): Tank->
  Tank: {.heading: Direction->heading, .aiming: Direction->aiming,}
  }
`````
As you can see, we have moved the declaration for `Tank` inside
of a method body. The syntax `heading: Direction` defines `heading`
as a parameter of the method `.of`.
That is, while the method parameter `this` is implicitly declared
by any method, other method parameters are explicitly declared.

- `heading: Direction` is a **parameter declaration**.
- `heading` is an example of an explicitly defined **parameter**.

### Method Declaration Syntax 
Methods can have as many parameters as we want.
The first implicit parameter is the value before the method name,
while the other are provided after the method name.
Syntactically, explicit method parameters are defined inside
of round brackets.
When there are no explicit parameters, 
these brackets can be optionally omitted / left out.
For example, the methods `.turn` and `.reverse` take no explicit parameters,
so before we omitted the parenthesis.
These same methods could equivalently be declared as `.turn()` and `.reverse()`.
To call the method `.turn` twice we showed the syntax `North.turn.turn`
but we could have equivalently called it with syntax 
`North.turn().turn()`.
The syntactic sugar of Fearless allows us to include or omit most
empty brackets.

The syntax `.of(heading: Direction, aiming: Direction): Tank`
defines a method called `.of` with parameters `heading` and `aiming`.
The parameter type must be a `Direction` and the method must return
a `Tank` object. Parameters allow methods to take input.
In this case, our `Tanks.of` method requires us (the developer) to specify
the initial `heading` and `aiming` of the tank we want to create.

### English to Fearless Conversion
Fearless code can be written to be closely aligned with natural language.
Consider the following example where we will describe some desired
features of our `Tanks` code in English and then show how they can be
implemented in Fearless code.
- **English version**
> *We would like the `Tanks.of` method to create a `Tank` object.*  
> *This `Tank` will have two methods: `.heading` and `.aiming`.*  
> *`Tank.heading` will return the direction the `Tank` is heading;*  
> *and Tank.aiming will return the direction the `Tank` is aiming.*  

- **Fearless version**
`````
  Tanks: { .of(heading: Direction, aiming: Direction): Tank->
    Tank: {.heading: Direction->heading, .aiming: Direction->aiming,}
    }
`````
`Tanks.of` takes two directions (`heading` and `aiming`) and returns a
newly-created Tank object. The second line of our Fearless code above
does this by using a type declaration for a (new type) `Tank`
inside the method body. 

You may think that declaring a new type inside of the method body
contradicts the general idea that method bodies must be expressions.
What kind of expression is a type declaration?
As discussed earlier, there are exactly three kinds of expressions
in Fearless: 
- parameters,
- method calls,
- and object literals.

A type declaration is not a parameter and it is not a method call;
it is actually an object literal.

Before we discussed how `North` is an object literal and how the
syntax for literals can get more involved / complex.
Using a type name as an object literal is convenient and is very common;
however the full syntax for object literals is much richer, and indeed
type declarations in method bodies are just special cases of object literals.

This use of type declarations as objects is interesting because we
can create a new kind of object, an object able to see / capture the method parameters.
In this way, objects can have a custom, useful state.
Up to now we have worked with just a finite set of objects:
the four directions.
Using capturing, we can create more objects, and we can compose objects
to obtain new objects.
For example, we could define a `Platoon` object containing many `Tanks`.

In the code above we defined `Tank` directly in the `Tanks.of` method.
In this way, the only way to create `Tank` objects is to call `Tanks.of`.
Alternatively, we can keep the `Tank` declaration outside and use
inheritance as shown below:
```
Tank: {.heading: Direction, .aiming: Direction,}
Tanks: { .of(heading: Direction, aiming: Direction): Tank->
  MadeTank: Tank {.heading ->heading, .aiming ->aiming,}
  }
TankNN: Tank {.heading ->North, .aiming ->North,}
```
In this way we have more freedom to create `Tank`s:
via the `Tanks.of` method or via a top level declaration, like `TankNN`.

To do so, we introduced the name `MadeTank`,
to indicate tanks originating from that point in the code.
The name `MadeTank` is not very useful, we will probably never want to
talk only about tanks made with the `MakeTank.of` method,
so we can rely on the sugar and type inference to chose a name for us
and to infer that the literal we are creating is extending `Tank`.
In this case, the name for our literal is going to be some fresh name
that never appears anywhere in the code.
That is, the code below is an equivalent but shorter version of the code above.
```
Tank: {.heading: Direction, .aiming: Direction,}
Tanks: { .of(heading: Direction, aiming: Direction): Tank->
  {.heading ->heading, .aiming ->aiming,}
  }
TankNN: Tank {.heading ->North, .aiming ->North,}
```
In practice, while coding in Fearless, most literals will rely on
inference and will be
written just as `{..}`.

At this point you must have noticed that all the method names we have show
start with `.`; and you may be wondering why the odd choice.
The `.` allows the computer to separate method names from other kinds of names;
for example in `North.turn` or `MakeTank.of` it is clear where the type
name finishes and the method name starts.
Fearless has two kinds of method names:

- names starting with exactly one `.` symbol and continuing with a lowercase
letter and any number of letters and numbers, and
- names composed exclusively of a non empty sequence of operator symbols,
that is, any symbols in this list `! ~ # & ^ + - * / < > = :`.
That is, the following is a list of valid and invalid method names:
```
.foo  #  :=  ++  <=  .bar23  <#--  //valid
.foo+  +bar  a=b  zoo  <hello>  .+>//invalid
```

On the other side, parameter names start with a lower-case letter, and
type names mostly start with an upper-case letter.
The rules for valid type names are a little more involved,
and we will discuss them in detail later.

While the code above works fine, we think that using `.of` in this way is
verbose and distracting: is `.of` the right method name?
Conceptually we just want to do `Tanks`, go!!! do your thing! be!

It is very common for Fearless types to have a method that is the 
**most crucial method** of that type.
As a convention, we use the method called `#` for that role.
The operator `#` is much more compact to call than the method `.of`.

Rewriting our last code example using `#` we get the following:

-------------------------*/@Test void tanksHash() { run("""
Direction: {
  .turn: Direction,
  .reverse: Direction ->this.turn.turn,
  }
North: Direction {East}
East : Direction {South}
South: Direction {West}
West : Direction {North}

Tank: {.heading: Direction, .aiming: Direction,}
Tanks: { #(heading: Direction, aiming: Direction): Tank->
  {.heading ->heading, .aiming ->aiming,}
  }
TankNN: Tank {.heading ->North, .aiming ->North,}
  """); }/*--------------------------------------------

Note how pretty much nothing has changed.
We just declared the method to be called `#` instead of `.of`.

We could have renamed into `#` also the method `Direction.turn`,
but we chose not to: 
in our mental model we do not think that turning is the main thing
we do with directions.
When coding it is important to distinguish the intended/ideal state
of the code from the current incomplete version that we are working with,
and to name concepts in function of such ideal state and not the current
sorry version of it.

We can now consider adding some methods to `Tank`:
for example the capacity of turning the turret!
To do so, we only need to update the code of the type declaration for `Tank`:
```
Tank: {
  .heading: Direction,
  .aiming: Direction,
  .turnTurret: Tank-> Tanks#(this.heading, this.aiming.turn)
}
```
As you can see we declare a new method `.turnTurret`
that returns a `Tank` with the turned turret.
We do this by calling `Tanks#` with the current heading
direction and the result of turning the current aiming direction.
We can call this method as follows:

`Tanks#(North,East).turnTurret` 

This will reduce as follows:

<table>
  <tr>
    <td>
      <pre><code>Tanks#(North, East).turnTurret</code></pre>
    </td>
    <td>Call <code>Tanks#</code></td>
  </tr>
  <tr>
    <td>
      <pre><code>{.heading ->North, .aiming ->East,}.turnTurret</code></pre>
    </td>
    <td>Call <code>Tank.turnTurret</code></td>
  </tr>
  <tr>
    <td>
      <pre><code>
Tanks#(
  {.heading ->North, .aiming ->East,}.heading,
  {.heading ->North, .aiming ->East,}.aiming.turn
  )
      </code></pre>
    </td>
    <td>Call <code>.heading</code></td>
  </tr>
  <tr>
    <td>
      <pre><code>
Tanks#(
  North,
  {.heading ->North, .aiming ->East,}.aiming.turn
  )
      </code></pre>
    </td>
    <td>Call <code>.aiming</code></td>
  </tr>
  <tr>
    <td>
      <pre><code>
Tanks#(
  North,
  East.turn
  )
      </code></pre>
    </td>
    <td>Call <code>East.turn</code></td>
  </tr>
  <tr>
    <td>
      <pre><code>Tanks#(North, South)</code></pre>
    </td>
    <td>Call <code>Tanks#</code></td>
  </tr>
  <tr>
    <td>
      <pre><code>{.heading ->North, .aiming ->South,}</code></pre>
    </td>
    <td>Final result</td>
  </tr>
</table>


It is important to learn to visualise how the code reduces in your mind,
so that you can predict code behaviour.


END*/
}
