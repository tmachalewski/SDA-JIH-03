package pl.sda;

import java.util.ArrayList;
import java.util.List;

public class Car {
    int id;
    String kind;
    List<Person> usedBy = new ArrayList<>();

    public Car() {
    }

    public Car(int id, String kind) {
        this.id = id;
        this.kind = kind;
    }
}
