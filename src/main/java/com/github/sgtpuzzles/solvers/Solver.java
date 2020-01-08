package com.github.sgtpuzzles.solvers;

import com.github.sgtpuzzles.grid.generators.GridGenerator;
import com.github.sgtpuzzles.grid.generators.GridGeneratorFactory;
import com.github.sgtpuzzles.solvers.loopy.LoopyDescParser;
import com.github.sgtpuzzles.solvers.loopy.LoopySolver;
import com.github.sgtpuzzles.solvers.loopy.params.LoopyParams;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class Solver {

	public static void main(String[] args) throws IOException {
		var parser = new SavedGameParser();
		var gridFactory = new GridGeneratorFactory();
		GameData<?> gameData = parser.parseSavedGame(Paths.get(args[0]));
		var params = (LoopyParams) gameData.getParams();
		GridGenerator generator = gridFactory.getGenerator(params.getType());
		Objects.requireNonNull(generator, "No generator available for " + params.getType());
		var graph = generator.generate(params);

		var loopySolver = new LoopySolver();
		var moves = loopySolver.solve(graph, new LoopyDescParser().parseClues(gameData.getDesc()));
		gameData.setMoves(moves);

		var solutionFile = args[0].substring(0, args[0].length() - 4) + ".solved";
		try (var writer = new BufferedWriter(new FileWriter(solutionFile))) {
			gameData.serializeTo(writer);
		}
	}
}
