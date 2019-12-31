package com.github.sgtpuzzles.grid.model;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.unmodifiableList;

@Data
public class Graph {
	@ToString.Exclude
	private final List<Face> faces = new ArrayList<>();
	private final List<Edge> edges = new ArrayList<>();
	private final Map<Point, Vertex> vertices = new HashMap<>();
	@ToString.Exclude
	private final ArrayList<Edge> outerFaceEdges = new ArrayList<>();
	@ToString.Exclude
	private final Face outerFace = new Face(-1, unmodifiableList(outerFaceEdges));

	public Vertex getVertex(final int x, final int y) {
		return vertices.computeIfAbsent(new Point(x, y), Vertex::new);
	}

	public Edge getEdge(final Vertex v1, final Vertex v2) {
		return v1.getEdge(v2)
				.orElseGet(() -> createEdge(v1, v2));
	}

	private Edge createEdge(final Vertex v1, final Vertex v2) {
		final var edge = new Edge(edges.size(), v1, v2);
		edges.add(edge);
		v1.addEdge(edge);
		v2.addEdge(edge);
		return edge;
	}

	public void addFace(List<Vertex> vertices) {
		var edges = IntStream.range(0, vertices.size() - 1)
				.mapToObj(i -> getEdge(vertices.get(i), vertices.get(i + 1)))
				.collect(Collectors.toCollection(ArrayList::new));
		edges.add(getEdge(vertices.get(vertices.size() - 1), vertices.get(0)));

		addFaceWithEdges(edges);
	}

	public void addFaceWithEdges(List<Edge> edges) {
		var face = new Face(faces.size(), unmodifiableList(edges));
		edges.forEach(e -> e.addFace(face));
		faces.add(face);
	}

	public Graph markComplete() {
		edges.stream()
				.filter(e -> e.getFace2() == null)
				.forEach(this::setOuterFace);
		faces.add(outerFace);
		return this;
	}

	private void setOuterFace(Edge edge) {
		edge.addFace(outerFace);
		outerFaceEdges.add(edge);
	}

	public Face getFace(int i) {
		return getFaces().get(i);
	}
}
