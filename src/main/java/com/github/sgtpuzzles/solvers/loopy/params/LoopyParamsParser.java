package com.github.sgtpuzzles.solvers.loopy.params;

import com.github.sgtpuzzles.solvers.GameParamsParser;

import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class LoopyParamsParser implements GameParamsParser<LoopyParams> {
	private static final Pattern PATTERN = Pattern.compile("^(\\d+)(x(\\d+))?t(\\d+)d(.)");

	@Override
	public LoopyParams parseParams(String params) {
		var matcher = PATTERN.matcher(params);

		if (matcher.matches()) {
			var width = parseInt(matcher.group(1));
			var height = matcher.group(3);
			return LoopyParams.builder()
					.width(width)
					.height(height == null ? width : parseInt(height))
					.type(LoopyType.values()[parseInt(matcher.group(4))])
					.difficulty(LoopyDifficulty.fromEncodedValue(matcher.group(5)))
					.build();
		} else {
			throw new IllegalArgumentException("Cannot parse params: " + params);
		}
	}
}
