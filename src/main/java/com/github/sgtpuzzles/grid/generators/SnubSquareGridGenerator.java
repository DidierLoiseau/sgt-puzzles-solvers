package com.github.sgtpuzzles.grid.generators;

import com.github.sgtpuzzles.grid.model.Graph;
import com.github.sgtpuzzles.solvers.loopy.params.LoopyType;
import lombok.Getter;

public class SnubSquareGridGenerator implements GridGenerator {
	@Getter
	private final LoopyType type = LoopyType.SNUBSQUARE;

	@Override
	public Graph generate(int width, int height) {
		var grid = new Graph();
		int a = 15;
		int b = 26;

		// generates both square and triangular faces horizontally
		// (see grid_new_snubsquare() in grid.c from sgtpuzzles)
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				/* face position */
				int px = (a + b) * x;
				int py = (a + b) * y;

				/* generate square faces */
				if ((x + y) % 2 != 0) {
					grid.addFace(
							px + a, py,
							px + a + b, py + a,
							px + b, py + a + b,
							px, py + b
					);
				} else {
					grid.addFace(
							px + b, py,
							px + a + b, py + b,
							px + a, py + a + b,
							px, py + a
					);
				}

				/* generate up/down triangles */
				if (x > 0) {
					if ((x + y) % 2 != 0) {
						grid.addFace(
								px + a, py,
								px, py + b,
								px - a, py
						);
					} else {
						grid.addFace(
								px, py + a,
								px + a, py + a + b,
								px - a, py + a + b
						);
					}
				}

				/* generate left/right triangles */
				if (y > 0) {
					if ((x + y) % 2 != 0) {
						grid.addFace(
								px + a, py,
								px + a + b, py - a,
								px + a + b, py + a
						);
					} else {
						grid.addFace(
								px, py - a,
								px + b, py,
								px, py + a
						);
					}
				}
			}
		}

		return grid.markComplete();
	}

}
