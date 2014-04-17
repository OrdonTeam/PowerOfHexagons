package com.ordonteam.powerofhexagons.input;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;
import static com.ordonteam.powerofhexagons.util.Util.getHalfWidth;
import static java.lang.Math.atan2;
import static java.lang.Math.toDegrees;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.ordonteam.powerofhexagons.view.Board;

public class InputHandler implements OnTouchListener {

	private float touchX;
	private float touchY;

	private Board board;

	public InputHandler(Board board) {
		this.board = board;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case ACTION_DOWN:
			touchX = event.getX();
			touchY = event.getY();
			break;
		case ACTION_UP:
			float dx = touchX - event.getX();
			float dy = touchY - event.getY();
			if (isInsensivityLevelCrossed(dx, dy)) {
				double angle = toDegrees(atan2(dy, dx)); // 0 is west, 90 is
															// north and so on
				if (angle < -30) {
					angle += 360;
				}
				moveAccordingTo(angle);
			}
			break;
		}
		return true;
	}

	private boolean isInsensivityLevelCrossed(float dx, float dy) {
		return Math.sqrt(dx * dx + dy * dy) > getHalfWidth() / 5;
	}

	private void moveAccordingTo(double angle) {
		if (0 - 30 < angle && angle < 0 + 30) {
			board.moveLeft();
		} else if (60 - 30 < angle && angle < 60 + 30) {
			board.moveUpLeft();
		} else if (120 - 30 < angle && angle < 120 + 30) {
			board.moveUpRight();
		} else if (180 - 30 < angle && angle < 180 + 30) {
			board.moveRight();
		} else if (240 - 30 < angle && angle < 240 + 30) {
			board.moveDownRight();
		} else if (300 - 30 < angle && angle < 300 + 30) {
			board.moveDownLeft();
		}
	}

}
