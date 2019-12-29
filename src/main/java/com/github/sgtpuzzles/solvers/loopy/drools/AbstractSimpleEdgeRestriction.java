package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Edge;
import com.github.sgtpuzzles.solvers.loopy.LineStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashSet;

@Getter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Slf4j
public abstract class AbstractSimpleEdgeRestriction extends AbstractEdgeRestriction implements SimpleEdgeRestriction {

	public AbstractSimpleEdgeRestriction(Collection<Edge> edges, int expectedYeses) {
		super(edges, expectedYeses);
	}

	@Override
	public int getExpectedNos() {
		return edges.size() - expectedYeses;
	}

	@Override
	public void recordSetting(EdgeSetting setting) {
		log.debug("Removing {} from {}", setting.getEdge(), this);
		if (!edges.remove(setting.getEdge())) {
			throw new IllegalArgumentException(this + "\n did not contain setting's edge " + setting.getEdge());
		}
		if (setting.getStatus() == LineStatus.LINE_YES) {
			expectedYeses--;
		}
	}

	public static abstract class AbstractSimpleEdgeRestrictionBuilder<C extends AbstractSimpleEdgeRestriction,
			B extends AbstractSimpleEdgeRestrictionBuilder<C, B>>
			extends AbstractEdgeRestriction.AbstractEdgeRestrictionBuilder<C, B> {
		public C amongEdges(Collection<Edge> edges) {
			var restriction = edges(new HashSet<>(edges)).build();
			log.debug("Created {}", restriction);
			return restriction;
		}
	}
}