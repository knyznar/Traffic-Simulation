import java.util.Random;

public class Car {
    int x ,y;
    int id;
    int v_init;
    int dest;


    Car(int car_id) {
        id = car_id;
        Random random= new Random();
        v_init = random.nextInt(3) + 4;
        //this.x = x;
        //this.y = y;
    }

    /*public void move(int id) {
            x += v_init;
    }*/



}
