package pl.sda;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class PersonDAO {

    public static Map<Integer, Person> repository = new HashMap<>();

    DataSource ds;
    public PersonDAO(DataSource ds){
        this.ds=ds;
    }
    public void createTable(){
        try(Connection con = ds.getConnection()){
            Statement st = con.createStatement();
            st.execute("create table if not exists Person (" +
                    "id int primary key auto_increment," +
                    "name varchar(45))");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void fillRepository(){
        try(Connection con = ds.getConnection()){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from Person");
            while(rs.next())
            {
                Person p = new Person();
                p.id=rs.getInt(1);
                if(repository.containsKey(p.id))
                {
                    continue;
                }

                p.name=rs.getString(2);

                repository.put(p.id, p);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
