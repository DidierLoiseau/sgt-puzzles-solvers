package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Edge;

import java.util.Set;

public interface EdgeRestriction {
	/**
	 * @return the edges covered by this restriction
	 */
	Set<Edge> getEdges();

	/**
	 * @return the number of expected yeses (at least/most)
	 */
	int getExpectedYeses();

	/**
	 * Records that the given setting has been applied.
	 *
	 * @param setting the setting to record
	 */
	void recordSetting(EdgeSetting setting);
}
