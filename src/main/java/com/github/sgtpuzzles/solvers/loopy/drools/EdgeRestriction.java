package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Edge;

import java.util.Collection;

public interface EdgeRestriction {
	Collection<Edge> getEdges();

	int getExpectedYeses();

	void recordSetting(EdgeSetting setting);
}
