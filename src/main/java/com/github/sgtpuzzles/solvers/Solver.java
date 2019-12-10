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
		System.out.println(gameData);
		var generator = new SquareGridGenerator();
		var grid = generator.generate((LoopyParams) gameData.getParams());
		System.out.println(grid);
		try (var writer = new BufferedWriter(new FileWriter(args[0] + ".copy"))) {
			gameData.getRawData().serializeTo(writer);
		}
	}
}
