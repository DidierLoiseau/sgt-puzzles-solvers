package com.github.sgtpuzzles.grid.generators;

import com.github.sgtpuzzles.grid.model.Graph;
import com.github.sgtpuzzles.solvers.loopy.params.LoopyParams;
import com.github.sgtpuzzles.solvers.loopy.params.LoopyType;

public interface GridGenerator {

	default Graph generate(LoopyParams params) {
		return generate(params.getWidth(), params.getHeight());
	}

	Graph generate(int width, int height);

	LoopyType getType();
}