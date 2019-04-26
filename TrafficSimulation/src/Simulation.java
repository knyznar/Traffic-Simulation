import java.util.concurrent.TimeUnit;

public class Simulation {

    public static void main(String[] args) throws Exception{
        Road road = new Road();

        while(true) {
            for (int i = 0; i < Consts.WIDTH; ++i) {
                for (int j = 0; j < Consts.LENGTH; ++j) {
                    if (0 == i || Consts.WIDTH-1 == i)
                        System.out.print("-");
                    else if (road.map[i][j] >= 0)
                        System.out.print("X");
                    else
                        System.out.print(" ");

                }
                System.out.println();
            }
            TimeUnit.SECONDS.sleep(1);
            System.out.println("\n\n\n");

            int id = road.is_new_car();
            if ( id >= 0 ) {
                Car car = new Car(id);

                int line = road.random_line() + 1;
                road.map[line][0] = id;
                road.cars.add(car);
            }
            //for (Car c : road.cars)
            road.move_cars();
        }
    }
}
