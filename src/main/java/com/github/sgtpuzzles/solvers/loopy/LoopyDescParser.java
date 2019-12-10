package com.github.sgtpuzzles.solvers.loopy;

import java.util.HashMap;

public class LoopyDescParser {
	private enum CharType {
		SKIP, CLUE;

		public static CharType from(char c) {
			return c >= 'a' && c <= 'z' ? SKIP : CLUE;
		}
	}

	public HashMap<Integer, Integer> parseClues(String desc) {
		var chars = desc.toCharArray();
		int faceId = 0;
		HashMap<Integer, Integer> result = new HashMap<>();

		for (int i = 0; i < desc.length(); i++) {
			switch (CharType.from(chars[i])) {
			case SKIP:
				faceId += parseSkip(chars[i]);
				break;
			case CLUE:
				result.put(faceId, parseClue(chars[i]));
				faceId++;
				break;
			}
		}

		return result;
	}

	private Integer parseClue(char c) {
		if (c >= '0' && c <= '9') {
			return c - '0';
		} else if (c >= 'A' && c <= 'Z') {
			return c - 'A' + 10;
		} else {
			throw new IllegalArgumentException("Illegal clue character: " + c);
		}
	}

	private int parseSkip(char c) {
		return c - 'a' + 1;
	}

}
