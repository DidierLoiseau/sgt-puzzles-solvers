package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Edge;
import com.github.sgtpuzzles.solvers.loopy.LineStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder
@ToString
@Slf4j
public abstract class AbstractEdgeRestriction implements EdgeRestriction {
	private static int nextId = 0;
	private final int id = nextId++;
	@EqualsAndHashCode.Include
	protected final Set<Edge> edges;
	@EqualsAndHashCode.Include
	protected int expectedYeses;

	public AbstractEdgeRestriction(Collection<Edge> edges, int expectedYeses) {
		this.edges = new HashSet<>(edges);
		this.expectedYeses = expectedYeses;
		log.debug("Created {}", this);
	}

	@Override
	public int getExpectedNos() {
		return edges.size() - expectedYeses;
	}

	@Override
	public void recordSetting(EdgeSetting setting) {
		log.debug("Removing {} from\n\t\t{}", setting.getEdge(), this);
		if (!edges.remove(setting.getEdge())) {
			throw new IllegalArgumentException(this + "\n did not contain setting's edge " + setting.getEdge());
		}
		if (setting.getStatus() == LineStatus.LINE_YES) {
			expectedYeses--;
		}
	}

	public static abstract class AbstractEdgeRestrictionBuilder<C extends AbstractEdgeRestriction, B extends AbstractEdgeRestrictionBuilder<C, B>> {
		public C amongEdges(Collection<Edge> edges) {
			var restriction = edges(new HashSet<>(edges)).build();
			log.debug("Created {}", restriction);
			return restriction;
		}
	}
}
