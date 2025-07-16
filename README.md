# 🧩 sgt-puzzles-solvers

A collection of **Drools-based** solver implementations for selected puzzles from **Simon Tatham’s Portable Puzzle Collection**.

## Overview

This repository provides automated solutions for various logic puzzles originally developed by Simon Tatham. It integrates the puzzle back-ends with Drools, enabling rule‑based solving and flexible strategy definition.

## 🧰 Features

* Solver modules for individual puzzles (currently only Loopy).
* Core engine using [Drools][1] rules and declarative logic.
* Modular architecture allowing easy addition of new puzzle types.

## 🚀 Getting Started

### 🛑 Prerequisites

* **Java JDK 17+**
* **Apache Maven**
* **Drools Framework**

### 🛠️ Building

```bash
git clone https://github.com/DidierLoiseau/sgt-puzzles-solvers.git
cd sgt-puzzles-solvers
mvn clean verify
```

## ⚙️ Usage

Currently, only Loopy is supported.
Just run `com.github.sgtpuzzles.solvers.Solver` with the path to a save file as argument.

It will output to a corresponding `.solved` file that you can then open with the original game.
Since it records each move, you can then play with undo/redo to see the deductions.

## 💡 Why Drools?

I just wanted to learn about it! 🤓 I was playing with Loopy at the time, and thought it would be a nice challenge.

Drools offers a powerful, declarative rule language that lets you articulate logical inferences like “if X is true and Y is true, assert Z”.
This also makes it a natural fit for expressing puzzle-solving logic.

## 📄 References

* Simon Tatham’s original puzzle source collections ([chiark.greenend.org.uk][2])
* Chris Boyle’s port to Android ([chris.boyle.name][3], [github][4]) – the port I was playing, and a convenient way to understand how save files are created

## 👷 Contributing

Contributions are welcome! Please consider:

* Writing clean, well-documented `.drl` rules.
* Adding unit tests for new puzzles.
* Submitting issues with reproducible puzzle instances.
* Discussing high-level solver architecture in issues or pull requests.

[1]: https://www.drools.org/
[2]: https://www.chiark.greenend.org.uk/~sgtatham/puzzles/ "Simon Tatham's Portable Puzzle Collection - Chiark.greenend.org.uk"
[3]: https://chris.boyle.name/projects/android-puzzles/ "Chris Boyle’s port to ANdroid"
[4]: https://github.com/chrisboyle/sgtpuzzles "Chris Boyle’s github repository"
