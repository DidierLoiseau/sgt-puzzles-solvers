package com.github.sgtpuzzles.solvers;

import lombok.Getter;

@Getter
public enum SaveKeys {
	/**
	 * Save format version
	 */
	VERSION,
	/**
	 * The game type (e.g. Loopy, Magnets etc.)
	 */
	GAME,
	/**
	 * The parameters of the puzzle (e.g. size, difficulty, sub-type etc.)
	 */
	PARAMS,
	/**
	 * Encoded description of the puzzle (clues) based on the above params
	 */
	DESC,
	/**
	 * Number of states in the file
	 */
	NSTATES,
	/**
	 * Current state number (1-based, 1 = initial)
	 */
	STATEPOS,
	/**
	 * An encoded move
	 */
	MOVE(true),
	/**
	 * A request to solve the game
	 */
	SOLVE(true),
	/**
	 * A request to restart the game
	 */
	RESTART(true),
	;

	private final boolean move;

	SaveKeys() {
		move = false;
	}

	SaveKeys(boolean move) {
		this.move = move;
	}
}
