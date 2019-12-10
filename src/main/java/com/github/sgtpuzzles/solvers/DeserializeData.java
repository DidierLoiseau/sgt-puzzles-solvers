package com.github.sgtpuzzles.solvers;

import lombok.Data;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

@Data
public class DeserializeData {

	private final Map<String, DeserializeLine> data;
	private final List<DeserializeLine> moves;

	public String get(final SaveKeys key) {
		var line = data.get(key.name());
		return line == null ? null : line.getValue();
	}

	public void serializeTo(Writer output) {
		data.values().forEach(d -> d.serializeTo(output));
		moves.forEach(d -> d.serializeTo(output));
	}

	public static DeserializeData parse(final Path save) throws IOException {
		try (var lines = Files.lines(save)) {
			return lines
					.map(DeserializeData::parseLine)
					.collect(collectingAndThen(
							toList(),
							DeserializeData::from)
					);
		}
	}

	private static DeserializeLine parseLine(final String line) {
		final var lineData = line.split(":", 3);
		return new DeserializeLine(lineData[0].strip(), lineData[2]);
	}

	private static DeserializeData from(List<DeserializeLine> lines) {
		var linesByIsMove = lines.stream().collect(partitioningBy(DeserializeData::isMove, toCollection(ArrayList::new)));
		var data = linesByIsMove.get(false).stream()
				.collect(toMap(
						DeserializeLine::getKey,
						Function.identity(),
						(line, line2) -> {
							throw new IllegalStateException("Two lines with same key: " + line.getKey());
						},
						LinkedHashMap::new));
		return new DeserializeData(data, linesByIsMove.get(true));
	}

	private static boolean isMove(DeserializeLine line) {
		try {
			return SaveKeys.valueOf(line.getKey()).isMove();
		} catch (IllegalArgumentException e) {
			// unknown parameter type
			return false;
		}
	}
}

@Data
class DeserializeLine {
	private final String key, value;

	public void serializeTo(Writer output) throws UncheckedIOException {
		try {
			output.append(String.format("%-8s:%d:%s\n", key, value.length(), value));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}