package application;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Shapes {
	public static final int SIZE = Tetris.SIZE;
	public static int XMAX = Tetris.XMAX;
	public static int YMAX = Tetris.YMAX;
	Rectangle a;
	Rectangle b;
	Rectangle c;
	Rectangle d;
	Color color;
	private String name;
	public int form = 1;
	
	//Constructors
	public Shapes (Rectangle a, Rectangle b, Rectangle c, Rectangle d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public Shapes (Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name, Color color) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.name = name;
		
		this.a.setFill(color);
		this.b.setFill(color);
		this.c.setFill(color);
		this.d.setFill(color);
	}
	
	//getter
	public String getName() {
		return this.name;
	}
	
	public void changeShapes() {
		if (form != 4) {
			form++;
		} else {
			form = 1;
		}
	}
}