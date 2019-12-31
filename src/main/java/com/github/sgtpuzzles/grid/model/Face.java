package com.github.sgtpuzzles.grid.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
public class Face {
	private final int id;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private final List<Edge> edges;

	public Edge getEdge(int i) {
		return getEdges().get(i);
	}
}
