package com.github.sgtpuzzles.grid.generators;

import com.github.sgtpuzzles.grid.model.Graph;
import com.github.sgtpuzzles.solvers.loopy.params.LoopyType;
import lombok.Getter;

public class GreatHexagonalGridGenerator implements GridGenerator {
	@Getter
	private final LoopyType type = LoopyType.GREATHEXAGONAL;

	@Override
	public Graph generate(int width, int height) {
		var grid = new Graph();
		int a = 15;
		int b = 26;

		// generates both square and triangular faces horizontally
		// (see grid_new_greatgreatdodecagonal() in grid.c from sgtpuzzles)
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				/* centre of hexagon */
				int px = (3 * a + b) * x;
				int py = (2 * a + 2 * b) * y;
				if (x % 2 != 0) {
					py += a + b;
				}

				/* hexagon */
				grid.addFace(
						px - a, py - b,
						px + a, py - b,
						px + 2 * a, py,
						px + a, py + b,
						px - a, py + b,
						px - 2 * a, py
				);

				/* square below hexagon */
				if (y < height - 1) {
					grid.addFace(
							px - a, py + b,
							px + a, py + b,
							px + a, py + 2 * a + b,
							px - a, py + 2 * a + b
					);
				}

				/* square below right */
				if (x < width - 1 && (x % 2 == 0 || y < height - 1)) {
					grid.addFace(
							px + 2 * a, py,
							px + 2 * a + b, py + a,
							px + a + b, py + a + b,
							px + a, py + b
					);
				}

				/* square below left */
				if (x > 0 && (x % 2 == 0 || y < height - 1)) {
					grid.addFace(
							px - 2 * a, py,
							px - a, py + b,
							px - a - b, py + a + b,
							px - 2 * a - b, py + a
					);
				}

				/* Triangle below right */
				if (x < width - 1 && y < height - 1) {
					grid.addFace(
							px + a, py + b,
							px + a + b, py + a + b,
							px + a, py + 2 * a + b
					);
				}

				/* Triangle below left */
				if (x > 0 && y < height - 1) {
					grid.addFace(
							px - a, py + b,
							px - a, py + 2 * a + b,
							px - a - b, py + a + b
					);
				}
			}
		}

		return grid.markComplete();
	}

}
