package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tetris extends Application {
	// The variables
	public static final int MOVE = 25;
	public static int movespeed;
	public static final int SIZE = 25;
	public static int XMAX = SIZE * 10;
	public static int YMAX = SIZE * 20;
	public static int[][] GRID = new int[XMAX / SIZE][YMAX / SIZE];
	private static Pane group = new Pane();
	private static Shapes object;
	private static Scene scene = new Scene(group, XMAX + 150, YMAX);
	public static int score = 0;
	private static int top = 0;
	private static boolean game = true;
	private static Shapes nextObj = Controller.newShape();
	private static int linesNo = 0;
	
	public static void startGame(Stage stage){
		score = 0;
		top = 0;
		linesNo = 0;
		GRID = new int[XMAX / SIZE][YMAX / SIZE];
		group = new Pane();
		scene = new Scene(group, XMAX + 150, YMAX);
		stage.setScene(scene);
		
		for (int[] a : GRID) {
			Arrays.fill(a, 0);
		}

		Line line = new Line(XMAX, 0, XMAX, YMAX);
		Text scoretext = new Text("Score: ");
		scoretext.setStyle("-fx-font: 20 arial;");
		scoretext.setY(50);
		scoretext.setX(XMAX + 5);
		
		Text level = new Text("Lines: ");
		level.setStyle("-fx-font: 20 arial;");
		level.setY(100);
		level.setX(XMAX + 5);
		level.setFill(Color.GREEN);
		group.getChildren().addAll(scoretext, line, level);

		Shapes a = nextObj;
		group.getChildren().addAll(a.a, a.b, a.c, a.d);
		moveOnKeyPress(a);
		object = a;
		nextObj = Controller.newShape();
		stage.setScene(scene);
		stage.setTitle("TETRIS");
		stage.show();

		switch(Main.difficulty) {
		case EASY:
			movespeed = 300;
			break;
		case NORMAL:
			movespeed = 250;
			break;
		case EXPERT:
			movespeed = 200;
			break;
		case HARDCORE:
			movespeed = 150;
			break;
		case LEGEND:
			movespeed = 100;
		}
		
		Timer fall = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0
								|| object.d.getY() == 0)
							top++;
						else
							top = 0;

						if (top == 2) {
							// GAME OVER
							Text over = new Text("GAME OVER");
							over.setFill(Color.RED);
							over.setStyle("-fx-font: 60 arial;");
							over.setY(250);
							over.setX(10);
							group.getChildren().add(over);
							game = false;
							File doc = new File("highscore.txt");
							Scanner highscore;
							try {
								highscore = new Scanner(doc);
								if (highscore.nextInt() < score) {
									FileWriter fw = new FileWriter(doc.getAbsoluteFile());
									BufferedWriter newscore = new BufferedWriter(fw);
									newscore.write(""+ score);
									newscore.close();
								}
								highscore.close();
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						// Exit
						if (top == 15) {
							stage.close();
						}

						if (game) {
							MoveDown(object);
							scoretext.setText("Score: " + Integer.toString(score));
							level.setText("Lines: " + Integer.toString(linesNo));
						}
					}
				});
			}
		};
		fall.schedule(task, 0, movespeed);
		
		stage.setOnHidden( e -> {
			File doc = new File("highscore.txt");
			Scanner highscore;
			try {
				highscore = new Scanner(doc);
				if (highscore.nextInt() < score) {
					FileWriter fw = new FileWriter(doc.getAbsoluteFile());
					BufferedWriter newscore = new BufferedWriter(fw);
					newscore.write(""+ score);
					newscore.close();
				}
				highscore.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			task.cancel();
			fall.cancel();
			fall.purge();
			/*score = 0;
			top = 0;
			linesNo = 0;
			GRID = new int[XMAX / SIZE][YMAX / SIZE];
			group = new Pane();
			scene = new Scene(group, XMAX + 150, YMAX);
			stage.setScene(scene);
			nextObj = Controller.newShape();*/
		});
	}

	private static void moveOnKeyPress(Shapes form) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case RIGHT:
					Controller.MoveRight(form);
					break;
				case DOWN:
					MoveDown(form);
					score++;
					break;
				case LEFT:
					Controller.MoveLeft(form);
					break;
				case UP:
					MoveTurn(form);
					break;
				default:
					break;
				}
			}
		});
	}

	private static void MoveTurn(Shapes form) {
		int f = form.form;
		Rectangle a = form.a;
		Rectangle b = form.b;
		Rectangle c = form.c;
		Rectangle d = form.d;
		switch (form.getName()) {
		case "j":
			if (f == 1 && checkBorder(a, -1, -1) && checkBorder(c, 1, 1) && checkBorder(d, 2, 0)) {		
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveUp(form.c);
				MoveRight(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeShapes();
				break;
			}
			if (f == 2 && checkBorder(a, -1, 1) && checkBorder(c, 1, -1) && checkBorder(d, 0, -2)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveDown(form.c);
				MoveRight(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeShapes();
				break;
			}
			if (f == 3 && checkBorder(a, 1, 1) && checkBorder(c, -1, -1) && checkBorder(d, -2, 0)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveLeft(form.c);
				MoveDown(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeShapes();
				break;
			}
			if (f == 4 && checkBorder(a, 1, -1) && checkBorder(c, -1, 1) && checkBorder(d, 0, 2)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveUp(form.c);
				MoveLeft(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeShapes();
				break;
			}
			break;
		case "l":
			if (f == 1 && checkBorder(a, 1, 1) && checkBorder(c, -1, -1) && checkBorder(d, 0, -2)) {
				MoveRight(form.a);
				MoveUp(form.a);
				MoveDown(form.c);
				MoveLeft(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeShapes();
				break;
			}
			if (f == 2 && checkBorder(a, 1, -1) && checkBorder(c, -1, 1) && checkBorder(d, -2, 0)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeShapes();
				break;
			}
			if (f == 3 && checkBorder(a, -1, -1) && checkBorder(c, 1, 1) && checkBorder(d, 0, 2)) {
				MoveLeft(form.a);
				MoveDown(form.a);
				MoveRight(form.c);
				MoveUp(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeShapes();
				break;
			}
			if (f == 4 && checkBorder(a, -1, 1) && checkBorder(c, 1, -1) && checkBorder(d, 2, 0)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeShapes();
				break;
			}
			break;
		case "o":
			break;
		case "s":
			if (f == 1 && checkBorder(a, 1, 1) && checkBorder(c, 1, -1) && checkBorder(d, 0, -2)) {
				MoveRight(form.a);
				MoveUp(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeShapes();
				break;
			}
			if (f == 2 && checkBorder(a, 1, -1) && checkBorder(c, -1, -1) && checkBorder(d, -2, 0)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveLeft(form.c);
				MoveDown(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeShapes();
				break;
			}
			if (f == 3 && checkBorder(a, -1, -1) && checkBorder(c, -1, 1) && checkBorder(d, 0, 2)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeShapes();
				break;
			}
			if (f == 4 && checkBorder(a, 1, 1) && checkBorder(c, 1, 1) && checkBorder(d, 2, 0)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveRight(form.c);
				MoveUp(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeShapes();
				break;
			}
			break;
		case "t":
			if (f == 1 && checkBorder(a, -1, 1) && checkBorder(b, -1, -1) && checkBorder(d, 1, 1)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveLeft(form.b);
				MoveDown(form.b);
				MoveRight(form.d);
				MoveUp(form.d);
				form.changeShapes();
				break;
			}
			if (f == 2 && checkBorder(a, 1, 1) && checkBorder(b, -1, 1) && checkBorder(d, 1, -1)) {
				MoveRight(form.a);
				MoveUp(form.a);
				MoveLeft(form.b);
				MoveUp(form.b);
				MoveRight(form.d);
				MoveDown(form.d);
				form.changeShapes();
				break;
			}
			if (f == 3 && checkBorder(a, 1, -1) && checkBorder(b, 1, 1) && checkBorder(d, -1, -1)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveRight(form.b);
				MoveUp(form.b);
				MoveLeft(form.d);
				MoveDown(form.d);
				form.changeShapes();
				break;
			}
			if (f == 4 && checkBorder(a, -1, -1) && checkBorder(b, 1, -1) && checkBorder(d, -1, 1)) {
				MoveLeft(form.a);
				MoveDown(form.a);
				MoveRight(form.b);
				MoveDown(form.b);
				MoveLeft(form.d);
				MoveUp(form.d);
				form.changeShapes();
				break;
			}
			break;
		case "z":
			if (f == 1 && checkBorder(a, 1, 1) && checkBorder(c, -1, 1) && checkBorder(d, -2, 0)) {
				MoveRight(form.a);
				MoveUp(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeShapes();
				break;
			}
			if (f == 2 && checkBorder(a, 1, -1) && checkBorder(c, 1, 1) && checkBorder(d, 0, 2)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveRight(form.c);
				MoveUp(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeShapes();
				break;
			}
			if (f == 3 && checkBorder(a, -1, -1) && checkBorder(c, 1, -1) && checkBorder(d, 2, 0)) {
				MoveLeft(form.a);
				MoveDown(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeShapes();
				break;
			}
			if (f == 4 && checkBorder(a, -1, 1) && checkBorder(c, -1, -1) && checkBorder(d, 0, -2)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveLeft(form.c);
				MoveDown(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeShapes();
				break;
			}
			break;
		case "i":
			if (f == 1 && checkBorder(a, 1, 1) && checkBorder(c, -1, -1) && checkBorder(d, -2, -2)) {
				MoveRight(form.a);
				MoveUp(form.a);
				MoveLeft(form.c);
				MoveDown(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeShapes();
				break;
			}
			if (f == 2 && checkBorder(a, 1, -1) && checkBorder(c, -1, 1) && checkBorder(d, -2, 2)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeShapes();
				break;
			}
			if (f == 3 && checkBorder(a, -1, -1) && checkBorder(c, 1, 1) && checkBorder(d, 2, 2)) {
				MoveLeft(form.a);
				MoveDown(form.a);
				MoveRight(form.c);
				MoveUp(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeShapes();
				break;
			}
			if (f == 4 && checkBorder(a, -1, 1) && checkBorder(c, 1, -1) && checkBorder(d, 2, -2)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeShapes();
				break;
			}
			break;
		}
	}

	private static void RemoveRows(Pane pane) {
		ArrayList<Node> rects = new ArrayList<Node>();
		ArrayList<Integer> lines = new ArrayList<Integer>();
		ArrayList<Node> newrects = new ArrayList<Node>();
		int full_lines;
		int last_full_lines = 0;
		int full = 0;
		for (int i = 0; i < GRID[0].length; i++) {
			for (int j = 0; j < GRID.length; j++) {
				if (GRID[j][i] == 1)
					full++;
			}
			if (full == GRID.length)
			lines.add(i);
			full = 0;
		}
		full_lines = lines.size();
		if (full_lines > 0) {
			do {
				for (Node node : pane.getChildren()) {
					if (node instanceof Rectangle)
						rects.add(node);
				}
				score += 100;
				linesNo++;

				for (Node node : rects) {
					Rectangle a = (Rectangle) node;
					if (a.getY() == lines.get(0) * SIZE) {
						GRID[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
						pane.getChildren().remove(node);
					} else
						newrects.add(node);
				}

				for (Node node : newrects) {
					Rectangle a = (Rectangle) node;
					if (a.getY() < lines.get(0) * SIZE) {
						GRID[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
						a.setY(a.getY() + SIZE);
					}
				}
				lines.remove(0);
				rects.clear();
				newrects.clear();
				for (Node node : pane.getChildren()) {
					if (node instanceof Rectangle)
						rects.add(node);
				}
				for (Node node : rects) {
					Rectangle a = (Rectangle) node;
					try {
						GRID[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
					} catch (ArrayIndexOutOfBoundsException e) {
					}
				}
				rects.clear();
			} while (lines.size() > 0);
			if (full_lines == 4 && last_full_lines == 4) {
				score += 800;
			} else if (full_lines == 4) {
				score += 400;
			}
			
			last_full_lines = full_lines;
		}
	}

	private static void MoveDown(Rectangle rect) {
		if (rect.getY() + MOVE < YMAX)
			rect.setY(rect.getY() + MOVE);
	}

	private static void MoveRight(Rectangle rect) {
		if (rect.getX() + MOVE <= XMAX - SIZE)
			rect.setX(rect.getX() + MOVE);
	}

	private static void MoveLeft(Rectangle rect) {
		if (rect.getX() - MOVE >= 0)
			rect.setX(rect.getX() - MOVE);
	}

	private static void MoveUp(Rectangle rect) {
		if (rect.getY() - MOVE > 0)
			rect.setY(rect.getY() - MOVE);
	}

	private static void MoveDown(Shapes form) {
		if (form.a.getY() == YMAX - SIZE || form.b.getY() == YMAX - SIZE || form.c.getY() == YMAX - SIZE
				|| form.d.getY() == YMAX - SIZE || moveA(form) || moveB(form) || moveC(form) || moveD(form)) {
			GRID[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
			GRID[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE] = 1;
			GRID[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE] = 1;
			GRID[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE] = 1;
			RemoveRows(group);

			Shapes a = nextObj;
			nextObj = Controller.newShape();
			object = a;
			group.getChildren().addAll(a.a, a.b, a.c, a.d);
			moveOnKeyPress(a);
		}

		if (form.a.getY() + MOVE < YMAX && form.b.getY() + MOVE < YMAX && form.c.getY() + MOVE < YMAX
				&& form.d.getY() + MOVE < YMAX) {
			int movea = GRID[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE) + 1];
			int moveb = GRID[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE) + 1];
			int movec = GRID[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE) + 1];
			int moved = GRID[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1];
			if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
				form.a.setY(form.a.getY() + MOVE);
				form.b.setY(form.b.getY() + MOVE);
				form.c.setY(form.c.getY() + MOVE);
				form.d.setY(form.d.getY() + MOVE);
			}
		}
	}

	private static boolean moveA(Shapes form) {
		return (GRID[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE) + 1] == 1);
	}

	private static boolean moveB(Shapes form) {
		return (GRID[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE) + 1] == 1);
	}

	private static boolean moveC(Shapes form) {
		return (GRID[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE) + 1] == 1);
	}

	private static boolean moveD(Shapes form) {
		return (GRID[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1] == 1);
	}

	private static boolean checkBorder(Rectangle rect, int x, int y) {
		boolean xb = false;
		boolean yb = false;
		if (x >= 0)
			xb = rect.getX() + x * MOVE <= XMAX - SIZE;
		if (x < 0)
			xb = rect.getX() + x * MOVE >= 0;
		if (y >= 0)
			yb = rect.getY() - y * MOVE > 0;
		if (y < 0)
			yb = rect.getY() + y * MOVE < YMAX;
		return xb && yb && GRID[((int) rect.getX() / SIZE) + x][((int) rect.getY() / SIZE) - y] == 0;
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}