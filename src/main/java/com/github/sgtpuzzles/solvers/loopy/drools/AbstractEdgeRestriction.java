package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Edge;
import com.github.sgtpuzzles.solvers.loopy.LineStatus;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@SuperBuilder
public abstract class AbstractEdgeRestriction implements EdgeRestriction {
	protected final Set<Edge> edges;
	protected int expectedYeses;

	public AbstractEdgeRestriction(Collection<Edge> edges, int expectedYeses) {
		this.edges = new HashSet<>(edges);
		this.expectedYeses = expectedYeses;
	}

	@Override
	public void recordSetting(EdgeSetting setting) {
		if (!edges.remove(setting.getEdge())) {
			throw new IllegalArgumentException("This Excatly does not contain setting's edge " + setting.getEdge());
		}
		if (setting.getStatus() == LineStatus.LINE_YES) {
			expectedYeses--;
		}
	}

	public static abstract class AbstractEdgeRestrictionBuilder<C extends AbstractEdgeRestriction, B extends AbstractEdgeRestrictionBuilder<C, B>> {
		public C amongEdges(Set<Edge> edges) {
			return edges(edges).build();
		}
	}
}
