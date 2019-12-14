package com.github.sgtpuzzles.solvers.loopy.drools;

import lombok.experimental.SuperBuilder;

public interface AtLeast extends EdgeRestriction {
	static AtLeastImpl.AtLeastImplBuilder<?, ?> atLeast(int expectedYeses) {
		return AtLeastImpl.builder().expectedYeses(expectedYeses);
	}
}

@SuperBuilder
class AtLeastImpl extends AbstractEdgeRestriction implements AtLeast {}