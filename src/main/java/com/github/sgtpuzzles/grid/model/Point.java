package com.github.sgtpuzzles.grid.model;

import lombok.Data;
import org.apache.commons.lang3.builder.CompareToBuilder;

@Data
public class Point implements Comparable<Point> {
	private final int x, y;

	@Override
	public String toString() {
		return String.format("[%d,%d]", x, y);
	}

	@Override
	public int compareTo(Point o) {
		return new CompareToBuilder()
				.append(x, o.x)
				.append(y, o.y)
				.toComparison();
	}
}
