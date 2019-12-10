package com.github.sgtpuzzles.solvers;

import com.github.sgtpuzzles.solvers.loopy.params.LoopyParamsParser;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public enum GameType {
	// only supported value for now
	LOOPY(new LoopyParamsParser());

	private final GameParamsParser<?> paramsParser;

	GameType(GameParamsParser<?> paramsParser) {
		this.paramsParser = paramsParser;
	}

	public static GameType parse(String value) {
		return valueOf(value.toUpperCase());
	}

	@SneakyThrows
	GameParamsParser<?> getParamsParser() {
		return paramsParser;
	}
}
