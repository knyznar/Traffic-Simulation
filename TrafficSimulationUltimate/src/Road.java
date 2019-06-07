import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

class Road {
    ArrayList<Car> first_lane = new ArrayList<>();  //1
    ArrayList<Car> second_lane = new ArrayList<>(); //2
    ArrayList<Car> third_lane = new ArrayList<>(); //3
    ArrayList<Car> first_lane_back = new ArrayList<>(); //4
    ArrayList<Car> second_lane_back = new ArrayList<>(); //5
    ArrayList<Car> third_lane_back = new ArrayList<>(); //6

    private Random random_seed = new Random();

    public static int[] nodes = {80,120,173,213,280,347,453,666,920,1067,1306,1514,1627,1660};     //crossings
    public static int[] lights_on = new int[5];
    //public static int[] lights_off = new int[7];

    Road() {
        for(int i=0; i<Simulation.LANE_LENGTH; ++i) {
            first_lane.add(null);
            second_lane.add(null);
            third_lane.add(null);
            first_lane_back.add(null);
            second_lane_back.add(null);
            third_lane_back.add(null);
        }
    }

    void generate_car( int probability) {       // na konkretnych pasach z konkretnym prawdopodobieństwem (5 największe 2najmniejsze )

        for (int i = 0; i < lights_on.length; ++i) {
            if (first_lane.get(lights_on[i]+2) == null && random_seed.nextInt(probability) != 0 ) {//?
                int v_max = random_seed.nextInt(3) + 4;
                first_lane.set(lights_on[i]+2, new Car(v_max, generate_dest()));
            }
            if (second_lane.get(lights_on[i]+2) == null && random_seed.nextInt(probability) != 0 ) {//?
                int v_max = random_seed.nextInt(3) + 4;
                second_lane.set(lights_on[i]+2, new Car(v_max, generate_dest()));
            }
            if (third_lane.get(lights_on[i]+2) == null && random_seed.nextInt(probability) != 0 ) {//?
                int v_max = random_seed.nextInt(3) + 4;
                third_lane.set(lights_on[i]+2, new Car(v_max, generate_dest()));
            }

            if (first_lane_back.get(lights_on[i]-1) == null && random_seed.nextInt(probability) != 0 ) {//?
                int v_max = random_seed.nextInt(3) + 4;
                first_lane_back.set(lights_on[i]-1, new Car(v_max, generate_dest()));
            }
            if (second_lane_back.get(lights_on[i]-1) == null && random_seed.nextInt(probability) != 0 ) {//?
                int v_max = random_seed.nextInt(3) + 4;
                second_lane_back.set(lights_on[i]-1, new Car(v_max, generate_dest()));
            }
            if (third_lane_back.get(lights_on[i]-1) == null && random_seed.nextInt(probability) != 0 ) {//?
                int v_max = random_seed.nextInt(3) + 4;
                third_lane_back.set(lights_on[i]-1, new Car(v_max, generate_dest()));
            }
        }
    }

    Destination generate_dest() {
        Side side = Side.values()[random_seed.nextInt(2)];
        int node = Road.nodes[random_seed.nextInt(Road.nodes.length)];
        return new Destination(node, side);
    }


