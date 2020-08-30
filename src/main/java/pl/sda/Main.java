package pl.sda;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(ConnectionConfiguration.DB_URL);
        ds.setUser(ConnectionConfiguration.USER);
        ds.setPassword(ConnectionConfiguration.PASS);

        PersonDAO pd = new PersonDAO(ds);
        pd.createTable();

        CarDAO cd = new CarDAO(ds);
        cd.createTable();

        AssocCarPersonDAO acpd = new AssocCarPersonDAO(ds);
        acpd.createTable();

        Map<Integer, Car> cars = CarDAO.repository;
        Map<Integer, Person> people = PersonDAO.repository;
        acpd.pairCarsAndPersons();
    }
}
