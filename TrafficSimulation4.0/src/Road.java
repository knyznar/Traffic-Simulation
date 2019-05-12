import java.util.ArrayList;
import java.util.Random;

class Road {
    ArrayList<Car> first_lane = new ArrayList<>();
    ArrayList<Car> second_lane = new ArrayList<>();
    ArrayList<Car> third_lane = new ArrayList<>();
    //ArrayList<Car> first_lane_back = new ArrayList<>();
    //ArrayList<Car> second_lane_back = new ArrayList<>();
    //ArrayList<Car> third_lane_back = new ArrayList<>();

    private Random random_seed = new Random();

    public static int[] nodes = {30,50,80};     //skrzyżowania

    Road() {
        for(int i=0; i<100; ++i) {
            first_lane.add(null);
            second_lane.add(null);
            third_lane.add(null);
            //first_lane_back.add(null);
            //second_lane_back.add(null);
            //third_lane_back.add(null);
        }
    }

    void generate_car() {
        if(random_seed.nextBoolean()) {
            int v_max = random_seed.nextInt(3) + 4;
            first_lane.set(0, new Car(v_max,generate_dest()));
        }
        if(random_seed.nextBoolean()) {
            int v_max = random_seed.nextInt(3) + 4;
            second_lane.set(0, new Car(v_max,generate_dest()));
        }
        if(random_seed.nextBoolean()) {
            int v_max = random_seed.nextInt(3) + 4;
            third_lane.set(0, new Car(v_max,generate_dest()));
        }
    }

    Destination generate_dest() {
        Side side = Side.values()[random_seed.nextInt(2)];
        int node = Road.nodes[random_seed.nextInt(Road.nodes.length)];
        return new Destination(node, side);
    }


    boolean change_lane_to_left (Car car, ArrayList<Car> current_lane, int nrOfLine) {

        ArrayList<Car> other_lane;


        if (nrOfLine == 1) return false;
        else if (nrOfLine == 3) other_lane = second_lane;
        else other_lane = first_lane;



        boolean can_change_lane = false;
        int position = current_lane.indexOf(car);


        //loop:
        //for (int i = 1; i <= 1 ; ++i) {

            // if (position + i < current_lane.size() && current_lane.get(position+i) != null && current_lane.get(position+i).getV_max() < car.getV_max()) {   //need to change
            if (current_lane.get(position+1) == null && current_lane.get(position+2) == null) {   //dwa miejsca przed samochodem muszą być wolne zeby mógł zmienić pas

                for (int j = 0; j <= car.getV_current() - 2; ++j) {  //będzie wystarczająco miejsca zeby zmienic pas z przodu
                    if (other_lane.get(position + j) != null)
                        return false;   //break loop;
                }
                //if (other_lane.get(position-1) != null)

                for (int k = 1; k <= car.getV_current() + 2; ++k) {   //safety distance
                    if (position + k >= current_lane.size()) return false;   //break loop;
                    if (other_lane.get(position + k) != null && position + k - 2 >= 0) {
                        //car.setV_current(Math.max(0, i - 2));


                        if (other_lane.get(position + k).getV_current() < car.getV_current())
                            car.setV_current(other_lane.get(position + k).getV_current());

                        other_lane.set(position + k - 2, car);     // move car
                        current_lane.set(position, null);
                        //can_change_lane = true;
                        System.out.println("zmiana pasa na lewy: " + 5*position );
                        return true;

                    }
                }

                System.out.println("zmianaa pasa na lewy: " + 5*position );
                other_lane.set(position + car.getV_current(), car);     // move car
                current_lane.set(position, null);
                return true;
            }
       // }
        //return  can_change_lane;
        return false;
    }

    boolean change_lane_to_right (Car car, ArrayList<Car> current_lane, int nrOfLine) {

        ArrayList<Car> other_lane;

        if (nrOfLine == 3) return false;
        else if (nrOfLine == 2) other_lane = third_lane;
        else other_lane = second_lane;

        int position = current_lane.indexOf(car);

        if (current_lane.get(position+1) == null && current_lane.get(position+2) == null) {   //dwa miejsca przed samochodem muszą być wolne zeby mógł zmienić pas

            for (int j = 0; j <= car.getV_current() - 2; ++j) {  //będzie wystarczająco miejsca zeby zmienic pas z przodu
                if (other_lane.get(position + j) != null)
                    return false;   //break loop;
            }

            for (int k = 1; k <= car.getV_current() + 2; ++k) {   //safety distance
                if (position + k >= current_lane.size()) return false;   //break loop;
                if (other_lane.get(position + k) != null && position + k - 2 >= 0) {

                    if (other_lane.get(position + k).getV_current() < car.getV_current())
                        car.setV_current(other_lane.get(position + k).getV_current());

                    other_lane.set(position + k - 2, car);     // move car
                    current_lane.set(position, null);

                    System.out.println("zmiana pasa na prawy: " + 5*position );
                    return true;

                }
            }

            System.out.println("zmianaa pasa na prawy: " + 5*position );
            other_lane.set(position + car.getV_current(), car);     // move car
            current_lane.set(position, null);
            return true;
        }
        return false;
    }

    private void move_single_car(Car car, ArrayList<Car> lane, int nrOfLine) {
        int position = lane.indexOf(car);

        if(car.getV_current()+1 <= car.getV_max())    // acceleration
            car.setV_current(car.getV_current()+1);

        if(position + car.getV_current() >= lane.size()) {     // car meets end of list
            lane.set(position, null);
            return;
        }

        for(int i = 1; i <= car.getV_current()+2 ; ++i) {       // check safety distance from other cars    //and/or change lane
            if (position + i < lane.size() && lane.get(position+i) != null) {
                //car.setV_current(Math.max(0, i - 2));
                //if (lane.get(position+i).getV_current() >= lane.size()) break;

                if (lane.get(position+i).getV_max() < car.getV_max()) { //jesli moze jechac szybciej niz ten przed nim to próbuje zminić pas
                        if (change_lane_to_left(car, lane, nrOfLine))
                            return;
                    if (change_lane_to_right(car, lane, nrOfLine))
                        return;
                }

                else if (position - 2 >= 0 && lane.get(position-2) != null && lane.get(position-2).getV_max() > car.getV_max()) { //jeśli samochód z tyłu może jechać szybciej niż ja to zjedz na prawo
                        if (change_lane_to_right(car, lane, nrOfLine))
                            return;
                }

                if (lane.get(position+i).getV_current() < car.getV_current())
                        car.setV_current(lane.get(position+i).getV_current());

                if (position + i - 2 < 0) ++i;
                lane.set(position+i - 2, car);     // move car
                lane.set(position, null);
                return;
            }
        }

        boolean rand_slow = random_seed.nextInt(10)==0;          // randomly reduce velocity by 1 (probability p = 1/10)
        if(rand_slow)
            car.setV_current(car.getV_current()-1);

        boolean rand_acc = random_seed.nextInt(10)==0;          // randomly increase velocity by 1 (probability p = 1/10)
        if(rand_acc && !rand_slow && car.getV_current() >= car.getV_max() && position + car.getV_current()+1 < lane.size())
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
