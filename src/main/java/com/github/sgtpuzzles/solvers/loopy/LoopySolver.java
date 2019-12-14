package com.github.sgtpuzzles.solvers.loopy;

import com.github.sgtpuzzles.grid.model.Graph;
import com.github.sgtpuzzles.solvers.loopy.drools.CountOrNone;
import com.github.sgtpuzzles.solvers.loopy.drools.Exactly;
import org.drools.core.command.runtime.rule.QueryCommand;
import org.kie.api.KieServices;
import org.kie.api.runtime.rule.QueryResults;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class LoopySolver {

	public List<Move> solve(Graph graph, Map<Integer, Integer> clues) {
		var kieServices = KieServices.Factory.get();
		var kContainer = kieServices.getKieClasspathContainer();

		var kieSession = kContainer.newKieSession();

		graph.getEdges().forEach(kieSession::insert);

		graph.getVertices().values().stream()
				.map(v -> new CountOrNone(new HashSet<>(v.getEdges().values()), 2))
				.forEach(kieSession::insert);
//		var edgeStates = graph.getEdges().stream()
//				.map(EdgeState::new)
//				.collect(toMap(EdgeState::getEdge, es -> es));
//		edgeStates.values().forEach(kieSession::insert);

		graph.getFaces().stream()
				.filter(f -> clues.containsKey(f.getId()))
				.map(f -> new Exactly(f.getEdges(), clues.get(f.getId())))
				.forEach(kieSession::insert);

		kieSession.fireAllRules();

		QueryResults result = kieSession.execute(new QueryCommand("move", "getMoves"));
		var moves = StreamSupport.stream(result.spliterator(), false)
				.map(r -> (Move) r.get("$move"))
				.collect(toList());

		kieSession.dispose();

		return moves;
	}
}
