package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Face;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
public class ConnectedFacesGroup extends AssociatedObjectsGroup<Face, ConnectedFacesGroup> {

	public ConnectedFacesGroup(Face initial) {
		super(initial);
	}

	public Set<Face> getFaces() {
		return getObjects();
	}
}
