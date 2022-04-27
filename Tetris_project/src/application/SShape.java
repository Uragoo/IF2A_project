package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SShape extends Shapes{
		
	public SShape(Rectangle a, Rectangle b, Rectangle c, Rectangle d) {
		super(a, b, c, d);
	}
	
	public SShape(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name, Color color) {
		super(a, b, c, d, name, color);
	}

	public static Shapes createShape() {
		Rectangle a = new Rectangle(SIZE-1, SIZE-1);
		Rectangle b = new Rectangle(SIZE-1, SIZE-1);
		Rectangle c = new Rectangle(SIZE-1, SIZE-1);
		Rectangle d = new Rectangle(SIZE-1, SIZE-1);
		
		a.setX(XMAX / 2 - SIZE);
		a.setY(SIZE);
		b.setX(XMAX / 2);
		b.setY(SIZE);
		c.setX(XMAX / 2);
		d.setX(XMAX / 2 + SIZE);
		return new SShape(a,b,c,d,"s", Color.RED);
	}
}
