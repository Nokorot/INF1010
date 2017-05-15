package o7;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import o3.OrdnetLenkeliste;
import o5.Labyrint;
import o5.Labyrint.UtVei;
import o5.Rute;
import o5.SortRute;

public class HelloWorld extends Application {

	private FileChooser fileChooser;
	private GridPane gridPane;
	
	private Labyrint labyrint;
	private Rectangle[] squares;
	private int width, height;
	private OrdnetLenkeliste<UtVei> utveier;  

	public HelloWorld() {
		fileChooser = new FileChooser();
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Labyrint GUI");
		VBox root = new VBox();

		GridPane grid = new GridPane();
		Button btn = new Button();
		btn.setText("Load file");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				File f = fileChooser.showOpenDialog(primaryStage);

				try {
					labyrint = Labyrint.readFromFile(f.toString());

					resetGrid(labyrint.labyrint);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		btn.setAlignment(Pos.CENTER);
		grid.add(btn, 0, 0);
		grid.setAlignment(Pos.CENTER);
		root.getChildren().add(grid);

		gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);

		root.getChildren().add(gridPane);
		VBox.setVgrow(gridPane, Priority.ALWAYS);

		Scene scene;
		primaryStage.setScene(scene = new Scene(root, 600, 600));
		primaryStage.setMinHeight(600);
		primaryStage.setMinWidth(600);
		primaryStage.show();
		
		scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent mouseEvent) {
		        if (squares == null) 
		        	return;
		        for (int i = 0; i < squares.length; i++){
		        	Rectangle square = squares[i];
		        	double x = mouseEvent.getX() - squares[i].getLayoutX();
		        	double y = mouseEvent.getY() - squares[i].getLayoutY() - 25;//squares[i].getHeight()/2;
		        	if (squares[i].contains(x, y))
		        		solve(i % width, i / width);
		        }
		    }
		});
	}
	
	private void solve(int x, int y){
		Rute rute = labyrint.labyrint[x][y];
		if (rute instanceof SortRute){
			System.out.println("Black");
			return;
		}
		System.out.println(x + " " + y);
		OrdnetLenkeliste<UtVei> utveier = labyrint.solve(x, y);
		UtVei shortest = labyrint.shortestWayOut();
		
		boolean[][] booleans = losningStringTilTabell(shortest.toString(), labyrint.width, labyrint.height);
		showWayOut(booleans);
	}
	
	
	private void resetGrid(Rute[][] labyrint) {
		gridPane.getChildren().clear();
		
		width = labyrint.length;
		height = labyrint[0].length;
		squares = new Rectangle[width * height];
		                        
		int max = width > height ? width : height;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Rectangle square = new Rectangle(500 / max, 500 / max);
				squares[i + j*width] = square;
				
				Color c = Color.WHITE;//(i + j) % 2 == 0 ? Color.AQUA : Color.BLUE;
				if (labyrint[i][j] instanceof SortRute)
					c = Color.BLACK;

				square.setFill(c);

				gridPane.add(square, i, j);
			}
		}
	}
	
	private void showWayOut(boolean[][] booleans){
		resetGrid(labyrint.labyrint);
		int count = 0;
		for (int i = 0; i < booleans.length * booleans[0].length ; i ++){
			if (booleans[i/booleans[0].length][i%booleans[0].length])
				count++;
		}
		int tot = count;
		
		for (int i = 0; i < squares.length; i++){
			if (booleans[i % width][i / width]){
				Color c = new Color((double) count/tot, 0, 0, 1);
				squares[i].setFill(Color.RED);
				count--;
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
		System.out.println(losningString);
		boolean[][] losning = new boolean[bredde][hoyde];
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
		java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s", ""));
		while (m.find()) {
			int x = Integer.parseInt(m.group(1));
			int y = Integer.parseInt(m.group(2));
			losning[x][y] = true;
		}
		return losning;
	}
}