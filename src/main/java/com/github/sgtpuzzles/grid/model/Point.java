package com.github.sgtpuzzles.grid.model;

import lombok.Data;

@Data
public class Point {
	private final int x, y;

	public String toString() {
		return String.format("[%d,%d]", x, y);
	}
}
