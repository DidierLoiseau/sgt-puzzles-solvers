package com.github.sgtpuzzles.solvers.loopy.params;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoopyParams {
	private final int width, height;
	private final LoopyType type;
	private final LoopyDifficulty difficulty;
}
