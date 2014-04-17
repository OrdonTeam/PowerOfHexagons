package com.ordonteam.powerofhexagons.view;

import static android.graphics.Paint.Align.CENTER;
import static android.graphics.Paint.Style.FILL;
import static android.graphics.Paint.Style.STROKE;
import static com.ordonteam.powerofhexagons.util.Util.SQRT_3;
import static com.ordonteam.powerofhexagons.util.Util.getHalfWidth;
import static com.ordonteam.powerofhexagons.util.Util.getSide;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.ordonteam.powerofhexagons.board.Coords;
import com.ordonteam.powerofhexagons.util.Util;

public class TileDrawer {

	private static final Paint fill = new Paint();
	private static final Paint stroke = new Paint();
	private static final Paint redStroke = new Paint();
	private static final Paint text = new Paint();

	static {
		fill.setColor(Color.RED);
		fill.setStyle(FILL);
		fill.setAntiAlias(true);

		stroke.setColor(Color.BLACK);
		stroke.setStrokeWidth(2.0f);
		stroke.setStyle(STROKE);
		stroke.setAntiAlias(true);

		redStroke.setColor(Color.RED);
		redStroke.setStrokeWidth(4.0f);
		redStroke.setStyle(STROKE);
		redStroke.setAntiAlias(true);

		text.setColor(Color.BLACK);
		text.setTextAlign(CENTER);
		text.setAntiAlias(true);
	}

	private Coords coords;
	private int value;
	private boolean isNew;

	public TileDrawer(Coords coords, int value, boolean isNew) {
		this.coords = coords;
		this.value = value;
		this.isNew = isNew;
	}

	public void drawTile(Canvas canvas) {
		drawTile(canvas, getHexagonCenterX(), getHexagonCenterY());
	}

	private float getHexagonCenterX() {
		return getHalfWidth()
				+ ((coords.getX() / 2f + coords.getY() - 3) * SQRT_3 * getSide());
	}

	private float getHexagonCenterY() {
		return getHalfWidth() + ((coords.getX() - 2) * 1.5f * getSide());
	}

	private void drawTile(Canvas canvas, float x, float y) {
		canvas.save();
		canvas.translate(x, y);
		drawHexagon(canvas);

		drawText(canvas);
		canvas.restore();
	}

	private void drawHexagon(Canvas canvas) {
		fill.setColor(Color.argb(255, div(1), div(4), div(16)));
		canvas.drawPath(Hexagon.getHexPath(getSide()), fill);
		if (isNew) {
			canvas.drawPath(Hexagon.getHexPath(getSide() - 2), redStroke);
		}
		canvas.drawPath(Hexagon.getHexPath(getSide()), stroke);
	}

	private int div(int i) {
		return (int) (255 - Util.log2(value + 1) / i % 4 * 255 / 5);
	}

	private void drawText(Canvas canvas) {
		if (value != 0) {
			String v = String.valueOf(value);
			setTextSize(v);
			float offsetY = -(text.descent() + text.ascent()) / 2;
			canvas.drawText(v, 0, offsetY, text);
		}
	}

	private void setTextSize(String v) {
		if (v.length() >= 5) {
			text.setTextSize(0.75f * Util.getFontSize());
		} else {
			text.setTextSize(Util.getFontSize());
		}
	}
}
