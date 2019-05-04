import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Simulation {

    public static void main(String[] args) throws Exception {
        Road road = new Road();
        Random random_seed = new Random();

        while (true) {
            print_lane(road.first_lane);
            System.out.println();
            print_lane(road.second_lane);
            System.out.println();
            print_lane(road.third_lane);
            System.out.println("\n\n\n\n");
            TimeUnit.SECONDS.sleep(1);

            road.generate_car();
            road.move_cars();
        }
    }

    private static void print_lane(ArrayList<Car> lane)  {
        for (int i = 0; i < lane.size(); ++i) {
            if (lane.get(i) != null)
                System.out.print("X(" + lane.get(i).getV_current() + ").");
            else
                System.out.print("____.");
        }
        System.out.println();

    }
}
