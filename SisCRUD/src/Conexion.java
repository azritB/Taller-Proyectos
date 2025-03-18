
import java.sql.Connection;
import java.sql.*;


public class Conexion {
    
    public static Connection getConnection(){  
        String conexionURL = "jdbc:sqlserver://localhost:1433;" //puerto usual para sql 1433
                + "database=DBSCRUD;"
                + "user=Tirza;"  // Reemplaza por el nombre de usuario que creaste
                + "password=123;"  // Reemplaza por la contraseña que asignaste
                + "encrypt=true;"
                + "trustServerCertificate=true;"  // Opcional en desarrollo
                + "loginTimeout=30;";
        
        try { 
            Connection con = DriverManager.getConnection(conexionURL);
            return con;
        }
        catch(SQLException ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
}
