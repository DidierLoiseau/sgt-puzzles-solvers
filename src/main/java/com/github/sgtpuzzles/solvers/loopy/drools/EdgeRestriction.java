package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Edge;

import java.util.Set;

public interface EdgeRestriction {
	Set<Edge> getEdges();

	int getExpectedYeses();

	void recordSetting(EdgeSetting setting);
}
