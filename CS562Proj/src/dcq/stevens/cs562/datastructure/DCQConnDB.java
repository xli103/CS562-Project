package dcq.stevens.cs562.datastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DCQConnDB {
	//get connections from database
		private Connection ct = null;
		private ResultSet rs = null;
		private PreparedStatement ps = null;
		
		public Connection getConn(){
			try{
				//upload drivers
				Class.forName("org.postgresql.Driver").newInstance();
			
				//set connections
				String url="jdbc:postgresql://127.0.0.1:5432/CS562";
		        String user="postgres";
		        String pwd="lxh920929";
		        
		        //get connections
				ct=DriverManager.getConnection(url,user,pwd);
				System.out.println("Database connected!");
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return ct;
		}
		
		//close connection from database
		public void closeConn(Connection ct, PreparedStatement ps, ResultSet rs){
			try{
				if(rs != null){
					rs.close();
				}
				if(ps != null){
					ps.close();
				}
				if(ct != null){
					ct.close();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
}
