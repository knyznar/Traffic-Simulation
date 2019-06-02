class Car {

    private int v_max, v_current;
    private Destination destination;

    Car(int v, Destination dest) {
        v_max = v;
        v_current = 0;
        destination = dest;
    }

    void setV_current(int v) {
        v_current = v;
    }

    int getV_current() {
        return v_current;
    }

    int getV_max() {
        return v_max;
    }

    Destination get_dest() { return destination; }

    void set_Destination(Destination dest) {
        destination = dest;
    }
}
