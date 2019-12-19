package com.github.sgtpuzzles.solvers.loopy;

import com.github.sgtpuzzles.grid.generators.SquareGridGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.github.sgtpuzzles.solvers.loopy.LineStatus.LINE_NO;
import static com.github.sgtpuzzles.solvers.loopy.LineStatus.LINE_YES;
import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;

// box drawing characters: https://en.wikipedia.org/wiki/Box-drawing_character#Unicode
public class LoopySolverTest {
	private final LoopySolver solver = new LoopySolver();
	private final SquareGridGenerator generator = new SquareGridGenerator();
	public static final Pattern VISUAL_CLUE_PATTERN = Pattern.compile("[ \u2500-\u257F] ([ \\d]) ");

	@Test
	public void solveSimpleAllYes() {
		// given
		var graph = generator.generate(1, 1);
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
		var graph = generator.generate(2, 2);
		var clues = Map.of(0, 0);

		// when
		List<Move> moves = solver.solve(graph, clues);

		// then
		assertThat(moves).contains(
				graph.getFace(0).getEdges().stream()
						.map(e -> new Move(e, LINE_NO))
						.toArray(Move[]::new));
	}

	@Test
	public void solveRemovesDanglingEdges() {
		// "dangling edges" are unknown edges connected to a vertex of remaining cardinality 1
		// given
		var graph = generator.generate(2, 2);
		var clues = parseClues("""
				    ╶╶╶╶┐
				  0     ╎
				╷   ┌───┤
				╎   │   │
				└╴╴╴┴───┘
				""");

		// when
		List<Move> moves = solver.solve(graph, clues);

		// then
		assertThat(moves).contains(
				new Move(graph.getFace(1).getEdge(0), LINE_NO),
				new Move(graph.getFace(1).getEdge(1), LINE_NO),
				new Move(graph.getFace(2).getEdge(2), LINE_NO),
				new Move(graph.getFace(2).getEdge(3), LINE_NO));
	}

	@Test
	public void solveExtendsLinesAcrossSinglePossibilityVertex() {
		// given
		var graph = generator.generate(3, 3);
		var clues = parseClues("""
				    ┏━━━┓
				    ┃ 3 ┃     // 0-2
				┏╸╸╸┛   ┗╺╺╺┓
				╏     0     ╏ // 3-5 – we want the edges around the corners to be set here
				┞───┐   ┌───┦
				│   │   │   │ // 6-8
				└───┴───┴───┘
				""");

		// when
		List<Move> moves = solver.solve(graph, clues);

		// then we want the lines to be set around the 3 and 5
		assertThat(moves).contains(new Move(graph.getFace(3).getEdge(0), LINE_YES),
				new Move(graph.getFace(3).getEdge(3), LINE_YES),
				new Move(graph.getFace(5).getEdge(0), LINE_YES),
				new Move(graph.getFace(5).getEdge(1), LINE_YES));
	}

	@Test
	public void solvePreventsFormingLoopsIfUnsatisfiedClues() {
		// given
		var graph = generator.generate(3, 3);
		var clues = parseClues("""
				        ┌───┐
				  0     │   │
				    ┏━━━┥   │
				  2 ┃   ╎ 2 │
				┏━━━┛   ┟───┘
				┃     2 ┃    
				┗━━━━━━━┛    
				""");

		// when
		List<Move> moves = solver.solve(graph, clues);

		// then we want to remove the edge at the right of the middle cell
		assertThat(moves).contains(new Move(graph.getFace(4).getEdge(1), LINE_NO));
	}

