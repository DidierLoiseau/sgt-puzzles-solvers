package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Edge;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ToString(callSuper = true)
public class Exactly extends AbstractEdgeRestriction implements AtLeast, AtMost {

	public Exactly(Collection<Edge> edges, int expectedYeses) {
		super(edges, expectedYeses);
	}

	public static ExactlyBuilder<?, ?> exactly(int expectedYeses) {
		return builder().expectedYeses(expectedYeses);
	}
}
