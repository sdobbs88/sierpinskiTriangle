
/**
 * Class: CSCI 2410 Data Structures and Algorithms
 * Instructor: Y. Daniel Liang 
 * Description: Uses
 * recursion to draw triangles within triangles. Buttons added to increase and
 * decrease number of triangles. Decrease button disables when number of
 * triangles is 0. 
 * Due: 08/22/2016
 *
 * @author Shaun C. Dobbs
 * @version 1.1
 *
 * I pledge by honor that I have completed the programming assignment
 * independently. I have not copied the code from a student or any source. I
 * have not given my code to any student. * Sign here: Shaun C. Dobbs
 */

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class SierpinskiTriangle extends Application {

        
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        SierpinskiTrianglePane trianglePane = new SierpinskiTrianglePane();

        Button btUp = new Button("+");
        Button btDown = new Button("-");

        btUp.setOnAction(e -> {
            trianglePane.setOrder(1);

            if (trianglePane.getOrder() > 0) {
                btDown.setDisable(false);
            } else if (trianglePane.getOrder() <= 0) {
                btDown.setDisable(true);
            }

            if (trianglePane.getOrder() >= 8) {
                btUp.setDisable(true);
            } else {
                btUp.setDisable(false);
            }
        });

        //Down/decrease button initially disabled.
        //Because the program starts at the triangle being 0.
        btDown.setDisable(true);

        btDown.setOnAction(e -> {
            trianglePane.setOrder(-1);
            //Add if statement to enable button if !=0

            if (trianglePane.getOrder() > 0) {
                btDown.setDisable(false);
            } else if (trianglePane.getOrder() <= 0) {
                btDown.setDisable(true);
            }

            if (trianglePane.getOrder() >= 8) {
                btUp.setDisable(true);
            } else {
                btUp.setDisable(false);
            }
        });

        // Pane to hold label, text field, and a button
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(btUp, btDown);
        hBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(trianglePane);
        borderPane.setBottom(hBox);
        

       

        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 200, 210);
        primaryStage.setTitle("SierpinskiTriangle"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        
        //listener for the maximize property. Not needed
        //primaryStage.maximizedProperty().addListener(ov -> trianglePane.paint());
        //scene.widthProperty().addListener(ov -> trianglePane.paint());
        //scene.heightProperty().addListener(ov -> trianglePane.paint());
        
        
        trianglePane.widthProperty().addListener(e -> trianglePane.paint());
        trianglePane.heightProperty().addListener(e -> trianglePane.paint());
        trianglePane.paint();
    }

    /**
     * Pane for displaying triangles
     */
    static class SierpinskiTrianglePane extends Pane {

        private int order = 0;

        /**
         * Set a new order
         */
        public void setOrder(int order) {
            //Now += order since it changes everytime the up or down button is pressed
            this.order += order;
            paint();
        }

        public int getOrder() {
            return order;
        }

        SierpinskiTrianglePane() {
        }

        protected void paint() {
            // Select three points in proportion to the panel size

            Point2D p1 = new Point2D(getWidth() / 2, 10);
            Point2D p2 = new Point2D(10, getHeight() - 10);
            Point2D p3 = new Point2D(getWidth() - 10, getHeight() - 10);

            this.getChildren().clear(); // Clear the pane before redisplay

            displayTriangles(order, p1, p2, p3);
        }

        private void displayTriangles(int order, Point2D p1,
                Point2D p2, Point2D p3) {
            if (order == 0) {
                // Draw a triangle to connect three points
                Polygon triangle = new Polygon();
                triangle.getPoints().addAll(p1.getX(), p1.getY(), p2.getX(),
                        p2.getY(), p3.getX(), p3.getY());
                triangle.setStroke(Color.BLACK);
                triangle.setFill(Color.WHITE);

                this.getChildren().add(triangle);
            } else {
                // Get the midpoint on each edge in the triangle
                Point2D p12 = p1.midpoint(p2);
                Point2D p23 = p2.midpoint(p3);
                Point2D p31 = p3.midpoint(p1);

                // Recursively display three triangles
                displayTriangles(order - 1, p1, p12, p31);
                displayTriangles(order - 1, p12, p2, p23);
                displayTriangles(order - 1, p31, p23, p3);
            }
        }
    }

    /**
     * The main method is only needed for the IDE with limited JavaFX support.
     * Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
