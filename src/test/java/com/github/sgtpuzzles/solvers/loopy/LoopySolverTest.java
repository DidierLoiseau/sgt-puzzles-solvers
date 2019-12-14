package com.github.sgtpuzzles.solvers.loopy;

import com.github.sgtpuzzles.grid.generators.SquareGridGenerator;
import com.github.sgtpuzzles.grid.model.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.github.sgtpuzzles.solvers.loopy.LineStatus.LINE_NO;
import static com.github.sgtpuzzles.solvers.loopy.LineStatus.LINE_YES;
import static org.assertj.core.api.Assertions.assertThat;

public class LoopySolverTest {
	private final LoopySolver solver = new LoopySolver();
	private final SquareGridGenerator generator = new SquareGridGenerator();

	@Test
	public void solveSimpleAllYes() {
		// given
		var graph = singleSquareGraph();
		var clues = Map.of(0, 4);

		// when
		List<Move> moves = solver.solve(graph, clues);

		// then
		assertThat(moves).containsExactlyInAnyOrder(
				graph.getEdges().stream()
						.map(e -> new Move(e, LINE_YES))
						.toArray(Move[]::new));
	}

	@Test
	public void solveSimpleAllNo() {
		// given
		var graph = singleSquareGraph();
		var clues = Map.of(0, 0);

		// when
		List<Move> moves = solver.solve(graph, clues);

		// then
		assertThat(moves).containsExactlyInAnyOrder(
				graph.getEdges().stream()
						.map(e -> new Move(e, LINE_NO))
						.toArray(Move[]::new));
	}

	@Test
	public void solveRemovesDanglingEdges() {
		// "dangling edges" are unknown edges connected to a vertex of remaining cardinality 1
		// given
		// ┌───┬───┐
		// │ 0 │   │
		// ├───┼───┤
		// │   │   │
		// └───┴───┘
		var graph = generator.generate(2, 2);
		var clues = Map.of(0, 0);

		// when
		List<Move> moves = solver.solve(graph, clues);

		// then
		// applying the 0 gives the following state (numbers are faceId's):
		//     ╶───┐
		//   0   1 │
		// ╷   ┌───┤
		// │ 2 │ 3 │
		// └───┴───┘
		assertThat(moves).contains(new Move(graph.getFaces().get(1).getEdges().get(0), LINE_NO));
		assertThat(moves).contains(new Move(graph.getFaces().get(1).getEdges().get(1), LINE_NO));
		assertThat(moves).contains(new Move(graph.getFaces().get(2).getEdges().get(2), LINE_NO));
		assertThat(moves).contains(new Move(graph.getFaces().get(2).getEdges().get(3), LINE_NO));
	}

	@Test
	public void solveExtendsLinesAccrossSinglePossibilityVertex() {
		// given
		// ┌───┬───┬───┐
		// │   │ 3 │   │
		// ├───┼───┼───┤
		// │   │ 0 │   │
		// ├───┼───┼───┤
		// │   │   │   │
		// └───┴───┴───┘
		var graph = generator.generate(3, 3);
		var clues = Map.of(1, 3, 4, 0);

		// when
		List<Move> moves = solver.solve(graph, clues);

		// then
		// applying the 0 & 3 gives the following state:
		//     ┏━━━┓
		//   0 ┃ 1 ┃ 2
		// ┌───┚   ┖───┐
		// │ 3   4   5 │
		// ├───┐   ┌───┤
		// │ 6 │ 7 │ 8 │
		// └───┴───┴───┘
		// We want the lines to be set around the 3 and 5
		assertThat(moves).contains(new Move(graph.getFaces().get(3).getEdges().get(0), LINE_YES));
		assertThat(moves).contains(new Move(graph.getFaces().get(3).getEdges().get(3), LINE_YES));
		assertThat(moves).contains(new Move(graph.getFaces().get(5).getEdges().get(0), LINE_YES));
		assertThat(moves).contains(new Move(graph.getFaces().get(5).getEdges().get(1), LINE_YES));
	}

	private Graph singleSquareGraph() {
		return generator.generate(1, 1);
	}
}
