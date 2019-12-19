package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Edge;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@SuperBuilder
@ToString(callSuper = true)
public class Exactly extends AbstractEdgeRestriction implements AtLeast, AtMost {

	public Exactly(Collection<Edge> edges, int expectedYeses) {
		super(edges, expectedYeses);
	}

	public Exactly(Collection<Edge> edges, int expectedYeses, EdgeRestriction parent) {
		super(edges, expectedYeses, parent);
	}

	public static ExactlyBuilder<?, ?> exactly(int expectedYeses) {
		return builder().expectedYeses(expectedYeses);
	}
}
