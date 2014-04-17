package com.ordonteam.powerofhexagons.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FieldMover extends Slider implements Comparator<Field> {

	public FieldMover(Map<Coords, Field> fields) {
		super(fields);
	}

	public Map<Coords, Field> getNextBoard() {
		List<Field> sortedFields = getSortedFields(this);
		Map<Coords, Field> nextBoard = new HashMap<Coords, Field>();
		slideAll(sortedFields, nextBoard, this);
		return nextBoard;
	}

	private List<Field> getSortedFields(Comparator<Field> comparator) {
		List<Field> sortedFields = new ArrayList<Field>(fields.values());
		Collections.sort(sortedFields, comparator);
		return sortedFields;
	}
}