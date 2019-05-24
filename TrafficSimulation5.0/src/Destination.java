import java.util.Random;

enum Side{left, right}

class Destination {
    int id;
    Side side;

    private Random random_seed = new Random();

    Destination(int i, Side s){
        id = i;
        side = s;
    }

    void set_Destination(int i, Side s) {
        id = i;
        side = s;
    }

    int get_id() {
        return id;
    }

    Side get_Side() {
        return side;
    }


}

