package com.github.sgtpuzzles.solvers.loopy.drools;

/**
 * A simple restriction on the number of edges.
 */
public interface SimpleEdgeRestriction extends EdgeRestriction {

	/**
	 * Returns the number of expected nos.
	 * <p>
	 * This is complementary to {@link #getExpectedYeses()} since expecting at least (resp. at most) a certain
	 * number of
	 * yeses implies expecting at most (resp. at least) a certain number of nos among the given set of edges.
	 *
	 * @return the number of expected nos (at most/least)
	 */
	int getExpectedNos();

}
