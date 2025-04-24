package application;/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Nathan Ramkissoon
 * Date: 4/24/2025
 * Time: 1:24 PM
 *
 * Project: csci205_final_project
 * Package: PACKAGE_NAME
 * Class: application.CardGameApplication
 *
 * Description:
 *
 * ****************************************
 */



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CardGameApplication extends Application
{

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage1)
    {

        Button button1 = new Button("Click me!");
        Label label1 = new Label("Hello, World!");
        label1.relocate(0, 50);

        Pane root = new Pane();
        root.getChildren().addAll(button1, label1);

        Scene scene1 = new Scene(root, 1080, 740);
        stage1.setScene(scene1);
        stage1.setTitle("Turn-Based Card Game");
        stage1.show();
    }
}
