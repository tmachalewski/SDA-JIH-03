package pl.sda;

import java.util.List;
import java.util.stream.Collectors;

public class CarService {

    public CarDAO cd;

    public CarService(CarDAO cd){
        this.cd=cd;
    }

    public List<Car> getCarsWhichKindStartWith(String s){
        return cd.readAll().stream().filter(c->c.kind.startsWith(s)).collect(Collectors.toList());
    }
}
