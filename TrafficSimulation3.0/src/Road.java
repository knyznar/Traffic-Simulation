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
        if(random_seed.nextBoolean() ) {
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

    boolean change_lane(Car car, ArrayList<Car> current_lane, int nrOfLine) {

        ArrayList<Car> other_lane1, other_lane2;

        if (nrOfLine == 1) {
            other_lane1 = second_lane;
        }
        else if (nrOfLine == 2) {
            other_lane1 = first_lane;
            other_lane2 = third_lane;
        }
        else {
            other_lane1 = second_lane;
        }



        boolean can_change_lane = false;
        int position = current_lane.indexOf(car);

        loop:
        for (int i = 1; i <= car.getV_current() ; ++i) {
            if (position + i < 100 && current_lane.get(position+i) != null && current_lane.get(position+i).getV_max() < car.getV_max()) {   //need to change
                for (int j = 0; j <= 4; ++j) {
                    if (other_lane1.get(position+j) != null || other_lane1.get(position-j) != null )
                        break loop;
                }
                current_lane.set(position,null);
                other_lane1.set(position+3, car);
                can_change_lane = true;

                //if (current_lane.get(position) != null ) {  //jeśli już nie zmieniliśmy pasa
                   System.out.println("\nzmiana pasa\n");
                //}
            }
        }
        return  can_change_lane;
    }

    private void move_single_car(Car car, ArrayList<Car> lane, int nrOfLine) {
        int position = lane.indexOf(car);

        if(car.getV_current()+1 <= car.getV_max())    // acceleration
            car.setV_current(car.getV_current()+1);

        if(position + car.getV_current() >= lane.size()) {     // car meets end of list
            lane.set(position, null);
            return;
        }

        //if (change_lane(car, lane, nrOfLine) == true)
        //    return;

        for(int i = 1; i <= car.getV_current()+2 ; ++i) {       // check safety distance from other cars    //or change lane
            if (position + i < lane.size() && lane.get(position+i) != null) {
                //car.setV_current(Math.max(0, i - 2));
                if (lane.get(position+i).getV_current() >= lane.size()) break;
                car.setV_current(lane.get(position+i).getV_current());

                lane.set(position+i - 2, car);     // move car
                lane.set(position, null);
                return;
                //break;
            }
        }

        boolean rand_slow = random_seed.nextInt(10)==0;          // randomly reduce velocity by 1 (probability p = 1/10)
        if(rand_slow)
            car.setV_current(car.getV_current()-1);

        boolean rand_acc = random_seed.nextInt(10)==0;          // randomly increase velocity by 1 (probability p = 1/10)
        if(car.getV_current() == car.getV_max() && rand_acc && position + car.getV_current() < lane.size() )
            car.setV_current(car.getV_current()+1);

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
