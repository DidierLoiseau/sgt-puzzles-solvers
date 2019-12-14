package com.github.sgtpuzzles.solvers.loopy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LineStatus {
	LINE_YES("y"),
	LINE_NO("n"),
	;

	private final String serializedState;
}