	@Test
	public void solveDetectsIndependentlyConnectedLines() {
		// given
		var graph = generator.generate(7, 7);
		var clues = parseClues("""
				┌───┬───┮━━━┓   ┏━━━┭───┬───┐
				│   │   │   ┃   ┃   │   │   │ // 0-6
				├───┼───┘   ┗━━━┛   └───┼───┤
				│   │     0   2   0     │   │ // 7-13
				┟───┘       ┏━━━┓   ┌───┼───┤
				┃     0     ┃   ┃   │   │   │ // 14-20
				┗━━━┓   ┏━━━┛   ┗╍╍╍┵───┼───┤
				    ┃ 2 ┃       ╎   ╎   │   │ // 21-27 – we want to prevent the closed loop here
				┏━━━┛   ┗━━━┓   ┏╍╍╍┭───┼───┤
				┃     0     ┃   ┃   │   │   │ // 28-34
				┞───┐       ┗━━━┛   └───┼───┤
				│   │     0   2   0     │   │ // 35-41
				├───┼───┐   ┏━━━┓   ┌───┼───┤
				│   │   │   ┃   ┃   │   │   │ // 42-48
				└───┴───┶━━━┛   ┗━━━┵───┴───┘
				""");

		// when
		List<Move> moves = solver.solve(graph, clues);

		// then we want to remove the 2 ╎ and add the 2 ╍╍╍
		assertThat(moves).contains(
				new Move(graph.getFace(25).getEdge(0), LINE_YES),
				new Move(graph.getFace(25).getEdge(1), LINE_NO),
				new Move(graph.getFace(25).getEdge(2), LINE_YES),
				new Move(graph.getFace(25).getEdge(3), LINE_NO));
	}

	@Test
	public void solveInfersRequiredFaceEdges() {
		// given
		var graph = generator.generate(2, 2);
		var clues = parseClues("""
				┏━━━┭───┐
				┃ 3 │   │
				┞───┼───┤
				│   │   │
				└───┴───┘
				""");

		// when25
		List<Move> moves = solver.solve(graph, clues);

		// then we want the top-left corner to be set
		assertThat(moves).contains(
				new Move(graph.getFace(0).getEdge(0), LINE_YES),
				new Move(graph.getFace(0).getEdge(3), LINE_YES));
	}

	@Test
	public void solveExcludesImpossibleFaceEdges() {
		// given
		var graph = generator.generate(2, 2);
		var clues = parseClues("""
				┌╴╴╴┬───┐
				╎ 1 │   │
				├───┼───┤
				│   │   │
				└───┴───┘
				""");

		// when25
		List<Move> moves = solver.solve(graph, clues);

		// then we want the top left corner to be removed
		assertThat(moves).contains(
				new Move(graph.getFace(0).getEdge(0), LINE_NO),
				new Move(graph.getFace(0).getEdge(3), LINE_NO));
	}

	@Test
	public void solveDetectsNestedExactly() {
		// given
		var graph = generator.generate(3, 3);
		var clues = parseClues("""
				┏━━━┭───┰───┐
				┃ 3 │ 3 ╏   │ // 0-2
				┞───┾╸╸╸┩╴╴╴┤
				│   │   ╎   │ // 3-5
				├───┼───┼───┤
				│   │   │   │ // 6-8
				└───┴───┴───┘
				""");

		// when25
		List<Move> moves = solver.solve(graph, clues);

		// then we want the top-left corner to be set
		assertThat(moves).contains(
				new Move(graph.getFace(1).getEdge(1), LINE_YES),
				new Move(graph.getFace(1).getEdge(2), LINE_YES));
	}

	private Map<Integer, Integer> parseClues(String visual) {
		var lines = visual.split("\n");
		var result = new HashMap<Integer, Integer>();
		var faceId = 0;
		for (int i = 0; i < lines.length; i++) {
			if (i % 2 == 1) {
				var cluePart = lines[i].split("//", 2)[0];
				var matcher = VISUAL_CLUE_PATTERN.matcher(cluePart);
				while (matcher.find()) {
					var clue = matcher.group(1);
					if (!clue.equals(" ")) {
						result.put(faceId, parseInt(clue));
					}
					faceId++;
				}
			}
		}
		return result;
	}
}