    boolean change_lane_to_left (Car car, ArrayList<Car> current_lane, int nrOfLine) {

        ArrayList<Car> other_lane;
        int position = current_lane.indexOf(car);

        if (nrOfLine == 1) return false;
        else if (nrOfLine == 4) return false;
        else if (nrOfLine == 3) other_lane = second_lane;
        else if (nrOfLine == 2) other_lane = first_lane;
        else if (nrOfLine == 5) other_lane = first_lane_back;
        else   other_lane = second_lane_back; //(nrOfLine == 6)

        if (nrOfLine < 4) { //prawe pasy

            int h = 1;
            if (position + h >= current_lane.size()) h = h - current_lane.size();
            int g = 2;
            if (position + g >= current_lane.size()) g = g - current_lane.size();

            if (current_lane.get(position + h) == null && current_lane.get(position + g) == null) {
                for (int j = 0; j <= car.getV_current() - 2; ++j) {  //będzie wystarczająco miejsca zeby zmienic pas z przodu
                    if (position + j >= other_lane.size()) j = j + (-position) - (other_lane.size() - position);
                    if (other_lane.get(position + j) != null)
                        return false;   //break loop;
                }
                int temp = -1;
                for (int k = 1; k <= car.getV_current() + 2; ++k) {   //safety distance
                    if (position + k >= current_lane.size()) {
                        temp = k;
                        k = k - current_lane.size();
                    }
                    if (other_lane.get(position + k) != null && position + k - 2 >= 0) {
                        if (other_lane.get(position + k).getV_current() < car.getV_current())
                            car.setV_current(other_lane.get(position + k).getV_current());

                        other_lane.set(position + k - 2, car);     // move car
                        current_lane.set(position, null);
                        return true;
                    } if (temp != -1) k = temp;
                }

                if (position + car.getV_current() >= other_lane.size()) {
                    int new_position = position + car.getV_current() - other_lane.size();
                    other_lane.set(new_position, car);
                }
                else other_lane.set(position + car.getV_current(), car);     // move car

                current_lane.set(position, null);
                return true;
            }
            return false;
        }
        else {  //leftlines
            int h = 1;
            if (position - h < 0) h = h - current_lane.size();
            int g = 2;
            if (position - g < 0) g = g - current_lane.size();

            if (current_lane.get(position - h) == null && current_lane.get(position - g) == null) {
                for (int j = 0; j <= car.getV_current() - 2; ++j) {  //będzie wystarczająco miejsca zeby zmienic pas z przodu
                    if (position - j < 0) j = j - other_lane.size();
                    if (other_lane.get(position - j) != null)
                        return false;
                }

                int temp = -1;
                for (int k = 1; k <= car.getV_current() + 2; ++k) {   //safety distance
                    if (position - k < 0) {
                        temp = k;
                        k = k - current_lane.size();
                    }
                    if (other_lane.get(position - k) != null) {
                        if (other_lane.get(position - k).getV_current() < car.getV_current())
                            car.setV_current(other_lane.get(position - k).getV_current());
                        if (position - k + 2 >= other_lane.size()) {
                            k = k +other_lane.size();
                        }

                        other_lane.set(position - k + 2, car);     // move car
                        current_lane.set(position, null);
                        return true;
                    }
                    if (-1 != temp) k = temp;
                }

                if (position - car.getV_current() < 0) position = position + other_lane.size();
                other_lane.set(position - car.getV_current(), car);     // move car
                if (position >= other_lane.size()) position = position - other_lane.size();
                current_lane.set(position, null);
                return true;
            }
            return false;
        }
    }

    boolean change_lane_to_right (Car car, ArrayList<Car> current_lane, int nrOfLine) {

        ArrayList<Car> other_lane;
        int position = current_lane.indexOf(car);

        if (nrOfLine == 3) return false;
        else if (nrOfLine == 6) return false;
        else if (nrOfLine == 2) other_lane = third_lane;
        else if (nrOfLine == 1) other_lane = second_lane;
        else if (nrOfLine == 4) other_lane = second_lane_back;
        else other_lane = third_lane_back;  //if(nrOfLine == 5)



        if (nrOfLine < 4) {
            int h = 1;
            if (position + h >= current_lane.size()) h = h + (-position) - (current_lane.size() - position);
            int g = 2;
            if (position + g >= current_lane.size()) g = g + (-position) - (current_lane.size() - position);

            if (current_lane.get(position + h) == null && current_lane.get(position + g) == null) {   //dwa miejsca przed samochodem muszą być wolne zeby mógł zmienić pas

                for (int j = 0; j <= car.getV_current() - 2; ++j) {  //będzie wystarczająco miejsca zeby zmienic pas z przodu
                    if (position + j >= current_lane.size())
                        j = j + (-position) - (current_lane.size() - position);   // current_lane.size()+j-position;//end of lane
                    if (other_lane.get(position + j) != null)
                        return false;   //break loop;
                }

                for (int k = 1; k <= car.getV_current() + 2; ++k) {   //safety distance
                    if (position + k >= current_lane.size())
                        k = k + (-position) - (current_lane.size() - position);  //return false;   //break loop;
                    if (other_lane.get(position + k) != null && position + k - 2 >= 0) {

                        if (other_lane.get(position + k).getV_current() < car.getV_current())
                            car.setV_current(other_lane.get(position + k).getV_current());

                        other_lane.set(position + k - 2, car);     // move car
                        current_lane.set(position, null);

                        return true;

                    }
                }

                other_lane.set(position + car.getV_current(), car);     // move car
                current_lane.set(position, null);
                return true;
            }
            return false;
        }
        else {
            int h = 1;
            if (position - h < 0) h = h - current_lane.size();
            int g = 2;
            if (position - g < 0) g = g - current_lane.size();

            if (current_lane.get(position - h) == null && current_lane.get(position - g) == null) {
                for (int j = 0; j <= car.getV_current() - 2; ++j) {  //będzie wystarczająco miejsca zeby zmienic pas z przodu
                    if (position - j < 0) j = j - other_lane.size();
                    if (other_lane.get(position - j) != null)
                        return false;
                }

                int temp = -1;
                for (int k = 1; k <= car.getV_current() + 2; ++k) {   //safety distance
                    if (position - k < 0) {
                        temp = k;
                        k = k - current_lane.size();    //return false;
                    }
                    if (other_lane.get(position - k) != null) {
                        if (other_lane.get(position - k).getV_current() < car.getV_current())
                            car.setV_current(other_lane.get(position - k).getV_current());
                        if (position - k + 2 >= other_lane.size()) {
                            k = k +other_lane.size();
                        }
                        other_lane.set(position - k + 2, car);     // move car
                        current_lane.set(position, null);
                        return true;
                    }
                    if (-1 != temp) k = temp;
                }

                if (position - car.getV_current() < 0) position = position + other_lane.size();
                other_lane.set(position - car.getV_current(), car);     // move car
                if (position >= other_lane.size()) position = position - other_lane.size();
                current_lane.set(position, null);
                return true;
            }
            return false;
        }
    }

