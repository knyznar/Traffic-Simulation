import java.util.ArrayList;
import java.util.Random;

public class Road {
    ArrayList<Car> first_lane = new ArrayList<>();
    ArrayList<Car> second_lane = new ArrayList<>();
    ArrayList<Car> third_lane = new ArrayList<>();

    Road() {
        for(int i=0; i<100; ++i) {
            first_lane.add(null);
            second_lane.add(null);
            third_lane.add(null);
        }
    }
    ;
    void generate_car(int v, ArrayList<Car> lane) {
        Car car = new Car(v);
        lane.set(0, car);
    }

    void move_cars(ArrayList<Car> lane) {
        Random random_seed = new Random();

        loop:
        for(int i=lane.size()-1; i>=0; --i) {

            if(lane.get(i) != null) {
                Car current_car = lane.get(i);

                if(current_car.getV_current()+1 <= current_car.getV_max())    // acceleration
                    current_car.setV_current(current_car.getV_current()+1);

                if(i + current_car.getV_current() >= lane.size()) {     // car meets end of list
                    lane.set(i, null);
                    continue loop;
                }

                for(int j = 1; j <= current_car.getV_current(); ++j) {       // check safety distance from other cars
                    if (lane.get(i+j) != null) {
                        current_car.setV_current(Math.max(0, j - 2));
                        break;
                    }
                }

                boolean rand_slow = random_seed.nextInt(10)==0;          // randomly reduce velocity by 1 (probability p = 1/10)
                if(rand_slow)
                    current_car.setV_current(current_car.getV_current()-1);


                lane.set(i + current_car.getV_current(), current_car);     // move car
                lane.set(i, null);
            }
        }
    }
}
