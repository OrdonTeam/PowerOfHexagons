package com.ordonteam.powerofhexagons.board;

import java.io.Serializable;

public class Coords implements Serializable {

	private static final long serialVersionUID = 4311308650226421140L;
	
	private final int x;
	private final int y;
	private final int z;

	public Coords(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Coords copy() {
		return new Coords(x, y, z);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public int compareX(Coords rhs) {
		return Integer.valueOf(x).compareTo(rhs.x);
	}

	public int compareY(Coords rhs) {
		return Integer.valueOf(y).compareTo(rhs.y);
	}

	public int compareZ(Coords rhs) {
		return Integer.valueOf(z).compareTo(rhs.z);
	}

	public Coords rightNeighbour() {
		return new Coords(x, y + 1, z + 1);
	}

	public Coords leftNeighbour() {
		return new Coords(x, y - 1, z - 1);
	}

	public Coords downRightNeighbour() {
		return new Coords(x + 1, y, z + 1);
	}

	public Coords upLeftNeighbour() {
		return new Coords(x - 1, y, z - 1);
	}

	public Coords downLeftNeighbour() {
		return new Coords(x + 1, y - 1, z);
	}

	public Coords upRightNeighbour() {
		return new Coords(x - 1, y + 1, z);
	}
	
	@Override
	public String toString() {
		return x+" "+y+" "+z;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coords other = (Coords) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

}
