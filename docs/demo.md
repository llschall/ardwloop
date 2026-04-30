---
layout: default
title: Home
---

# How to run the demo

## Upload the `demo_led` example on the Arduino Board

## Start the Java program

# How the demo internally works

The demo code is written in Java and located in `app/src/main/java`.
It applies the MVC pattern.
It firstly intends to provide call examples to the **ardwloop API**.

## DemoProgram

`ardwloop.demo.model.DemoProgram` implements the communication with the Arduino board.

That's the most important class to understand what the Demo program sends and receives from the Arduino board.

It implements the **IArdwProgram** interface, by particular the 2 core methods:

- `public SetupData ardwSetup(SetupData setup)`
- `public LoopData ardwLoop(LoopData loop)`

Any program using ardwloop should implement the **IArdwProgram** interface.
See [source code](https://github.com/llschall/ardwloop-demo/blob/main/app/src/main/java/ardwloop/demo/model/DemoProgram.java).

## DemoModel

`ardwloop.demo.model.DemoModel` is the model from the MVC pattern.

The most important is that it starts the Ardwloop threads in its `start()` method, and that it forwards the necessary
commands to its `DemoProgram` instance.

In addition, it does the _optional_ stuff hereafter:

- it stores the **ArdwloopModel** that would enable to extract various information later on.
- it uses the ardwloop **refresher** to synchronize the DemoView refresh with the Arduino communication cycles.

## StartDemo

`ardwloop.demo.StartDemo.main()` is where the program is started.
