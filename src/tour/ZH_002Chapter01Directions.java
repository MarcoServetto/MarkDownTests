package tour;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ZH_002Chapter01Directions {
  void run(String s){}
/*START
--CHAPTER-- Chapter 1
--SECTION-- Directions

# Chapter 1

### The three Main Fearless Concepts
In Fearless we have three main concepts: 
- Types,
- Methods and
- Expressions.

To demonstrate these concepts, let’s consider an example expressing
the four cardinal directions:
`````
North:{}
East: {}
South:{}
West: {}
`````
We used four names of real world directions to name **types** in Fearless.
Those four lines are **type declarations**, and the four names are **type names**.
However, those types are not very useful because they are unconnected.
We can create connections between those types by adding methods
to our directions:
`````
North: {.turn -> East, }
East : {.turn -> South,}
South: {.turn -> West, }
West : {.turn -> North,}
`````
In the example above,
- `North: { .turn -> East, }` is a type declaration.
Within this declaration
- `North` is the typeName,
- `.turn` is a **method name**.
- `.turn -> East,` is a **method declaration** and in this case
  the method body is just `East`.

We will see that method bodies can get much more involved than that.
We introduced `East` as a type name; but when `East` is used inside
the method body it is seen as an **expression**,
specifically, an object literal expression. Objects represent conceptual units.

Coding is all about defining kinds of objects and the relations between them.
Other terms for 'object' could be 'value', 'entity', 'concept' or 'element'.
We will use the term 'object' consistently to talk about those.

To understand this better, consider the English sentence “Stop”.
This sentence consists of a single word; and yet, it is still a sentence.
An English sentence is a collection of words and punctuation with at least
one word. In the same way, some expressions in Fearless are simply
a single typeName. However, just as sophisticated sentences in English
are fundamental to effective communication, more elaborate expressions are
crucial in programming.

### Method calls: expressions composed by multiple parts.

Method calls are expressions composed of other expressions.
Method calls allow us to write the equivalent of sentences
in natural language.
Single words have relatively simple meanings but they can be chained
together with other words to communicate a more complex idea.
Using the code above, a simple example of a method call is `North.turn`.
- `North` is an expression and
- `North.turn` is an expression built on top of `North`.
- `North.turn.turn` is also a valid expression, built on top of `North.turn`.

We can write `North` to refer to the object North,
that we are interpreting as representing the cardinal direction north.
We can also write `North.turn` to refer to the object East,
that we are interpreting as representing the cardinal direction east.
In other words, there are exactly two ways to refer to a literal:
1. by directly mentioning it, or
2. by mentioning some expression that returns it.

We say that the `.turn` method of `North` returns the object `East`.
In other words, `North.turn` is an expression which is equivalent to
the object `East`.
In the same way, `South.turn.turn` is an expression which is equivalent
to the object `North`.  This process of discovering the meaning of an
expression is called evaluation.

The example we are discussing, 
would cause an error if we try to run it.
`````
North: {.turn-> East, } 
East : {.turn-> South,}
South: {.turn-> West, }
West : {.turn-> North,}

Error: missing type for method .turn in North
`````
While the exact error message may vary between different versions
of Fearless, the core of this error is that the method `.turn`
does not know what type it returns.

Types help reasoning and they are used to ensure that
our program is safe to use. 
Many programmers rely on types to understand code.

So, what does the `North.turn` method return? `East`. So we can write:
`````
North: {.turn: East -> East, }
East : {.turn: South-> South,}
South: {.turn: West -> West, }
West : {.turn: North-> North,}
`````
This is correct, and would make our code compile. However,
this is stating that the four methods `.turn` are different
kinds of methods, since they return different types.
This does not capture our intention.
We intended for each of our four directions to be examples
of a general idea, the idea of `Direction`;
and for every direction to have a `.turn` method, giving us the next
clockwise direction.

We can capture this intention with the following code:
`````
Direction: {.turn: Direction,}
North: Direction {.turn-> East, }
East : Direction {.turn-> South,}
South: Direction {.turn-> West, }
West : Direction {.turn-> North,}
`````
Since `North`,`East`,`South` and `West` are all directions,
we can introduce a `Direction` type to group `North`,`East`,
`South` and `West` as kinds of `Direction`.
We say that `North`,`East`,`South` and `West` implement `Direction`.
We also say that `Direction` is a supertype of 
`North`,`East`,`South` and `West`.

There is now a type `Direction` that has an abstract method `.turn`
returning a `Direction`. The method `Direction.turn` is **abstract** since
there is no body. In other words, there is no `->` (arrow) symbol followed
by an expression.
We can think that there are five different `.turn` methods, one abstract
and four concrete/implemented.
Alternatively, we can see `.turn` as a single method, whose implementation
is scattered across all the kinds of directions.
Those two viewpoints are equivalently correct.

Note how we do not need to repeat the type `Direction` in `North.turn`.
Fearless can infer or predict that type since `North` implements `Direction`
and `Direction.turn` returns a `Direction`.
This process is called type inference and is an important
concept in Fearless.

We can now imagine a method `.reverse` that turns 180 degrees.
A naive approach would hard code the method result in all the cases,
as shown below.
`````
Direction: {.turn:Direction, .reverse:Direction,}
North: Direction {.turn->East, .reverse->South,}
East : Direction {.turn->South, .reverse->West,}
South: Direction {.turn->West, .reverse->North,}
West : Direction {.turn->North, .reverse->East,}
`````
This solution works but is quite verbose.
A better solution for any given direction would be to call `.turn`
twice and get the desired result:
rotating 180 degrees clockwise is the same as rotating 90 degrees clockwise,
twice.

However, if we simply follow this intuition and we add `.turn.turn` in all the directions, we get even more verbose code.
`````
Direction: {.turn: Direction, .reverse: Direction,}
North: Direction {.turn->East,  .reverse->North.turn.turn,}
East : Direction {.turn->South, .reverse->East.turn.turn, }
South: Direction {.turn->West,  .reverse->South.turn.turn,}
West : Direction {.turn->North, .reverse->West.turn.turn, }
`````
However, the code is now more regular, the four bodies of `.reverse` are all almost identical.
They are all of the form `.reverse->???.turn.turn,` where `???` is the current direction.

We are able to reuse the  `.reverse->???.turn.turn,` part by putting it
into our definition of `Direction`.

`````
Direction: {
  .turn: Direction,
  .reverse: Direction ->???.turn.turn,
  }
North: Direction {.turn->East, }
East : Direction {.turn->South,}
South: Direction {.turn->West, }
West : Direction {.turn->North,}
`````
Now the code of `.reverse` appears only one time in our program.
Note how we are using some new lines and spaces to show visually the
content of `Direction`. This is called indentation. 

Of course, `???` is not valid Fearless code.
Originally, we wrote the name of the current direction, eg.
`North.turn.turn`, `East.turn.turn`. 
A method body allows us to reuse some code over and over again,
but here instead of repeating the same exact behaviour over and over again,
we want our behaviour to be contextual over the current direction.
That is, `???` should be `North` inside of `North.reverse` and
`South` inside of `South.reverse`.
We can indeed access the current direction by using `this`, as shown below.
-------------------------*/@Test void directionReverse() { run("""
  Direction: {
    .turn: Direction,
    .reverse: Direction ->this.turn.turn,
    }
  North: Direction {.turn->East, }
  East : Direction {.turn->South,}
  South: Direction {.turn->West, }
  West : Direction {.turn->North,}
  """); }/*--------------------------------------------
That is, when `.reverse` is inherited by other directions,
the **parameter** `this` will refer to the current literal.
For example, even though the method `North.reverse` is `this.turn.turn`, in that context `this` is the `North` literal, so when the method is called we would replace `this` with `North` and get  `North.turn.turn`.
Here is an example showing this execution / replacement process.

 | ------------------ | ----------------------------------- |
 | `North.reverse`    | Original form                       |
 | `North.turn.turn`  | Expanded method `North.reverse`     |
 | `East.turn`        | Expanded method `North.turn`        |
 | `South`            | Expanded method `East.turn`         |

`this` is an example of the third and last kind of expression: parameters.

### The three kinds of Fearless expressions
In summary, the three kinds of Fearless expressions are:
1. parameters: `this` as an example.
  Later, we will see how to declare and use other parameters.
2. method calls: `North.turn` and `North.reverse`.
  Later we will see more kinds of method calls.
3. objects: `North`, `East`, `South` and `West`. 
  The literals we have seen are simply the names of existing types.
  Later on in this tutorial we will see more kinds of objects,
  capable of summoning into existence novel or unique values;
  in addition to the ones already existing.

Note: `North`, `East`, `South`, and `West` can be used directly as
objects because they have no abstract methods. 
Since `Direction` has at least one abstract method (`.turn`),
it  can not be used as a valid object.

END*/
}
