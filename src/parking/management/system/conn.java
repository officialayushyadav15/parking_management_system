
package parking.management.system;
import java.sql.*;

public class conn {
    Connection c;
    Statement s;
    conn(){
        try{
            c=DriverManager.getConnection("jdbc:mysql://localhost:3306/pms","root","Ayush@2003");
            s=c.createStatement();
        }catch(Exception e){
                 e.printStackTrace();
        }
        
    }
}
