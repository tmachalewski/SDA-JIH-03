package pl.sda;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarDAO {

    public static Map<Integer, Car> repository = new HashMap<>();

    DataSource ds;
    public CarDAO(DataSource ds){
        this.ds=ds;
    }
    public void createTable(){
        try(Connection con = ds.getConnection()){
            Statement st = con.createStatement();
            st.execute("create table if not exists Car (" +
                    "id int primary key auto_increment," +
                    "kind varchar(45))");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void findById(int id){
        try(Connection con = ds.getConnection()){
            PreparedStatement st = con.prepareStatement("Select * from Car where id=?");
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            int count =0 ;
            while(rs.next())
            {
                if(count>=1){
                    throw new UnsupportedOperationException("there should be only one car with this id");
                }

                Car c = new Car();
                if(repository.containsKey(id))
                {
                    c = repository.get(id);
                }

                c.id=rs.getInt(1);
                c.kind=rs.getString(2);

                repository.put(id, c);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void fillRepository(){
        try(Connection con = ds.getConnection()){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from Car");
            int count =0 ;
            while(rs.next())
            {
                Car c = new Car();
                c.id=rs.getInt(1);
                if(repository.containsKey(c.id))
                {
                    continue;
                }

                c.kind=rs.getString(2);

                repository.put(c.id, c);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public List<Car> readAll(){
        List<Car> cars = new ArrayList<>();
        try(Connection con = ds.getConnection()){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from Car");

            while(rs.next()){
                Car c = new Car();
                c.id=rs.getInt(1);
                c.kind=rs.getString(2);
                cars.add(c);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return cars;

    }



}
