//https://www.mobilefish.com/services/record_mouse_coordinates/record_mouse_coordinates.php
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.stage.Stage;
import javafx.scene.shape.Path;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;

public class Coordinates extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        //String fileName = "C:\\Users\\Grażynka\\Downloads\\drogaaa.jpg";
        Image image = new Image(new FileInputStream("C:\\Users\\Grażynka\\Downloads\\drogaaaa.jpg"));
        //Image image = new Image("my/res/flower.png", 100, 100, false, false);//directly resizing
        ImageView imageView = new ImageView(image);
        //imageView.setFitHeight(300);
        //imageView.setFitWidth(300);
        //imageView.setPreserveRatio(true);//preserve the width:height ratio





        BorderPane pane = new BorderPane();
        pane.setCenter(imageView);

        //Group root = new Group(imageView);

        //double width = image.getWidth()/10;
        //double height = image.getHeight()/10;

        //Creating an object of the class named Path
        Path path = new Path();

        //Moving to the starting point
        MoveTo moveTo = new MoveTo();
        moveTo.setX(1644.0);
        moveTo.setY(158.0);
        //Instantiating the class CubicCurve
        CubicCurveTo cubicCurveTo = new CubicCurveTo();


        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Grażynka\\IdeaProjects\\TrafficSimulation3.0\\coordinates.txt"));
        String line = null;
        int x=0,y=0;
        while((line = reader.readLine()) != null) {
            System.out.println(line);
            String[] values = line.split(",");
            x = Integer.parseInt(values[0]);
            y = Integer.parseInt(values[1]);




            //Setting properties of the class CubicCurve
            cubicCurveTo.setControlX1(400.0f);
            cubicCurveTo.setControlY1(400.0f);
            cubicCurveTo.setControlX2(175.0f);
            cubicCurveTo.setControlY2(250.0f);
            cubicCurveTo.setX(500.0f);
            cubicCurveTo.setY(200.0f);

            //Adding the path elements to Observable list of the Path class
            path.getElements().add(moveTo);
            path.getElements().add(cubicCurveTo);
        }
        //Creating a Group object
        //Group root = new Group(path);



        pane.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
                System.out.print(event.getScreenX()+ " ");
                System.out.println(event.getScreenY());
            }
        });

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(pane,path);

        Scene scene = new Scene(stackPane,400,400);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args){launch(args);}
}
