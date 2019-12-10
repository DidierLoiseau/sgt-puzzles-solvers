package com.github.sgtpuzzles.solvers.loopy.params;

public enum LoopyType {
	SQUARE("Squares", 3, 3),
	TRIANGULAR("Triangular", 3, 3),
	HONEYCOMB("Honeycomb", 3, 3),
	SNUBSQUARE("Snub-Square", 3, 3),
	CAIRO("Cairo", 3, 4),
	GREATHEXAGONAL("Great-Hexagonal", 3, 3),
	OCTAGONAL("Octagonal", 3, 3),
	KITE("Kites", 3, 3),
	FLORET("Floret", 1, 2),
	DODECAGONAL("Dodecagonal", 2, 2),
	GREATDODECAGONAL("Great-Dodecagonal", 2, 2),
	PENROSE_P2("Penrose (kite/dart)", 3, 3),
	PENROSE_P3("Penrose (rhombs)", 3, 3),
	GREATGREATDODECAGONAL("Great-Great-Dodecagonal", 2, 2),
	KAGOME("Kagome", 3, 3),
	;

	private final String name;
	private final int amin;
	private final int omin;

	LoopyType(final String name, final int amin, final int omin) {
		this.name = name;
		this.amin = amin;
		this.omin = omin;
	}
}
