package application;

import java.io.File;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	public static int XMAX = 400;
	public static int YMAX = 500;
	private static Pane group = new Pane();
	private static Scene scene = new Scene(group, XMAX, YMAX);
	public static DIFFICULTY difficulty = DIFFICULTY.EASY;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		group.setStyle("-fx-background-color: #5CDDDA");
		Label title = new Label("TETRIS GAME");
		title.setStyle("-fx-font: 30 arial;");
		title.setLayoutX(100);
		title.setLayoutY(50);
		
		Label current_difficulty = new Label("Current Difficulty : " + difficulty);
		current_difficulty.setStyle("-fx-font: 20 arial;");
		current_difficulty.setLayoutX(100);
		current_difficulty.setLayoutY(100);
		
		File doc = new File("highscore.txt");
		Scanner score = new Scanner(doc);
		Label highscore = new Label("Highscore : " + score.nextLine());
		highscore.setStyle("-fx-font: 30 arial;");
		highscore.setLayoutX(100);
		highscore.setLayoutY(450);
		score.close();
		
		Button play = new Button("Start the Game");
		play.setPrefSize(300, 50);
		play.setLayoutX(50);
		play.setLayoutY(150);
		
		MenuItem easy = new MenuItem("EASY MODE");
		MenuItem normal = new MenuItem("NORMAL MODE");
		MenuItem expert = new MenuItem("EXPERT MODE");
		MenuItem hardcore = new MenuItem("HARDCORE MODE");
		MenuItem legend = new MenuItem("LEGEND MODE");
		MenuButton difficulty_choice = new MenuButton("Set Difficulty Level");
		difficulty_choice.getItems().addAll(easy, normal, expert, hardcore, legend);
		difficulty_choice.setPrefSize(300, 50);
		difficulty_choice.setLayoutX(50);
		difficulty_choice.setLayoutY(250);
		
		group.getChildren().addAll(title, play, current_difficulty, difficulty_choice, highscore);
		stage.setScene(scene);
		stage.setTitle("TETRIS");
		stage.show();
		
		play.setOnAction(value -> {
			Tetris.startGame(new Stage());
		});
		
		easy.setOnAction(value -> {
			difficulty_choice.hide();
			difficulty = DIFFICULTY.EASY;
			current_difficulty.setText("Current Difficulty : " + difficulty);
			Pane pane = new Pane();
			pane.setStyle("-fx-background-color: #5CDDDA");
			pane.getChildren().addAll(title, play, current_difficulty, difficulty_choice, highscore);
			scene.setRoot(pane);
			stage.setScene(scene);
		});
		
		normal.setOnAction(value -> {
			difficulty_choice.hide();
			difficulty = DIFFICULTY.NORMAL;
			current_difficulty.setText("Current Difficulty : " + difficulty);
			Pane pane = new Pane();
			pane.setStyle("-fx-background-color: #5CDDDA");
			pane.getChildren().addAll(title, play, current_difficulty, difficulty_choice, highscore);
			stage.setScene(new Scene(pane, XMAX, YMAX));
		});
		
		expert.setOnAction(value -> {
			difficulty_choice.hide();
			difficulty = DIFFICULTY.EXPERT;
			current_difficulty.setText("Current Difficulty : " + difficulty);
			Pane pane = new Pane();
			pane.setStyle("-fx-background-color: #5CDDDA");
			pane.getChildren().addAll(title, play, current_difficulty, difficulty_choice, highscore);
			scene.setRoot(pane);
			stage.setScene(scene);
		});
		
		hardcore.setOnAction(value -> {
			difficulty_choice.hide();
			difficulty = DIFFICULTY.HARDCORE;
			current_difficulty.setText("Current Difficulty : " + difficulty);
			Pane pane = new Pane();
			pane.setStyle("-fx-background-color: #5CDDDA");
			pane.getChildren().addAll(title, play, current_difficulty, difficulty_choice, highscore);
			scene.setRoot(pane);
			stage.setScene(scene);
		});
		
		legend.setOnAction(value -> {
			difficulty_choice.hide();
			difficulty = DIFFICULTY.LEGEND;
			current_difficulty.setText("Current Difficulty : " + difficulty);
			Pane pane = new Pane();
			pane.setStyle("-fx-background-color: #5CDDDA");
			pane.getChildren().addAll(title, play, current_difficulty, difficulty_choice, highscore);
			scene.setRoot(pane);
			stage.setScene(scene);
		});
	}

	public enum DIFFICULTY {
		EASY,
		NORMAL,
		EXPERT,
		HARDCORE,
		LEGEND;		
	}
}