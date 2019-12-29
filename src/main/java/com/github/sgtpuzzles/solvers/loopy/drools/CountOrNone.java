package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Edge;
import com.github.sgtpuzzles.solvers.loopy.LineStatus;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

import static com.github.sgtpuzzles.solvers.loopy.drools.AtLeast.atLeast;
import static com.github.sgtpuzzles.solvers.loopy.drools.AtMost.atMost;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CountOrNone extends AbstractEdgeRestriction {

	public CountOrNone(Set<Edge> edges, int expectedYeses) {
		super(edges, expectedYeses);
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
}
