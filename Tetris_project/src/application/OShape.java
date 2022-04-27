package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OShape extends Shapes {
	
	public OShape(Rectangle a, Rectangle b, Rectangle c, Rectangle d) {
		super(a, b, c, d);
	}
	
	public OShape(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name, Color color) {
		super(a, b, c, d, name, color);
	}

	public static Shapes createShape() {
		Rectangle a = new Rectangle(SIZE-1, SIZE-1);
		Rectangle b = new Rectangle(SIZE-1, SIZE-1);
		Rectangle c = new Rectangle(SIZE-1, SIZE-1);
		Rectangle d = new Rectangle(SIZE-1, SIZE-1);
		
		a.setX(XMAX / 2 - SIZE);
		b.setX(XMAX / 2);
		c.setX(XMAX / 2 - SIZE);
		c.setY(SIZE);
		d.setX(XMAX / 2);
		d.setY(SIZE);
		return new OShape(a,b,c,d,"o", Color.YELLOWGREEN);
	}
}