    private void move_single_car(Car car, ArrayList<Car> lane, int nrOfLine) {
        int position = lane.indexOf(car);

        if (nrOfLine < 4) {
            int red_light = 0;
            int light_pos=-10;
            for (int i = 0; i < lights_on.length; ++i) {
                for (int j = 0; j < 25; ++j) {  //trzeba będzie zwolnić
                    if (position + j == lights_on[i]) {
                        red_light = 1;
                        light_pos = lights_on[i];
                        break;
                    }
                }
            }

            if (abs(car.get_dest().id - position) < 30) {    //if destination is near
                if (car.getV_current() > 3) car.setV_current(car.getV_current() - 1);
                if (car.get_dest().side == Side.right) {
                    if (change_lane_to_right(car, lane, nrOfLine))
                        return;
                } else if (change_lane_to_left(car, lane, nrOfLine))
                    return;
                if (car.getV_current() - 1 > 0)
                    car.setV_current(car.getV_current() - 1);
            }


            if (1 == red_light) {                                          //jeśli czerwone
                if (car.getV_current() > 0) car.setV_current(car.getV_current() - 1);
            } else if (car.getV_current() + 1 <= car.getV_max())          // acceleration
                car.setV_current(car.getV_current() + 1);


            for (int i = 1; i <= car.getV_current() + 2; ++i) {               // check safety distance from other cars    //and/or change lane
                if (car.getV_current() == 0 || car.getV_current() == 1)
                    break;                                                   //mala predkosc nie potrzebue bezpiecznego dystansu
                int temp = -1;                                               //jeśli skończy się lista
                if (red_light == 0 && i <= car.getV_current() && position + i == car.get_dest().id) {    //&& car.get_dest().side==..        //destination reached
                    lane.set(position, null);
                    return;
                }

                if (position + i >= lane.size()) {
                    temp = i;
                    i = i - lane.size();
                }

                if (lane.get(position + i) != null) {
                    if (lane.get(position+i).getV_current() >= lane.size()) break;

                    if (lane.get(position + i).getV_max() < car.getV_max()) { //jesli moye jechac szybciej niz ten przed nim to próbuje zminić pas
                        if (change_lane_to_left(car, lane, nrOfLine))
                            return;
                        if (change_lane_to_right(car, lane, nrOfLine))
                            return;
                    } else if (position - 2 >= 0 && lane.get(position - 2) != null && lane.get(position - 2).getV_max() > car.getV_max()) { //jeśli samochód z tyłu może jechać szybciej niż ja to zjedz na prawo
                        if (change_lane_to_right(car, lane, nrOfLine))
                            return;
                        if (change_lane_to_left(car, lane, nrOfLine))
                            return;
                    }

                    if (lane.get(position + i).getV_current() < car.getV_current())
                        car.setV_current(lane.get(position + i).getV_current());

                    if (position + i - 2 < 0) i = temp;
                    lane.set(position + i - 2, car);
                    lane.set(position, null);
                    return;
                }
                if (temp != -1) i = temp;
            }

            boolean rand_slow = random_seed.nextInt(10) == 0;          // randomly reduce velocity by 1 (probability p = 1/10)
            if (car.getV_current() > 0 && rand_slow)
                car.setV_current(car.getV_current() - 1);

            //boolean rand_acc = random_seed.nextInt(10)==0;          // randomly increase velocity by 1 (probability p = 1/10)
            //if(rand_acc && !rand_slow && car.getV_current() >= car.getV_max() && position + car.getV_current()+1 < lane.size())
            //    car.setV_current(car.getV_current()+1);

            if (red_light == 1 && position != light_pos && car.getV_current() == 0 && position + 1 < lane.size())    //stoi na światłach
                if (lane.get(position + 1) == null) {
                    lane.set(position + 1, car);
                    lane.set(position, null);
                    return;
                }

            if (position + car.getV_current() >= lane.size()) {
                int new_position = position + car.getV_current() - lane.size();
                lane.set(new_position, car);
            } else lane.set(position + car.getV_current(), car);     // move car
            if (0 != car.getV_current()) lane.set(position, null);
        }

        else {
            int red_light = 0;
            int light_pos = -10;
            loop:
            for (int i = 0; i < lights_on.length; ++i) {
                for (int j = 0; j < 25; ++j) {                       //trzeba będzie zwolnić
                    if (position - j == lights_on[i]) {
                        red_light = 1;
                        light_pos = lights_on[i];
                        break loop;
                    }
                }
            }


            if (abs(car.get_dest().id + position) < 30) {    //if destination is near
                if (car.getV_current() > 3) car.setV_current(car.getV_current() - 1);
                if (car.get_dest().side == Side.right) {
                    if (change_lane_to_right(car, lane, nrOfLine)) return; }
                else if (change_lane_to_left(car, lane, nrOfLine)) return;
                if (car.getV_current() - 1 > 0)
                    car.setV_current(car.getV_current() - 1);     //jeszcze raz zmniejszamy bo zaraz się zwiększy
            }


            if (1 == red_light ) {
                if (car.getV_current() > 0) car.setV_current(car.getV_current() - 1); }
            else if (car.getV_current() + 1 <= car.getV_max())
                car.setV_current(car.getV_current() + 1);


            for (int i = 1; i <= car.getV_current() + 2; ++i) {       // check safety distance from other cars    //and/or change lane
                if (car.getV_current() == 0 || car.getV_current() == 1)
                    break;

                if (red_light == 0 && i <= car.getV_current() && position - i == car.get_dest().id) {    //&& car.get_dest().side==..        //destination reached
                    lane.set(position, null);
                    return;
                }

                int temp = -1;  //jeśli skończy się lista
                if (position - i < 0) {
                    temp = i;
                    i = i - lane.size();
                }

                if (lane.get(position - i) != null) {

                    if (lane.get(position - i).getV_max() < car.getV_max()) { //jesli moze jechac szybciej niz ten przed nim to próbuje zminić pas
                        if (change_lane_to_left(car, lane, nrOfLine))
                            return;
                        if (change_lane_to_right(car, lane, nrOfLine))
                            return;
                    } else if (position + 2 < lane.size() && lane.get(position + 2) != null && lane.get(position + 2).getV_max() > car.getV_max()) { //jeśli samochód z tyłu może jechać szybciej niż ja to zjedz na prawo
                        if (change_lane_to_right(car, lane, nrOfLine))
                            return;
                        if (change_lane_to_left(car, lane, nrOfLine))
                            return;
                    }

                    if (lane.get(position - i).getV_current() < car.getV_current()) //nie udało się zmienić pasa
                        car.setV_current(lane.get(position - i).getV_current());

                    if (position - i + 2 >= lane.size()) i = temp;

                    lane.set(position - i + 2, car);
                    lane.set(position, null);
                    return;
                }
                if (temp != -1) i = temp;
            }

            boolean rand_slow = random_seed.nextInt(10) == 0;          // randomly reduce velocity by 1 (probability p = 1/10)
            if (car.getV_current() > 0 && rand_slow)
                car.setV_current(car.getV_current() - 1);

            //boolean rand_acc = random_seed.nextInt(10)==0;          // randomly increase velocity by 1 (probability p = 1/10)
            //if(rand_acc && !rand_slow && car.getV_current() >= car.getV_max() && position + car.getV_current()+1 < lane.size())
            //    car.setV_current(car.getV_current()+1);

            if (red_light == 1 && position != light_pos+3 && car.getV_current() == 0 && position - 1 >= 0)
                if (lane.get(position - 1) == null) {
                    lane.set(position - 1, car);
                    lane.set(position, null);
                    return;
                }

            if (position - car.getV_current() < 0) {
                int new_position = lane.size() + position - car.getV_current();
                lane.set(new_position, car);
            }
            else
                lane.set(position - car.getV_current(), car);     // move car

            if (0 != car.getV_current())
                lane.set(position, null);
        }
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

        for(int i=0; i < first_lane_back.size(); ++i) {
            if(first_lane_back.get(i) != null)
                move_single_car(first_lane_back.get(i), first_lane_back, 4);
            if(second_lane_back.get(i) != null)
                move_single_car(second_lane_back.get(i), second_lane_back, 5);
            if(third_lane_back.get(i) != null)
                move_single_car(third_lane_back.get(i), third_lane_back, 6);
        }
    }
}
