package com.ordonteam.powerofhexagons.view;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import android.graphics.Path;

public class Hexagon {
	
	public static Path getHexPath(float side) {
		Path path = new Path();
		path.moveTo(0, side);
		for (float angle = 0; angle < 2 * PI; angle += 2 * PI / 6) {
			float x = (float) (sin(angle) * side);
			float y = (float) (cos(angle) * side);
			path.lineTo(x, y);
		}
		path.close();
		return path;
	}
}
