package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Edge;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder
@ToString
@Slf4j
public abstract class AbstractEdgeRestriction implements EdgeRestriction {
	private static int nextId = 0;
	private final int id = nextId++;
	@EqualsAndHashCode.Include
	protected final Set<Edge> edges;
	@EqualsAndHashCode.Include
	protected int expectedYeses;

	public AbstractEdgeRestriction(Collection<Edge> edges, int expectedYeses) {
		this.edges = new HashSet<>(edges);
		this.expectedYeses = expectedYeses;
		log.debug("Created {}", this);
	}

}
