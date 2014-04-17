package com.ordonteam.powerofhexagons.view;

import static com.ordonteam.powerofhexagons.util.Util.getFontSize;
import static com.ordonteam.powerofhexagons.util.Util.getHalfHeight;
import static com.ordonteam.powerofhexagons.util.Util.getHalfWidth;
import static com.ordonteam.powerofhexagons.util.Util.setHalfHeight;
import static com.ordonteam.powerofhexagons.util.Util.setHalfWidth;
import static com.ordonteam.powerofhexagons.util.Util.setSideBasedOnWidth;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.View;

import com.ordonteam.powerofhexagons.board.Coords;
import com.ordonteam.powerofhexagons.board.Field;
import com.ordonteam.powerofhexagons.board.Field.FieldCreationException;
import com.ordonteam.powerofhexagons.board.FieldMover;
import com.ordonteam.powerofhexagons.util.Util;

public class Board extends View {

	private static final int BACKGROUND_COLOR = Color.argb(255, 210, 210, 210);

	private Map<Coords, Field> fields = new HashMap<Coords, Field>();
	private Random random = new Random();
	private ScoreCalculator scoreCalculator;

	public Board(Activity context) {
		super(context);
		scoreCalculator = new ScoreCalculator(context);
		fields = Util.getNewEmptyBoard();
		addRandom();
		addRandom();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		setHalfWidth(w / 2);
		setHalfHeight(h / 2);
		setSideBasedOnWidth(w);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawColor(BACKGROUND_COLOR);

		for (Field field : fields.values()) {
			field.draw(canvas);
		}

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(getFontSize());
		paint.setTextAlign(Align.CENTER);
		paint.setAntiAlias(true);
		canvas.drawText("Score", getHalfWidth() / 2, getHalfHeight() * 1.7f,
				paint);
		canvas.drawText("" + scoreCalculator.getScore(), getHalfWidth() / 2,
				getHalfHeight() * 1.7f + getFontSize(), paint);
		canvas.drawText("High score ", 3 * getHalfWidth() / 2,
				getHalfHeight() * 1.7f, paint);
		canvas.drawText("" + scoreCalculator.getMaxScore(),
				3 * getHalfWidth() / 2, getHalfHeight() * 1.7f + getFontSize(),
				paint);
	}

	public void addRandom() {
		List<Field> emptyFields = getEmptyFields();
		int size = emptyFields.size();
		if (size > 0) {
			Field field = emptyFields.get(random.nextInt(size));
			field.setInitialValue(random.nextBoolean() ? 2 : 4);
			field.setNew();
		}
		invalidate();
	}

	public void clear() {
		for (Field field : fields.values()) {
			field.clear();
		}
		addRandom();
		addRandom();
		scoreCalculator.calculateScore(fields.values());
		invalidate();
	}

	private List<Field> getEmptyFields() {
		List<Field> emptyFields = new ArrayList<Field>();
		for (Field field : fields.values()) {
			if (field.isEmpty())
				emptyFields.add(field);
		}
		return emptyFields;
	}

	public void moveRight() {
		Map<Coords, Field> nextBoard = new FieldMover(fields) {
			@Override
			public int compare(Field lhs, Field rhs) {
				return -lhs.compareY(rhs);
			}

			@Override
			public Coords getNeighbour(Field field) {
				return field.rightNeighbourCoords();
			}
		}.getNextBoard();
		doMove(nextBoard);
	}

	public void moveLeft() {
		Map<Coords, Field> nextBoard = new FieldMover(fields) {
			@Override
			public int compare(Field lhs, Field rhs) {
				return lhs.compareY(rhs);
			}

			@Override
			public Coords getNeighbour(Field field) {
				return field.leftNeighbourCoords();
			}
		}.getNextBoard();
		doMove(nextBoard);
	}

	public void moveDownRight() {
		Map<Coords, Field> nextBoard = new FieldMover(fields) {
			@Override
			public int compare(Field lhs, Field rhs) {
				return -lhs.compareZ(rhs);
			}

			@Override
			public Coords getNeighbour(Field field) {
				return field.downRightNeighbourCoords();
			}
		}.getNextBoard();
		doMove(nextBoard);
	}

	public void moveUpLeft() {
		Map<Coords, Field> nextBoard = new FieldMover(fields) {
			@Override
			public int compare(Field lhs, Field rhs) {
				return lhs.compareZ(rhs);
			}

			@Override
			public Coords getNeighbour(Field field) {
				return field.upLeftNeighbourCoords();
			}
		}.getNextBoard();
		doMove(nextBoard);
	}

	public void moveDownLeft() {
		Map<Coords, Field> nextBoard = new FieldMover(fields) {
			@Override
			public int compare(Field lhs, Field rhs) {
				return lhs.compareY(rhs);
			}

			@Override
			public Coords getNeighbour(Field field) {
				return field.downLeftNeighbourCoords();
			}
		}.getNextBoard();
		doMove(nextBoard);
	}

	public void moveUpRight() {
		Map<Coords, Field> nextBoard = new FieldMover(fields) {
			@Override
			public int compare(Field lhs, Field rhs) {
				return -lhs.compareY(rhs);
			}

			@Override
			public Coords getNeighbour(Field field) {
				return field.upRightNeighbourCoords();
			}
		}.getNextBoard();
		doMove(nextBoard);
	}

	private void doMove(Map<Coords, Field> nextBoard) {
		if (boardHasChanged(nextBoard)) {
			fields = nextBoard;
			addRandom();
			scoreCalculator.calculateScore(fields.values());
			invalidate();
		}
	}

	private boolean boardHasChanged(Map<Coords, Field> nextBoard) {
		for (Coords coords : fields.keySet()) {
			if (hasChanged(nextBoard, coords)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasChanged(Map<Coords, Field> nextBoard, Coords coords) {
		return fields.get(coords).hasChangedSince(nextBoard.get(coords));
	}

	public void save(Context context) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(context.openFileOutput("savedData",
					Context.MODE_PRIVATE));
			out.writeObject(fields);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void load(Activity context) {
		ObjectInputStream inputStream = null;
		try {
			inputStream = new ObjectInputStream(
					context.openFileInput("savedData"));
			this.fields = (Map<Coords, Field>) inputStream.readObject();
			scoreCalculator.calculateScore(fields.values());
		} catch (FileNotFoundException e) {
			tryOldWay(context.getPreferences(Context.MODE_PRIVATE));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
			}
		}
	}

	// This is for 1.0 Version
	private void tryOldWay(SharedPreferences sharedPreferences) {
		String stringSet = sharedPreferences.getString("fields", null);
		if (stringSet != null) {
			fields.clear();
			for (String field : stringSet.split("\n")) {
				try {
					Field fromString = Field.fromString(field);
					fields.put(fromString.getCoords(), fromString);
				} catch (FieldCreationException e) {
					// Empty line?
				}
			}
			scoreCalculator.calculateScore(fields.values());
		}
	}

}
