package com.github.sgtpuzzles.solvers;

import java.io.IOException;
import java.nio.file.Path;

import static com.github.sgtpuzzles.solvers.SaveKeys.*;

public class SavedGameParser {

	public GameData<?> parseSavedGame(final Path savePath) throws IOException {
		final var rawData = DeserializeData.parse(savePath);

		checkVersion(rawData);

		GameType type = GameType.parse(rawData.get(GAME));
		final GameParamsParser<?> parser = type.getParamsParser();

		return GameData.builder()
				.type(type)
				.params(parser.parseParams(rawData.get(PARAMS)))
				.desc(rawData.get(DESC))
				.rawData(rawData)
				.build();
	}

	private void checkVersion(final DeserializeData rawData) {
		final var version = rawData.get(VERSION);
		if (!version.equals("1")) {
			throw new IllegalArgumentException("Unknown save version " + version);
		}
	}
}
