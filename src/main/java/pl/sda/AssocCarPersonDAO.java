package pl.sda;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssocCarPersonDAO {
    DataSource ds;
    public AssocCarPersonDAO(DataSource ds){
        this.ds=ds;
    }
    public void createTable(){
        try(Connection con = ds.getConnection()){
            Statement st = con.createStatement();
            st.execute("create table if not exists car_person (" +
                    "car_id int," +
                    "person_id int," +
                    "constraint usedBy foreign key (person_id) references Person(id), " +
                    "constraint usedCar foreign key (car_id) references Car(id)" +
                    ")");

        }catch(SQLException e){
            e.printStackTrace();
        }



    }

    public List<Integer> getTripsByCarId(int id) {
        List<Integer> ret = new ArrayList<>();
        try(Connection con = ds.getConnection()){
            PreparedStatement st = con.prepareStatement("Select * from car_person where car_id=?");
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                ret.add(rs.getInt("person_id"));
            }
            return ret;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return ret;
    }

    public void pairCarsAndPersons(){
        new CarDAO(ds).fillRepository();
        new PersonDAO(ds).fillRepository();

        try(Connection con = ds.getConnection()){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from car_person");
            while(rs.next())
            {
                int car_id = rs.getInt("car_id");
                int person_id = rs.getInt("person_id");

                Car c = CarDAO.repository.get(car_id);
                Person p = PersonDAO.repository.get(person_id);

                if(p==null||c==null){
                    continue;
                }

                if(!c.usedBy.contains(p)){
                    c.usedBy.add(p);
                };

                if(!p.usedCars.contains(c)){
                    p.usedCars.add(c);
                };
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
