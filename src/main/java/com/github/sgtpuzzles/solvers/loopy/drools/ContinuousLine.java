package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Edge;
import com.github.sgtpuzzles.grid.model.Vertex;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;

@EqualsAndHashCode
@Getter
@ToString
public class ContinuousLine {
	private final List<Vertex> ends;
	@EqualsAndHashCode.Exclude
	private final Set<Edge> edges;

	public ContinuousLine(Edge e) {
		super();
		ends = new ArrayList<>(asList(e.getVertex1(), e.getVertex2()));
		edges = new HashSet<>(singleton(e));
	}

	public void extendWith(Edge edge) {
		for (ListIterator<Vertex> it = ends.listIterator(); it.hasNext(); ) {
			Vertex vertex = it.next();

			if (edge.contains(vertex)) {
				it.set(edge.getOther(vertex));
				edges.add(edge);
				return;
			}
		}
		throw new IllegalArgumentException("Line " + this + " is not adjacent with " + edge);
	}

	public void mergeWith(ContinuousLine other, Edge connectingEdge) {
		extendWith(connectingEdge);
		edges.addAll(other.getEdges());
		ends.addAll(other.getEnds());
		ends.removeAll(connectingEdge.getVertices());
		if (ends.size() != 2) {
			throw new IllegalStateException("Didn't compute new ends properly!");
		}
	}
}
