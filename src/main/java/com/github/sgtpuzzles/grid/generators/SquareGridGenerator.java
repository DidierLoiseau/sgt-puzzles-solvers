package com.github.sgtpuzzles.grid.generators;

import com.github.sgtpuzzles.grid.model.Graph;
import com.github.sgtpuzzles.grid.model.Vertex;
import com.github.sgtpuzzles.solvers.loopy.params.LoopyParams;

import java.util.Arrays;
import java.util.List;

public class SquareGridGenerator {

	public Graph generate(LoopyParams params) {
		return generate(params.getWidth(), params.getHeight());
	}

	public Graph generate(int width, int height) {
		var grid = new Graph();
		int side = 20;

		// generates square faces horizontally
		// (see grid_new_square() in grid.c from sgtpuzzles)
		// ┌───┬───┐
		// │ 0 │ 1 │
		// ├───┼───┤
		// │ 2 │ 3 │
		// └───┴───┘
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				addFace(grid, side, x, y);
			}
		}

		return grid.markComplete();
	}

	private void addFace(Graph grid, int side, int x, int y) {
		grid.addFace(getVertices(grid, x * side, y * side, side));
	}

	private List<Vertex> getVertices(Graph grid, int x, int y, int side) {
		var topLeft = grid.getVertex(x, y);
		var topRight = grid.getVertex(x + side, y);
		var bottomRight = grid.getVertex(x + side, y + side);
		var bottomLeft = grid.getVertex(x, y + side);
		return Arrays.asList(topLeft, topRight, bottomRight, bottomLeft);
	}
}
