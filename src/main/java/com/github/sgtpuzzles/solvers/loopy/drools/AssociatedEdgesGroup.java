package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Edge;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * A group of {@link Edge}s that must all be in the same state.
 */
@EqualsAndHashCode(callSuper = true)
public class AssociatedEdgesGroup extends AssociatedObjectsGroup<Edge, AssociatedEdgesGroup>
		implements EdgeRestriction {

	public AssociatedEdgesGroup(Edge initial) {
		super(initial);
	}

	// help drools resolve types, otherwise it sees it as an AssociatedObjectsGroup
	@Override
	public AssociatedEdgesGroup getOppositeGroup() {
		return super.getOppositeGroup();
	}

	@Override
	public Set<Edge> getEdges() {
		return getObjects();
	}

	@Override
	public int getExpectedYeses() {
		return getEdges().size();
	}

	@Override
	public void recordSetting(EdgeSetting setting) {
		getObjects().remove(setting.getEdge());
	}

}
