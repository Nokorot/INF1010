package o7;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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

	private String buttonStyle = "-fx-background-color: #bbbbbb";
	
	private FileChooser fileChooser;
	private GridPane gridPane;
	private TextField tf, tf2;
	
	private Labyrint labyrint;
	private Rectangle[] squares;
	private int width, height;
	private OrdnetLenkeliste<UtVei> utveier;
	
	private Image maze = loadImage("res/maze.jpg");
	
	private Stage primaryStage;

	private int index = 0;

	public HelloWorld() {
		fileChooser = new FileChooser();
	}

	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		VBox root = new VBox();

		GridPane grid = new GridPane();
		tf = new TextField();
		tf.setAlignment(Pos.CENTER);
		tf.setEditable(false);
		grid.add(tf, 1, 0);

		tf2 = new TextField();
		tf2.setAlignment(Pos.CENTER);
		tf2.setEditable(false);
		grid.add(tf2, 2, 0);

		Button next = new Button();
		next.setText("-->");
		next.setStyle(buttonStyle);
		next.setOnAction((ActionEvent e) -> onButtonPress(e, "next"));

		Button previus = new Button();
		previus.setText("<--");
		previus.setStyle(buttonStyle);
		previus.setOnAction((ActionEvent e) -> onButtonPress(e, "previus"));

		grid.add(previus, 0, 0);
		grid.add(next, 3, 0);
		grid.setAlignment(Pos.CENTER);
		root.getChildren().add(grid);

		grid = new GridPane();
		
		gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		
		if (maze != null){
			Label l = new Label();
			l.setGraphic(new ImageView(maze));
			grid.add(l, 0, 0);
		}
		
		grid.add(gridPane, 0, 0);
		root.getChildren().add(grid);

		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		Button btn = new Button();
		btn.setText("Load file");
		btn.setMinWidth(200);
		btn.setStyle(buttonStyle);
		btn.setOnAction((ActionEvent e) -> onButtonPress(e, "loadFile"));
		grid.add(btn, 0, 0);
		root.getChildren().add(grid);

		root.setStyle("-fx-background-color: #555555");
		
		Scene scene;
		primaryStage.setTitle("Labyrint GUI");
		primaryStage.setScene(scene = new Scene(root, 800, 560));
		primaryStage.setResizable(false);
		primaryStage.show();

		scene.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> onMousePress(me));
	}
	
	private void onButtonPress(ActionEvent event, String button){
		switch (button) {
		case "next":
			if (utveier == null) return;
			index = (index + 1) % utveier.size();
			showTheWayOut();
			break;
		case "previus":
			if (utveier == null) return;
			index = (index + utveier.size() - 1) % utveier.size();
			showTheWayOut();
			break;
		case "loadFile":
			File f = fileChooser.showOpenDialog(this.primaryStage);

			try {
				labyrint = Labyrint.readFromFile(f.toString());
				resetGrid(labyrint.labyrint);
			} catch (FileNotFoundException e) {
			}
			break;
		}
	}

	private void onMousePress(MouseEvent e) {
		if (squares == null)
			return;
		for (int i = 0; i < squares.length; i++) {
			double x = e.getX() - squares[i].getLayoutX();
			double y = e.getY() - squares[i].getLayoutY() - 25;
			if (squares[i].contains(x, y))
				solve(i % width, i / width);
		}
	}

	private void solve(int x, int y) {
		Rute rute = labyrint.labyrint[x][y];
		if (rute instanceof SortRute) {
			System.out.println("Black");
			return;
		}
		utveier = labyrint.solve(x, y);
		index = 0;
		showTheWayOut();
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
				squares[i + j * width] = square;

				Color c = Color.WHITE;
				if (labyrint[i][j] instanceof SortRute)
					c = Color.BLACK;

				square.setFill(c);

				gridPane.add(square, i, j);
			}
		}
	}

	private void showTheWayOut() {
		if (labyrint == null)
			return;
		resetGrid(labyrint.labyrint);

		UtVei utVei = utveier.get(index);

		tf.setText("nr. " + (index + 1) + " : " + utveier.size());
		tf2.setText("Length: " + utVei.size());
		
//		boolean[][] booleans = losningStringTilTabell(utveier.get(index).toString(), labyrint.width, labyrint.height);
//		for (int i = 0; i < squares.length; i++)
//			if (booleans[i % width][i / width]) 
//				squares[i].setFill(Color.RED);
		
		int count = 1, length = utVei.size();
		for (Rute r : utveier.get(index)){
			Color c = new Color(1 - (double) (count++) / length * 0.75, 0, 0, 1);
			squares[r.X() + r.Y() * width].setFill(c);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private Image loadImage(String dir) {
		File file = new File(dir);
		
		try {
			BufferedImage img = ImageIO.read(file);
			return SwingFXUtils.toFXImage(img, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
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