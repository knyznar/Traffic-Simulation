import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Consts{
    public static final int LENGTH = 100;
    public static final int WIDTH = 5;
}

public class Road {
    public int[][] map = new int[Consts.WIDTH][Consts.LENGTH];
    public int count = -1;
    public List<Car> cars = new ArrayList<Car>();

    public Road() {
        for (int i = 0; i < Consts.WIDTH; ++i)
            for (int j = 0; j < Consts.LENGTH; ++j) {
                if (0 == i || 4 == i)
                    map[i][j] = -2;
                else
                    map[i][j] = -1;

            }
    }

    public int is_new_car() {
        Random random = new Random();
        boolean is_car = random.nextBoolean();
        int car_id = -1;
        if(is_car == true)
             car_id = count + 1;
        return car_id;
    }

    public void move_cars() {
        int v_acc;
        loop:
        for (int i = 0; i < Consts.WIDTH; ++i)
            for (int j = 0; j < Consts.LENGTH; ++j) {

                if (map[i][j] >= 0) {
                    v_acc = cars.get(map[i][j]).v_init;//actual


                    for (int k = 1; k <= v_acc; ++k) {
                        if (j+k >= Consts.LENGTH) {
                            map[i][j] = -1;
                            continue loop;
                        }
                        if (map[i][j+k] != -1) {
                            v_acc = k - 1;
                            break;
                        }
                    }

                    if (map[i][j+v_acc]==-1) {
                        Car car = new Car(map[i][j]);
                        //Car car = new Car(map[i][j],i,j+v_acc);
                        //cars.set(map[i][j]).x = cars.get(map[i][j]).x + cars.get(map[i][j]).v_init;
                        map[i][j+v_acc] = map[i][j];
                        map[i][j] = -1;
                        j = j + v_acc;
                        cars.set(map[i][j],car);
                    }
                }
            }
    }

    public int random_line() {
        Random random= new Random();
        int line = random.nextInt(3);

        return line;
    }
}