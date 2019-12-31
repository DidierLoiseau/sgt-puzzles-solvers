package com.github.sgtpuzzles.solvers.loopy.drools;

import com.github.sgtpuzzles.grid.model.Face;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class ConnectedFacesGroup {
	private static int nextId = 0;
	@EqualsAndHashCode.Include
	private final int id = nextId++;
	private final Set<Face> faces = new HashSet<>();
	private ConnectedFacesGroup oppositeGroup = null;

	public ConnectedFacesGroup(Face initial) {
		faces.add(initial);
	}

	public Optional<ConnectedFacesGroup> getOppositeGroupOptional() {
		return Optional.ofNullable(oppositeGroup);
	}

	/**
	 * Merges this group with the given group.
	 *
	 * @param other the group to merge with
	 * @return {@code true} if the other's group {@link #oppositeGroup} should be deleted
	 */
	public boolean mergeWith(ConnectedFacesGroup other) {
		faces.addAll(other.getFaces());
		return other.getOppositeGroupOptional()
				.map(this::mergeOppositeGroup)
				.orElse(false);
	}

	/**
	 * Marks the given group as opposite group of this group.
	 *
	 * @param other the group to mark as opposite
	 * @return {@code true) if {@code other} should be deleted. Its {@link #oppositeGroup} (if any) should always be
	 * deleted, because it will be merged with {@code this} group.
	 */
	public boolean markAsOpposite(ConnectedFacesGroup other) {
		return other.getOppositeGroupOptional()
				.map(this::mergeWith)
				.orElseGet(() -> mergeOppositeGroup(other));
	}

	/**
	 * Just merges the given group with {@code this}' {@link #oppositeGroup}.
	 *
	 * @param other another group
	 * @return {@code true} if {@code other} should be deleted afterwards
	 */
	private boolean mergeOppositeGroup(ConnectedFacesGroup other) {
		if (other == oppositeGroup) {
			return false;
		}
		if (oppositeGroup == null) {
			connectWithOpposite(other);
			return false;
		} else {
			oppositeGroup.addFaces(other);
			return true;
		}
	}

	private void connectWithOpposite(ConnectedFacesGroup other) {
		oppositeGroup = other;
		other.oppositeGroup = this;
	}

	private void addFaces(ConnectedFacesGroup other) {
		faces.addAll(other.getFaces());
	}

	@Override
	public String toString() {
		return "ConnectedFacesGroup(" + id
				+ ",opposite=" + (oppositeGroup == null ? null : oppositeGroup.getId())
				+ ",faces=" + faces + ")";
	}
}
