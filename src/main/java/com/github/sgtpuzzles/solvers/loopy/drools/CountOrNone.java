package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Edge;
import com.github.sgtpuzzles.solvers.loopy.LineStatus;
import lombok.Data;

import java.util.Set;

import static com.github.sgtpuzzles.solvers.loopy.drools.Exactly.exactly;

@Data
public class CountOrNone {
	private final Set<Edge> edges;
	private final int expectedYeses;

	public void recordSetting(EdgeSetting setting) {
		if (setting.getStatus() == LineStatus.LINE_NO) {
			if (!edges.remove(setting.getEdge())) {
				throw new IllegalArgumentException("This Excatly does not contain setting's edge " + setting.getEdge());
			}
		} else {
			// Rule "CountOrNone becomes count" should replace this fact by an Exactly
			// This should not be called.
			throw new IllegalArgumentException("Cannot remove edge for LINE_YES");
		}
	}

	public boolean isAllOrNone() {
		return edges.size() == expectedYeses;
	}

	public Exactly toExactly() {
		return exactly(expectedYeses).amongEdges(edges);
	}
}
