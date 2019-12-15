package com.github.sgtpuzzles.grid.model;

import lombok.*;

import java.util.Collection;
import java.util.List;

@Data
@ToString(includeFieldNames = false)
public class Edge {
	private final int id;
	private final Vertex vertex1, vertex2;
	@Setter(AccessLevel.NONE)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Face face1, face2;

	public void addFace(Face face) {
		if (face1 == null) {
			face1 = face;
		} else if (face2 != null) {
			throw new IllegalArgumentException("Both faces already set");
		} else {
			face2 = face;
		}
	}

	public boolean contains(Vertex vertex) {
		return vertex == vertex1 || vertex == vertex2;
	}

	public Vertex getOther(Vertex vertex) {
		return vertex == vertex1 ? vertex2 : vertex1;
	}

	public Collection<Vertex> getVertices() {
		return List.of(vertex1, vertex2);
	}
}
