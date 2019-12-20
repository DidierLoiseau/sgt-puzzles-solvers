package com.github.sgtpuzzles.solvers.loopy.drools;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

public interface AtMost extends EdgeRestriction {
	static AtMostImpl.AtMostImplBuilder<?, ?> atMost(int expectedYeses) {
		return AtMostImpl.builder().expectedYeses(expectedYeses);
	}
}

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ToString(callSuper = true)
class AtMostImpl extends AbstractEdgeRestriction implements AtMost {
}