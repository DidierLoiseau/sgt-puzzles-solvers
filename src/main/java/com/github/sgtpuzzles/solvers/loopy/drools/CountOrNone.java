package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Edge;
import com.github.sgtpuzzles.solvers.loopy.LineStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

import static com.github.sgtpuzzles.solvers.loopy.drools.AtLeast.atLeast;
import static com.github.sgtpuzzles.solvers.loopy.drools.AtMost.atMost;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
public class CountOrNone extends AbstractEdgeRestriction {
	/**
	 * The set of edges that will be set iff {@code this}' edges are not set. Can only be set if
	 * {@link #isAllOrNone()}.
	 */
	@Getter
	private CountOrNone complement;

	public CountOrNone(Set<Edge> edges, int expectedYeses) {
		this(edges, expectedYeses, null);
	}

	public CountOrNone(Set<Edge> edges, int expectedYeses, CountOrNone complement) {
		super(edges, expectedYeses);
		this.complement = complement;
	}

	@Override
	public void recordSetting(EdgeSetting setting) {
		if (setting.getStatus() == LineStatus.LINE_NO) {
			if (!edges.remove(setting.getEdge())) {
				throw new IllegalArgumentException("This Excatly does not contain setting's edge " + setting.getEdge());
			}
		} else {
			// Rule "CountOrNone becomes count" should replace this fact by AtLeast + AtMost
			// This should not be called.
			throw new IllegalArgumentException("Cannot remove edge for LINE_YES");
		}
	}

	public boolean isAllOrNone() {
		return edges.size() == expectedYeses;
	}

	public AtLeast toAtLeast() {
		return atLeast(expectedYeses).amongEdges(edges);
	}

	public AtMost toAtMost() {
		return atMost(expectedYeses).amongEdges(edges);
	}

	public CountOrNone createComplement(Set<Edge> edges) {
		checkAllOrNone();
		return complement = new CountOrNone(edges, edges.size(), this);
	}

	public void addEdges(Set<Edge> edges) {
		checkAllOrNone();
		this.edges.addAll(edges);
		expectedYeses = edges.size();
	}

	private void checkAllOrNone() {
		if (!isAllOrNone()) {
			throw new IllegalStateException("Not an all-or-none");
		}
	}
}
