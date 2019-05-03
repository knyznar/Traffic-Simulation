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

            boolean gen_car = random_seed.nextBoolean();
            int lane = random_seed.nextInt(3);      // 0 - first_lane, 1 - second_lane, 2 - third_lane
            if (gen_car) {
                int v_max = random_seed.nextInt(3) + 4;
                if(lane == 0)
                    road.generate_car(v_max, road.first_lane);
                else if(lane == 1)
                    road.generate_car(v_max, road.second_lane);
                else if(lane == 2)
                    road.generate_car(v_max, road.third_lane);
            }
            road.move_cars(road.first_lane);
            road.move_cars(road.second_lane);
            road.move_cars(road.third_lane);
        }
    }

    public static void print_lane(ArrayList<Car> lane)  {
        for (int i = 0; i < lane.size(); ++i) {
            if (lane.get(i) != null)
                System.out.print("X(" + lane.get(i).getV_current() + ").");
            else
                System.out.print("____.");
        }
        System.out.println();
    }
}
