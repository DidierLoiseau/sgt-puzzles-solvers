package com.github.sgtpuzzles.solvers.loopy.drools;

import lombok.experimental.SuperBuilder;

public interface AtMost extends EdgeRestriction {
	static AtMostImpl.AtMostImplBuilder<?, ?> atMost(int expectedYeses) {
		return AtMostImpl.builder().expectedYeses(expectedYeses);
	}
}

@SuperBuilder
class AtMostImpl extends AbstractEdgeRestriction implements AtMost {
}