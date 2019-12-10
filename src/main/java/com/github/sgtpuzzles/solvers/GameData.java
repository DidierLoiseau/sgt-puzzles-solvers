package com.github.sgtpuzzles.solvers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameData<P> {
	private final GameType type;
	private final P params;
	private final String desc;
	private final DeserializeData rawData;
}
