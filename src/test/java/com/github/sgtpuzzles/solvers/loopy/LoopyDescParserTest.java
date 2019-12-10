package com.github.sgtpuzzles.solvers.loopy;

import org.assertj.core.data.MapEntry;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class LoopyDescParserTest {

	private final LoopyDescParser parser = new LoopyDescParser();

	@Test
	public void parseClues() {
		assertThat(parseClues("")).isEmpty();
		assertThat(parseClues("a")).isEmpty();
		assertThat(parseClues("b")).isEmpty();
		assertThat(parseClues("z")).isEmpty();

		assertParseWithSingleClue("0", 0);
		assertParseWithSingleClue("1", 1);
		assertParseWithSingleClue("9", 9);
		assertParseWithSingleClue("A", 10);
		assertParseWithSingleClue("Z", 35);

		assertThat(parseClues("a4dD3zz9")).containsOnly(
				clueEntry(1, 4),
				clueEntry(6, 13),
				clueEntry(7, 3),
				clueEntry(7 + 2 * 26 + 1, 9));
	}

	private HashMap<Integer, Integer> parseClues(final String desc) {
		return parser.parseClues(desc);
	}

	private void assertParseWithSingleClue(String s, int i) {
		assertThat(parseClues(s)).containsOnly(clueEntry(0, i));
	}

	private static MapEntry<Integer, Integer> clueEntry(int faceId, int edgeCount) {
		return entry(faceId, edgeCount);
	}
}