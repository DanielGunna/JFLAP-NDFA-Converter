# JFLAP-Lambda-Converter

## Introduction ##

A simple tool to convert [NDFA(Non-deterministic Finite Automaton)](https://en.wikipedia.org/wiki/Nondeterministic_finite_automaton)(without [Lambda(or Epilson) Transitions](https://en.wikipedia.org/wiki/Epsilon_transition)) into [DFA(Deterministic Finite Automaton)](https://en.wikipedia.org/wiki/Deterministic_finite_automaton), both using [JFLAP](http://www.jflap.org/)'s simulator file format.

## Conversion Strategy ##

The approach used to convert a given NDFA is based on [dynamic programming](https://en.wikipedia.org/wiki/Dynamic_programming). First we create a data structure to represent NDFA as shown for sample automaton below:

![Sample Automaton](https://github.com/DanielGunna/JFLAP-NDFA-Converter/blob/DanielGunna-patch-1/images/automata.png)


| States/Symbols       	| Symbol 0  	| Symbol 1           	|
|----------------------	|-----------	|--------------------	|
| State 0 (**Initial**)	| {State 0} 	| {State 0, State 1} 	|
| State 1              	| {State 2} 	| {State 2}          	|
| State 2              	| {State 3} 	| {State 3}          	|
| State 3 (**Final**)  	| {       } 	| {       }          	|

For each  NDFA automaton's state, we'll list all others states that can be reached  through computing of each symbol of alphabet by current state. 

Next step is add  NDFA automaton's initial state to a new data structure similar as that we show above. This data structure will be used in conversion process to represent  DFA automaton resulted from conversion. Data structure after previous steps is showed below:


| States/Symbols       	| Symbol 0  	| Symbol 1           	|
|----------------------	|-----------	|--------------------	|
| State 0 (**Initial**)	| { } 	| { }         	|
|                     	| { } 	| { }          	|
|                     	| { } 	| { }          	|
|                       | { } 	| { }          	|




## Usage ## 
