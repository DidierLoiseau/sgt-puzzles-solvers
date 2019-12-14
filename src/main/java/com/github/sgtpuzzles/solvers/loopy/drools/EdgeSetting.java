package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Edge;
import com.github.sgtpuzzles.solvers.loopy.LineStatus;
import lombok.Data;

@Data
public class EdgeSetting {
	private final Edge edge;
	private final LineStatus status;
}
