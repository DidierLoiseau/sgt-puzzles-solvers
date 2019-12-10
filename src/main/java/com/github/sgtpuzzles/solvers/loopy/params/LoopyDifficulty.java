package com.github.sgtpuzzles.solvers.loopy.params;

import java.util.Arrays;

public enum LoopyDifficulty {
	EASY, NORMAL, TRICKY, HARD;

	private final String encodedValue;

	LoopyDifficulty() {
		encodedValue = name().toLowerCase().substring(0, 1);
	}

	public static LoopyDifficulty fromEncodedValue(String v) {
		return Arrays.stream(values())
				.filter(d -> d.encodedValue.equals(v))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Unknown difficulty " + v));
	}
}
