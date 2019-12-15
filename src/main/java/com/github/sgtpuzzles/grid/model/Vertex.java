package com.github.sgtpuzzles.grid.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@ToString(includeFieldNames = false)
public class Vertex {
	private final Point position;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private final Map<Vertex, Edge> edges = new HashMap<>();

	public Optional<Edge> getEdge(final Vertex other) {
		return Optional.ofNullable(edges.get(other));
	}

	public void addEdge(final Edge edge) {
		var other = edge.getVertex1() == this ? edge.getVertex2() : edge.getVertex1();
		edges.put(other, edge);
	}
}
