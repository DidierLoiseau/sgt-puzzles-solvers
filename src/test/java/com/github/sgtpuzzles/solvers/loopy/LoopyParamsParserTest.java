package com.github.sgtpuzzles.solvers.loopy;

import com.github.sgtpuzzles.solvers.loopy.params.LoopyDifficulty;
import com.github.sgtpuzzles.solvers.loopy.params.LoopyParams;
import com.github.sgtpuzzles.solvers.loopy.params.LoopyParamsParser;
import com.github.sgtpuzzles.solvers.loopy.params.LoopyType;
import org.junit.jupiter.api.Test;

import static com.github.sgtpuzzles.solvers.loopy.params.LoopyDifficulty.*;
import static com.github.sgtpuzzles.solvers.loopy.params.LoopyType.*;
import static org.assertj.core.api.Assertions.assertThat;

class LoopyParamsParserTest {
	private final LoopyParamsParser parser = new LoopyParamsParser();

	@Test
	void parseParams() {
		assertThat(parser.parseParams("6t0de"))
				.isEqualTo(gameParams(6, 6, SQUARE, EASY));
		assertThat(parser.parseParams("6t0dn"))
				.isEqualTo(gameParams(6, 6, SQUARE, NORMAL));
		assertThat(parser.parseParams("6t0dt"))
				.isEqualTo(gameParams(6, 6, SQUARE, TRICKY));
		assertThat(parser.parseParams("6t0dh"))
				.isEqualTo(gameParams(6, 6, SQUARE, HARD));
		assertThat(parser.parseParams("7x8t0de"))
				.isEqualTo(gameParams(7, 8, SQUARE, EASY));
		assertThat(parser.parseParams("7x8t1de"))
				.isEqualTo(gameParams(7, 8, TRIANGULAR, EASY));
		assertThat(parser.parseParams("7x8t14de"))
				.isEqualTo(gameParams(7, 8, KAGOME, EASY));
	}

	private LoopyParams gameParams(final int width, final int height, final LoopyType type,
								   final LoopyDifficulty difficulty) {
		return LoopyParams.builder()
				.width(width)
				.height(height)
				.type(type)
				.difficulty(difficulty)
				.build();
	}
}