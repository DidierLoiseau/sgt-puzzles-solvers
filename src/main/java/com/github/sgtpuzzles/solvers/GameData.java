package com.github.sgtpuzzles.solvers;

import com.github.sgtpuzzles.solvers.loopy.Move;
import lombok.Builder;
import lombok.Data;

import java.io.BufferedWriter;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class GameData<P> {
	private final GameType type;
	private final P params;
	private final String desc;
	private final DeserializeData rawData;
	private List<Move> moves;

	public void serializeTo(BufferedWriter writer) {
		syncMoves();
		rawData.serializeTo(writer);
	}

	private void syncMoves() {
		if (!moves.isEmpty()) {
			rawData.setMoves(
					moves.stream()
							.map(m -> new DeserializeLine(SaveKeys.MOVE.name(), m.toSerializedState()))
							.collect(Collectors.toList()));
		}
	}
}
