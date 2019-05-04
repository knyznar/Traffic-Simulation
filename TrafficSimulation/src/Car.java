class Car {

    private int v_max, v_current;

    Car(int v) {
        v_max = v;
        v_current = 0;
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
}
