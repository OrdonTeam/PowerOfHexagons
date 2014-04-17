package com.ordonteam.powerofhexagons.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Slider {

	protected Map<Coords, Field> fields = new HashMap<Coords, Field>();

	public Slider(Map<Coords, Field> fields) {
		for (Coords coords : fields.keySet()) {
			this.fields.put(coords, fields.get(coords).copy());
		}
	}
	
	public abstract Coords getNeighbour(Field field);

	public void slideAll(List<Field> sortedFields,
			Map<Coords, Field> nextBoard, Slider slider) {
		for (Field field : sortedFields) {
			slider.slide(nextBoard, field);
		}
	}

	private void slide(Map<Coords, Field> nextBoard, Field field) {
		Field neighbour = fields.get(getNeighbour(field));
		if (canMove(neighbour)) {
			field.moveValueTo(neighbour);
			slide(nextBoard, neighbour);
		} else if (field.canBeMergedWith(neighbour)) {
			field.mergeTo(neighbour);
		}
		putField(nextBoard, field);
	}

	private boolean canMove(Field neighbour) {
		return neighbour != null && neighbour.isEmpty();
	}

	private void putField(Map<Coords, Field> nextBoard, Field field) {
		nextBoard.put(field.getCoords(), field);
	}
}