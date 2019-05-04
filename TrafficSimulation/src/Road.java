import java.util.ArrayList;
import java.util.Random;

class Road {
    ArrayList<Car> first_lane = new ArrayList<>();
    ArrayList<Car> second_lane = new ArrayList<>();
    ArrayList<Car> third_lane = new ArrayList<>();
    private Random random_seed = new Random();

    Road() {
        for(int i=0; i<100; ++i) {
            first_lane.add(null);
            second_lane.add(null);
            third_lane.add(null);
        }
    }

    void generate_car() {
        if(random_seed.nextBoolean()) {
            int v_max = random_seed.nextInt(3) + 4;
            first_lane.set(0, new Car(v_max));
        }
        if(random_seed.nextBoolean()) {
            int v_max = random_seed.nextInt(3) + 4;
            second_lane.set(0, new Car(v_max));
        }
        if(random_seed.nextBoolean()) {
            int v_max = random_seed.nextInt(3) + 4;
            third_lane.set(0, new Car(v_max));
        }
    }

    boolean change_lane(Car car, ArrayList<Car> current_lane, ArrayList<Car> next_lane) {
        boolean can_change_lane = true;
        int position = current_lane.indexOf(car);

        for(int i = position-car.getV_current(); i < position+car.getV_current(); ++i) {
            if(next_lane.get(i) != null) {
                can_change_lane = false;
                break;
            }
        }
        return can_change_lane;
    }

    private void move_single_car(Car car, ArrayList<Car> lane, int nrOfLine) {
        int position = lane.indexOf(car);

        if(car.getV_current()+1 <= car.getV_max())    // acceleration
            car.setV_current(car.getV_current()+1);

        if(position + car.getV_current() >= lane.size()) {     // car meets end of list
            lane.set(position, null);
            return;
        }

        for(int i = 1; i <= car.getV_current(); ++i) {       // check safety distance from other cars
            if (lane.get(position+i) != null) {
                car.setV_current(Math.max(0, i - 2));
                break;
            }
        }

        boolean rand_slow = random_seed.nextInt(10)==0;          // randomly reduce velocity by 1 (probability p = 1/10)
        if(rand_slow)
            car.setV_current(car.getV_current()-1);


        lane.set(position + car.getV_current(), car);     // move car
        lane.set(position, null);
    }

    void move_cars() {

        for(int i=first_lane.size()-1; i>=0; --i) {
            if(first_lane.get(i) != null)
                move_single_car(first_lane.get(i), first_lane, 1);
            if(second_lane.get(i) != null)
                move_single_car(second_lane.get(i), second_lane, 2);
            if(third_lane.get(i) != null)
                move_single_car(third_lane.get(i), third_lane, 3);
        }
    }
}
