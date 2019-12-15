package com.github.sgtpuzzles.grid.model;

import lombok.Data;

import java.util.List;

@Data
public class Face {
	private final int id;
	private final List<Edge> edges;

	public Edge getEdge(int i) {
		return getEdges().get(i);
	}
}
