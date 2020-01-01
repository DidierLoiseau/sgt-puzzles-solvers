package com.github.sgtpuzzles.solvers.loopy.drools;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public abstract class AssociatedObjectsGroup<T, G extends AssociatedObjectsGroup<T, G>> {
	private static int nextId = 0;
	@EqualsAndHashCode.Include
	private final int id = nextId++;
	private final Set<T> objects = new HashSet<>();
	private G oppositeGroup = null;

	public AssociatedObjectsGroup(T initial) {
		objects.add(initial);
	}

	public Optional<G> getOppositeGroupOptional() {
		return Optional.ofNullable(oppositeGroup);
	}

	/**
	 * Merges this group with the given group.
	 *
	 * @param other the group to merge with
	 * @return {@code true} if the other's group {@link #oppositeGroup} should be deleted
	 */
	public boolean mergeWith(G other) {
		objects.addAll(other.getObjects());
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
	public boolean markAsOpposite(G other) {
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
	private boolean mergeOppositeGroup(G other) {
		if (other == oppositeGroup) {
			return false;
		}
		if (oppositeGroup == null) {
			connectWithOpposite(other);
			return false;
		} else {
			((AssociatedObjectsGroup<T, G>) oppositeGroup).addObjects(other);
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	private void connectWithOpposite(G other) {
		oppositeGroup = other;
		((AssociatedObjectsGroup<T, G>) other).oppositeGroup = (G) this;
	}

	public void disconnectOpposite() {
		oppositeGroup = null;
	}

	private void addObjects(G other) {
		objects.addAll(other.getObjects());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + id
				+ ",opposite=" + (oppositeGroup == null ? null : oppositeGroup.getId())
				+ ",objects=" + objects + ")";
	}
}
