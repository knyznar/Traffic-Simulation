import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Simulation {

    public static void main(String[] args) throws Exception{
        Road road = new Road();
        Random random_seed = new Random();

        while(true) {
            for(int i = 0; i < road.left_lane.size(); ++i) {
                if(road.left_lane.get(i) != null)
                    System.out.print("X(" + road.left_lane.get(i).getV_current() + ")");
                else
                    System.out.print("____");
            }
            System.out.println();

            TimeUnit.SECONDS.sleep(1);
            System.out.println("\n\n\n\n");

            boolean gen_car = random_seed.nextBoolean();
            if(gen_car) {
                int v_max = random_seed.nextInt(3) + 4;
                road.generate_car(v_max);
            }
            road.move_cars();

        }


    }
}
