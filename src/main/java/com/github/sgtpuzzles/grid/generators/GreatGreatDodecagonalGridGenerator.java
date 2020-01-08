package com.github.sgtpuzzles.grid.generators;

import com.github.sgtpuzzles.grid.model.Graph;
import com.github.sgtpuzzles.solvers.loopy.params.LoopyType;
import lombok.Getter;

public class GreatGreatDodecagonalGridGenerator implements GridGenerator {
	@Getter
	private final LoopyType type = LoopyType.GREATGREATDODECAGONAL;

	@Override
	public Graph generate(int width, int height) {
		var grid = new Graph();
		int a = 15;
		int b = 26;

		// generates both square and triangular faces horizontally
		// (see grid_new_greathexagonal() in grid.c from sgtpuzzles)

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				/* centre of dodecagon */
				int px = (4 * a + 4 * b) * x;
				int py = (6 * a + 2 * b) * y;
				if (y % 2 != 0) {
					px += 2 * a + 2 * b;
				}

				/* dodecagon */
				grid.addFace(
						px + a, py - (2 * a + b),
						px + a + b, py - (a + b),
						px + 2 * a + b, py - a,
						px + 2 * a + b, py + a,
						px + a + b, py + a + b,
						px + a, py + 2 * a + b,
						px - a, py + 2 * a + b,
						px - (a + b), py + a + b,
						px - (2 * a + b), py + a,
						px - (2 * a + b), py - a,
						px - (a + b), py - (a + b),
						px - a, py - (2 * a + b)
				);

				/* hexagon on top right of dodecagon */
				if (y != 0 && (x < width - 1 || y % 2 == 0)) {
					grid.addFace(
							px + a + 2 * b, py - (4 * a + b),
							px + a + 2 * b, py - (2 * a + b),
							px + a + b, py - (a + b),
							px + a, py - (2 * a + b),
							px + a, py - (4 * a + b),
							px + a + b, py - (5 * a + b)
					);
				}

				/* hexagon on right of dodecagon */
				if (x < width - 1) {
					grid.addFace(
							px + 2 * a + 3 * b, py - a,
							px + 2 * a + 3 * b, py + a,
							px + 2 * a + 2 * b, py + 2 * a,
							px + 2 * a + b, py + a,
							px + 2 * a + b, py - a,
							px + 2 * a + 2 * b, py - 2 * a
					);
				}

				/* hexagon on bottom right of dodecagon */
				if (y < height - 1 && (x < width - 1 || y % 2 == 0)) {
					grid.addFace(
							px + a + 2 * b, py + 2 * a + b,
							px + a + 2 * b, py + 4 * a + b,
							px + a + b, py + 5 * a + b,
							px + a, py + 4 * a + b,
							px + a, py + 2 * a + b,
							px + a + b, py + a + b
					);
				}

				/* square on top right of dodecagon */
				if (y != 0 && x < width - 1) {
					grid.addFace(
							px + a + 2 * b, py - (2 * a + b),
							px + 2 * a + 2 * b, py - 2 * a,
							px + 2 * a + b, py - a,
							px + a + b, py - (a + b)
					);
				}

				/* square on bottom right of dodecagon */
				if (y < height - 1 && x < width - 1) {
					grid.addFace(
							px + 2 * a + 2 * b, py + 2 * a,
							px + a + 2 * b, py + 2 * a + b,
							px + a + b, py + a + b,
							px + 2 * a + b, py + a
					);
				}

				/* square below dodecagon */
				if (y < height - 1 && (x < width - 1 || y % 2 == 0) && (x > 0 || y % 2 != 0)) {
					grid.addFace(
							px + a, py + 2 * a + b,
							px + a, py + 4 * a + b,
							px - a, py + 4 * a + b,
							px - a, py + 2 * a + b
					);
				}

				/* square on bottom left of dodecagon */
				if (x != 0 && y < height - 1) {
					grid.addFace(
							px - (2 * a + b), py + a,
							px - (a + b), py + a + b,
							px - (a + 2 * b), py + 2 * a + b,
							px - (2 * a + 2 * b), py + 2 * a
					);
				}

				/* square on top left of dodecagon */
				if (x != 0 && y != 0) {
					grid.addFace(
							px - (a + b), py - (a + b),
							px - (2 * a + b), py - a,
							px - (2 * a + 2 * b), py - 2 * a,
							px - (a + 2 * b), py - (2 * a + b)
					);

				}

				/* square above dodecagon */
				if (y != 0 && (x < width - 1 || y % 2 == 0) && (x > 0 || y % 2 != 0)) {
					grid.addFace(
							px + a, py - (4 * a + b),
							px + a, py - (2 * a + b),
							px - a, py - (2 * a + b),
							px - a, py - (4 * a + b)
					);
				}

				/* upper triangle (v) */
				if (y != 0 && x < width - 1) {
					grid.addFace(
							px + 3 * a + 2 * b, py - (2 * a + b),
							px + 2 * a + 2 * b, py - 2 * a,
							px + a + 2 * b, py - (2 * a + b)
					);
				}

				/* lower triangle (^) */
				if (y < height - 1 && x < width - 1) {
					grid.addFace(
							px + 3 * a + 2 * b, py + 2 * a + b,
							px + a + 2 * b, py + 2 * a + b,
							px + 2 * a + 2 * b, py + 2 * a
					);
				}
			}
		}

		return grid.markComplete();
	}

}
