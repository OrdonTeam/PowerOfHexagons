package com.ordonteam.powerofhexagons.util;

import static java.lang.Math.log;

import java.util.HashMap;
import java.util.Map;

import com.ordonteam.powerofhexagons.board.Coords;
import com.ordonteam.powerofhexagons.board.Field;

public class Util {
	
	private static int fontSize;
	private static int halfWidth;
	private static int halfHeight;
	private static int side;

	
	public static final float SQRT_3 = (float) Math.sqrt(3);
	
	public static final double log2(double value) {
		return log(value) / log(2);
	}

	public static int getFontSize() {
		return fontSize;
	}

	public static void setFontSize(int fontSize) {
		Util.fontSize = fontSize;
	}

	public static int getHalfWidth() {
		return halfWidth;
	}

	public static void setHalfWidth(int halfWidth) {
		Util.halfWidth = halfWidth;
	}

	public static int getHalfHeight() {
		return halfHeight;
	}

	public static void setHalfHeight(int halfHeight) {
		Util.halfHeight = halfHeight;
	}

	public static int getSide() {
		return side;
	}

	public static void setSideBasedOnWidth(int w) {
		Util.side = (int) (w / (5 * SQRT_3));
	}

	public static Map<Coords, Field> getNewEmptyBoard() {
		HashMap<Coords, Field> fields = new HashMap<Coords, Field>();
		addNewField(0, 2, 0, fields);
		addNewField(0, 3, 1, fields);
		addNewField(0, 4, 2, fields);
		addNewField(1, 1, 0, fields);
		addNewField(1, 2, 1, fields);
		addNewField(1, 3, 2, fields);
		addNewField(1, 4, 3, fields);
		addNewField(2, 0, 0, fields);
		addNewField(2, 1, 1, fields);
		addNewField(2, 2, 2, fields);
		addNewField(2, 3, 3, fields);
		addNewField(2, 4, 4, fields);
		addNewField(3, 0, 1, fields);
		addNewField(3, 1, 2, fields);
		addNewField(3, 2, 3, fields);
		addNewField(3, 3, 4, fields);
		addNewField(4, 0, 2, fields);
		addNewField(4, 1, 3, fields);
		addNewField(4, 2, 4, fields);
		return fields;
	}

	private static void addNewField(int x, int y, int z, Map<Coords, Field> fields) {
		Field newField = new Field(new Coords(x, y, z));
		fields.put(newField.getCoords(), newField);
	}
}
