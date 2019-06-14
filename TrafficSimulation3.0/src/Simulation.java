import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


class Lights extends TimerTask {
    public void run() {
        if (0 == Simulation.lights) {
            Simulation.lights = 1;

            Road.lights_on[0] = Road.nodes[0];//czerwone
            Road.lights_on[1] = Road.nodes[3];
            Road.lights_on[2] = Road.nodes[9];
            Road.lights_on[3] = Road.nodes[11];
            Road.lights_on[4] = Road.nodes[13];
        }
        else if (1 == Simulation.lights) {
            Simulation.lights = 2;
            Road.lights_on[0] = Road.nodes[1];//czerwone
            Road.lights_on[1] = Road.nodes[4];
            Road.lights_on[2] = Road.nodes[6];
            Road.lights_on[3] = Road.nodes[8];
            Road.lights_on[4] = Road.nodes[10];

        }
        else  {
            Simulation.lights = 0;

            Road.lights_on[0] = Road.nodes[2];//czerwone
            Road.lights_on[1] = Road.nodes[5];
            Road.lights_on[2] = Road.nodes[2];
            Road.lights_on[3] = Road.nodes[11];
            Road.lights_on[4] = Road.nodes[12];
        }

        System.out.println("Lights flag:"+Simulation.lights);
    }
}

public class Simulation extends Application {

    public static final int LANE_LENGTH = 1666;
    public static int lights = 0; //światła
    private long cntcars = 0;
    private static Label cnt  = new Label("0");

    Road road = new Road();
    File file = new File(".\\cars.txt");
    int cnt_file = 0;///
    int probability = 1;
    BufferedWriter writer;// = new BufferedWriter(new FileWriter(file, true));
    BufferedWriter wr;///
    String file_number;

    ArrayList<ArrayList<Car>> lanes = new ArrayList<ArrayList<Car>>(){{add(road.third_lane_back);
                                                                      add(road.second_lane_back);
                                                                      add(road.first_lane_back);
                                                                      add(road.first_lane);
                                                                      add(road.second_lane);
                                                                      add(road.third_lane); }};//,road.second_lane_back,road.first_lane_back,road.first_lane,road.second_lane,road.third_lane};


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Traffic Simulation");

        Group root = new Group();
        Scene scene = new Scene(root, 350, 100);
        primaryStage.setScene(scene);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(20);


        final Slider slider = new Slider(1, 5, 2);//min,max,acc
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        slider.setMinorTickCount(0); // Disable minor ticks
        slider.setSnapToTicks(true); //przyciaganie
        slider.setMaxWidth(200);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                probability = new_val.intValue();
                System.out.println("probability:"+ probability);
            }
        });

        final Label intensity = new Label("Intensity:");
        final Label amount = new Label("Amount of cars:");

        intensity.setFont(Font.font("Cambria", 20));
        amount.setFont(Font.font("Cambria", 20));
        cnt.setFont(Font.font("Cambria", 20));


        GridPane.setConstraints(intensity, 0, 0);//kolumna wiersy
        GridPane.setConstraints(slider, 1, 0);
        GridPane.setConstraints(amount, 0, 1);
        GridPane.setConstraints(cnt, 1, 1);
        grid.getChildren().addAll(intensity,slider,amount,cnt);
        scene.setRoot(grid);


        //https://riptutorial.com/javafx/example/7291/updating-the-ui-using-platform-runlater
        // longrunning operation runs on different thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        try {
                            simulate();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                    }

                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }

        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();

        primaryStage.show();
    }


    void simulate () throws IOException {

    //http://zetcode.com/java/createfile/

        file_number = String.valueOf(cnt_file);
        if (cnt_file < 10)
            file_number = "00" + cnt_file;
        else if (cnt_file < 100)
            file_number = "0" + cnt_file;

        //FileUtils.cleanDirectory(directory);
        String path = ".\\test_files\\" + file_number;///
        File f = new File(path);///
        cnt_file++;///
        wr = new BufferedWriter(new FileWriter(f, false));
        writer = new BufferedWriter(new FileWriter(file, false));
        wr.write("");

        for (ArrayList<Car> lane : lanes) {
            for (int j = 0; j < lane.size(); ++j) {
                if (lane.get(j) == null) {writer.append("0"); wr.append("0");}
                else {writer.append("1");wr.append("1");}
            }
            writer.append("\n");
            wr.append("\n");
        }

        wr.close();
        writer.close();

        print_lanes();

        road.generate_car( probability+1);//probability od 5 do 2
        road.move_cars();

        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n\n\n\n");
    }

    public static void main(String[] args) {
        Timer timer = new Timer(true);
        timer.schedule(new Lights(), 0, 20000);
        launch(args);
    }


    private void print_lane(ArrayList<Car> lane,int row)  {

        --row;

        for (int i = 0; i < lane.size(); ++i) {

            if (lane.get(i) != null) {
                System.out.print("X(" + lane.get(i).getV_current() + ").");
                cntcars++;
            }
            else
                System.out.print("____.");
        }
        System.out.println();
    }


    void print_lanes(){
        cntcars = 0;
        print_lane(road.third_lane_back,3);
            System.out.println();
        print_lane(road.second_lane_back,2);
            System.out.println();
        print_lane(road.first_lane_back,1);
            System.out.println("\n");
        print_lane(road.first_lane,4);
            System.out.println();
        print_lane(road.second_lane,5);
            System.out.println();
        print_lane(road.third_lane,6);
            System.out.println("\n\n\n\n");

        cnt.setText(Long.toString(cntcars));    //policzone samochody
    }
}




