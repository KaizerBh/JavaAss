package application;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// select the input file 'points.txt'
			FileChooser fileChooser = new FileChooser();
			String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
			fileChooser.setInitialDirectory(new File(currentPath));
			File selectedFile = fileChooser.showOpenDialog(primaryStage);

			// read the input file
			Scanner scan = new Scanner(selectedFile.getAbsoluteFile());
			// define an array list data structure to store the points
			ArrayList<Coordinates> coords = new ArrayList<Coordinates>();

			ArrayList<Coordinates> updatedCoord = new ArrayList<Coordinates>(coords.size());
			while (scan.hasNext()) {
				// read a line in file
				String line = scan.nextLine();

				// parse it to two segments by splitting it with ; as delimiter
				String[] segments = new String[2];

				// part 1 for x
				segments[0] = line.substring(0, line.indexOf(";"));

				// part 2 for y
				segments[1] = line.substring(line.indexOf(";"));

				// again split the part to get values of x and y with = as delimiter
				String[] pointvalx = segments[0].trim().split("=");
				String[] pointvaly = segments[1].trim().split("=");

				// parse and store the values of x and y
				float x = Float.parseFloat(pointvalx[1]);
				float y = Float.parseFloat(pointvaly[1]);

				Coordinates coord = new Coordinates(x, y);
				coords.add(coord);

			}

			// set label to display the coordinate values in the screen
			Label label = new Label("");
			// label dimensions
			label.setLayoutX(150);
			label.setLayoutY(440);
			// label font
			label.setFont(Font.font("Arial", 12));

			// Creating a Group object

			// group collects all items in the window
			Group root = new Group();
			root.getChildren().add(label);

			// create Shape.Circle array
			Circle[] circles = new Circle[coords.size()];

			for (int i = 0; i < coords.size(); i++) {
				// construct a circle with point values x and y and radius 5 pixels
				circles[i] = new Circle(coords.get(i).getX(), coords.get(i).getY(), 5);
				circles[i].setFill(Paint.valueOf("Blue")); // set colour blue
				// add hover property listener to the shape
				int finalI = i;
				// when the shape is hovered by the mouse, this listener method will be
				// triggered.
				circles[i].hoverProperty().addListener(
						(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean show) -> {
							if (show) { // on hover
								// display the coordinates in the label below the screen
								label.setText(String.format("x=%.2f y=%.2f", circles[finalI].getCenterX(),
										circles[finalI].getCenterY()));
							} else { // on mouse out
								label.setText(""); // clear the contents
							}
						});

				// drag and drop listeners

				final double[] orgSceneX = { 0 };
				final double[] orgSceneY = { 0 };

				circles[finalI].setOnMousePressed((e) -> {

					// get the values of the shape in the scene
					orgSceneX[0] = e.getSceneX();
					orgSceneY[0] = e.getSceneY();
					Circle c = (Circle) (e.getSource());
					c.toFront();
				});

				// drag listener
				circles[finalI].setOnMouseDragged((e) -> {
					// pick the circle
					Circle c = (Circle) (e.getSource());

					// get the changed offset value of X and Y axis while dragging
					double offsetX = e.getSceneX() - orgSceneX[0];
					double offsetY = e.getSceneY() - orgSceneY[0];

					// check whether the changed offset value crosses the screen limit of the
					// window, if crossed, stop drag event
					if (c.getCenterX() + offsetX > 400 || c.getCenterX() + offsetX < 0) {
						return;
					}
					if (c.getCenterY() + offsetY > 450 || c.getCenterY() + offsetY < 0) {
						return;
					}

					// set the new position for circle
					c.setCenterX(c.getCenterX() + offsetX);
					c.setCenterY(c.getCenterY() + offsetY);

					// get the updated values
					orgSceneX[0] = e.getSceneX();
					orgSceneY[0] = e.getSceneY();

				});

				circles[finalI].setOnMouseReleased((e) -> {
					// pick the circle
					Circle c = (Circle) (e.getSource());

					// get the changed offset value of X and Y axis while dragging
					double offsetX = e.getSceneX() - orgSceneX[0];
					double offsetY = e.getSceneY() - orgSceneY[0];

					// check whether the changed offset value crosses the screen limit of the
					// window, if crossed, stop drag event
					if (c.getCenterX() + offsetX > 400 || c.getCenterX() + offsetX < 0) {
						return;
					}
					if (c.getCenterY() + offsetY > 450 || c.getCenterY() + offsetY < 0) {
						return;
					}

					// set the new position for circle
					c.setCenterX(c.getCenterX() + offsetX);
					c.setCenterY(c.getCenterY() + offsetY);

					// get the updated values
					orgSceneX[0] = e.getSceneX();
					orgSceneY[0] = e.getSceneY();
					Coordinates updated = new Coordinates((float) orgSceneX[0], (float) orgSceneY[0]);
					updatedCoord.add(updated);
				});

				// add the circle to the group
				root.getChildren().add(circles[finalI]);
			}

			// create a scene with the group
			Scene scene = new Scene(root, 400, 450);
			scene.setFill(Paint.valueOf("#fffff2")); // add bg color

			primaryStage.setTitle("Show Points");
			primaryStage.setHeight(500);
			primaryStage.setWidth(400);
			primaryStage.setResizable(false);

			// Adding scene to the stage
			primaryStage.setScene(scene);

			// Displaying the contents of the stage
			primaryStage.show();

			// Set on close or exit it will store the data into temp array then write that
			// data, in order not to get too many duplicates
			// Had to trick the system such as reverse the list and then look at the
			// original array size then get those 3 or more items.
			primaryStage.setOnCloseRequest((e -> {
				int sizeOfOriginalArray = coords.size();
				ArrayList<String> temp = new ArrayList<String>();
				Collections.reverse(updatedCoord);
				for (int i = 0; i < sizeOfOriginalArray; i++) {
					String str = updatedCoord.get(i).toString();
					temp.add(str);
				}
				System.out.println(temp);
				try {
					FileWriter writer = new FileWriter("points.txt");
					for (String str : temp) {
						writer.write(str + System.lineSeparator());
					}
					writer.close();
				} catch (Exception e2) {
					System.err.println("FILE NOT FOUND/COULD NOT WRITE INTO SYSTEM");
				}

			}));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

}
