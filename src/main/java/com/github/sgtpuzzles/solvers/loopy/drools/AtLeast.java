package com.github.sgtpuzzles.solvers.loopy.drools;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

public interface AtLeast extends EdgeRestriction {
	static AtLeastImpl.AtLeastImplBuilder<?, ?> atLeast(int expectedYeses) {
		return AtLeastImpl.builder().expectedYeses(expectedYeses);
	}
}

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ToString(callSuper = true)
class AtLeastImpl extends AbstractEdgeRestriction implements AtLeast {
}