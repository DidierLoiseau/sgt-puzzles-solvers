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
	 * Returns the number of expected nos.
	 * <p>
	 * This is complementary to {@link #getExpectedYeses()} since expecting at least (resp. at most) a certain number of
	 * yeses implies expecting at most (resp. at least) a certain number of nos among the given set of edges.
	 *
	 * @return the number of expected nos (at most/least)
	 */
	int getExpectedNos();

	/**
	 * Records that the given setting has been applied.
	 *
	 * @param setting the setting to record
	 */
	void recordSetting(EdgeSetting setting);
}
