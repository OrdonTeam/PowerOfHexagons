package com.ordonteam.powerofhexagons.board;

import java.io.Serializable;

import com.ordonteam.powerofhexagons.view.TileDrawer;

import android.graphics.Canvas;

public class Field implements Serializable {

	private static final long serialVersionUID = -2792414335624420536L;

	private final Coords coords;
	private int value;
	private boolean canBeMerged = true;
	private boolean isNew = false;

	public Field(Coords coords) {
		this(coords, 0);
	}

	public Field(Coords coords, int value) {
		this.coords = coords;
		this.value = value;
	}

	public Coords getCoords() {
		return coords;
	}

	public boolean isEmpty() {
		return value == 0;
	}

	public void setInitialValue(int augend) {
		value = augend;
	}

	public void clear() {
		value = 0;
		isNew = false;
	}

	public void draw(Canvas canvas) {
		new TileDrawer(coords, value, isNew).drawTile(canvas);
	}

	public boolean canBeMergedWith(Field neighbour) {
		if(neighbour == null)
			return false;
		if (!canBeMerged || !neighbour.canBeMerged)
			return false;
		return value == neighbour.value;
	}

	public boolean hasChangedSince(Field oldField) {
		return value != oldField.value;
	}

	public void setNew() {
		isNew = true;
	}

	public int getValue() {
		return value;
	}
	
	public void moveValueTo(Field neighbour) {
		neighbour.value += value;
		value = 0;
	}
	
	public void mergeTo(Field neighbour) {
		neighbour.canBeMerged = false;
		neighbour.value += value;
		value = 0;		
	}
	//Delegate method
	public int compareX(Field rhs) {
		return coords.compareX(rhs.coords);
	}

	public int compareY(Field rhs) {
		return coords.compareY(rhs.coords);
	}

	public int compareZ(Field rhs) {
		return coords.compareZ(rhs.coords);
	}

	public Coords rightNeighbourCoords() {
		return coords.rightNeighbour();
	}

	public Coords leftNeighbourCoords() {
		return coords.leftNeighbour();
	}

	public Coords downRightNeighbourCoords() {
		return coords.downRightNeighbour();
	}

	public Coords upLeftNeighbourCoords() {
		return coords.upLeftNeighbour();
	}

	public Coords downLeftNeighbourCoords() {
		return coords.downLeftNeighbour();
	}

	public Coords upRightNeighbourCoords() {
		return coords.upRightNeighbour();
	}

	public Field copy() {
		return new Field(coords.copy(), value);
	}

	@Override
	public String toString() {
		return coords.toString() + " " + value;
	}

	public static Field fromString(String line) {
		String[] split = line.split(" ");
		if (split.length != 4)
			throw new FieldCreationException(line);
		return new Field(new Coords(Integer.valueOf(split[0]),
				Integer.valueOf(split[1]), Integer.valueOf(split[2])),
				Integer.valueOf(split[3]));
	}

	public static class FieldCreationException extends RuntimeException {
		private static final long serialVersionUID = -687622583395375041L;

		public FieldCreationException(String line) {
			super("Wrong field format: " + line);
		}
	}
}
