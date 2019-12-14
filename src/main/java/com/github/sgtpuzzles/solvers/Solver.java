package com.github.sgtpuzzles.solvers;

import com.github.sgtpuzzles.grid.generators.SquareGridGenerator;
import com.github.sgtpuzzles.solvers.loopy.LoopyDescParser;
import com.github.sgtpuzzles.solvers.loopy.LoopySolver;
import com.github.sgtpuzzles.solvers.loopy.params.LoopyParams;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class Solver {

	public static void main(String[] args) throws IOException {
		final var parser = new SavedGameParser();
		final GameData<?> gameData = parser.parseSavedGame(Paths.get(args[0]));
		var generator = new SquareGridGenerator();
		var graph = generator.generate((LoopyParams) gameData.getParams());

		var loopySolver = new LoopySolver();
		var moves = loopySolver.solve(graph, new LoopyDescParser().parseClues(gameData.getDesc()));
		gameData.setMoves(moves);

		var solutionFile = args[0].substring(0, args[0].length() - 4) + ".solved";
		try (var writer = new BufferedWriter(new FileWriter(solutionFile))) {
			gameData.serializeTo(writer);
		}
	}
}
