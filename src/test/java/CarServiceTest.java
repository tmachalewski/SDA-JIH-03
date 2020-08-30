import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.sda.Car;
import pl.sda.CarDAO;
import pl.sda.CarService;
import pl.sda.ConnectionConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

    @Mock
    CarDAO cad;


    @Test
    public void getCarsWhichKindStartWithShouldReturnProperlyFilteredCarsMocked(){

        List<Car> mockedCars = Arrays.asList(
                new Car(1, "KindA"),
                new Car(1, "KindA"),
                new Car(1, "KindA"),
                new Car(1, "ZindA"),
                new Car(1, "ZindA"),
                new Car(1, "XindA"));


        when(cad.readAll()).thenReturn(mockedCars);

        CarService cs = new CarService(cad);

        assertEquals(3, cs.getCarsWhichKindStartWith("Ki").size());

    }

    @Test
    public void getCarsWhichKindStartWithShouldReturnProperlyFilteredCars(){
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(ConnectionConfiguration.DB_URL);
        ds.setUser(ConnectionConfiguration.USER);
        ds.setPassword(ConnectionConfiguration.PASS);

        CarDAO cd = new CarDAO(ds);
        CarService cs = new CarService(cd);

        assertEquals(4, cs.getCarsWhichKindStartWith("Ki").size());

    }



}
