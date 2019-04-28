import java.util.ArrayList;

public class Road {
    ArrayList<Car> left_lane = new ArrayList<>();
    ArrayList<Car> right_lane = new ArrayList<>();

    Road() {
        for(int i=0; i<100; ++i) {
            left_lane.add(null);
            right_lane.add(null);
        }
    }

    void generate_car(int v) {
        Car car = new Car(v);
        left_lane.set(0, car);
    }

    void move_cars() {
        loop:
        for(int i=left_lane.size()-1; i>=0; --i) {

            if(left_lane.get(i) != null) {
                Car current_car = left_lane.get(i);

                if(current_car.getV_current()+1 <= current_car.getV_max())    // acceleration
                    current_car.setV_current(current_car.getV_current()+1);

                if(i + current_car.getV_current() >= left_lane.size()) {     // car meets end of list
                    left_lane.set(i, null);
                    continue loop;
                }

                for(int j = 1; j <= current_car.getV_current(); ++j) {       // check safety distance from other cars
                    if (left_lane.get(i+j) != null) {
                        current_car.setV_current(j - 1);
                        break;
                    }
                }

                left_lane.set(i + current_car.getV_current(), current_car);     // move car
                left_lane.set(i, null);
            }
        }
    }
}
