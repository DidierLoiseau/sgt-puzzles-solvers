package com.github.sgtpuzzles.solvers.loopy;

import com.github.sgtpuzzles.grid.model.Edge;
import lombok.Data;

@Data
public class Move {
	private final Edge edge;
	private final LineStatus status;

	public String toSerializedState() {
		return edge.getId() + status.getSerializedState();
	}
}
