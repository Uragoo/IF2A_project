package application;

public class Controller {
	// Getting the numbers and the GRID from Tetris
	public static final int MOVE = Tetris.MOVE;
	public static int movespeed = Tetris.movespeed;
	public static final int SIZE = Tetris.SIZE;
	public static int XMAX = Tetris.XMAX;
	public static int YMAX = Tetris.YMAX;
	public static int[][] GRID = Tetris.GRID;

	public static void MoveRight(Shapes shape) {
		if (shape.a.getX() + MOVE <= XMAX - SIZE && shape.b.getX() + MOVE <= XMAX - SIZE
				&& shape.c.getX() + MOVE <= XMAX - SIZE && shape.d.getX() + MOVE <= XMAX - SIZE) {
			int movea = GRID[((int) shape.a.getX() / SIZE) + 1][((int) shape.a.getY() / SIZE)];
			int moveb = GRID[((int) shape.b.getX() / SIZE) + 1][((int) shape.b.getY() / SIZE)];
			int movec = GRID[((int) shape.c.getX() / SIZE) + 1][((int) shape.c.getY() / SIZE)];
			int moved = GRID[((int) shape.d.getX() / SIZE) + 1][((int) shape.d.getY() / SIZE)];
			if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
				shape.a.setX(shape.a.getX() + MOVE);
				shape.b.setX(shape.b.getX() + MOVE);
				shape.c.setX(shape.c.getX() + MOVE);
				shape.d.setX(shape.d.getX() + MOVE);
			}
		}
	}

	public static void MoveLeft(Shapes shape) {
		if (shape.a.getX() - MOVE >= 0 && shape.b.getX() - MOVE >= 0 && shape.c.getX() - MOVE >= 0
				&& shape.d.getX() - MOVE >= 0) {
			int movea = GRID[((int) shape.a.getX() / SIZE) - 1][((int) shape.a.getY() / SIZE)];
			int moveb = GRID[((int) shape.b.getX() / SIZE) - 1][((int) shape.b.getY() / SIZE)];
			int movec = GRID[((int) shape.c.getX() / SIZE) - 1][((int) shape.c.getY() / SIZE)];
			int moved = GRID[((int) shape.d.getX() / SIZE) - 1][((int) shape.d.getY() / SIZE)];
			if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
				shape.a.setX(shape.a.getX() - MOVE);
				shape.b.setX(shape.b.getX() - MOVE);
				shape.c.setX(shape.c.getX() - MOVE);
				shape.d.setX(shape.d.getX() - MOVE);
			}
		}
	}

	public static Shapes newShape() {
		int block = (int) (Math.random() * 100);
		if (block < 15) { 
			return JShape.createShape();
		} else if (block < 30) { 
			return LShape.createShape();
		} else if (block < 45) { 
			return OShape.createShape();
		} else if (block < 60) { 
			return SShape.createShape();
		} else if (block < 75) { 
			return TShape.createShape();
		} else if (block < 90) { 
			return ZShape.createShape();
		} else { 
			return IShape.createShape();
		}
	}
}