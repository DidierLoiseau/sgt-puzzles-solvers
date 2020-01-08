package com.github.sgtpuzzles.grid.generators;

import com.github.sgtpuzzles.solvers.loopy.params.LoopyType;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class GridGeneratorFactory {
	private final Map<LoopyType, GridGenerator> generators;

	public GridGeneratorFactory() {
		var reflections = new Reflections(getClass());
		generators = reflections.getSubTypesOf(GridGenerator.class).stream()
				.map(this::instanciate)
				.collect(toMap(GridGenerator::getType, Function.identity()));
	}

	@SneakyThrows
	private GridGenerator instanciate(Class<? extends GridGenerator> c) {
		return c.getConstructor().newInstance();
	}

	public GridGenerator getGenerator(LoopyType type) {
		return generators.get(type);
	}
}
