/*
 * Name: Xuan Li,Xinyu Ge
 * CWID: 10409939,10405323
 */
/**  
 * @Title: ConnectionFactory.java 
 * @Package stevens.cs562.SQLKnokout.util
 * @Description: The ConnectionFactory class can handle  database configure information
 * @author Xinyu Ge
 * @date 2016-4-15 
 * @version V1.0  
 */ 
package stevens.cs562.SQLKnokout.util;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
/**
 * 
* @ClassName: ConnectionFactory 
* @Description: ConnectionFactory class can read database configure form db.properties file
* @author Xinyu Ge
* @date 2016-4-15 ÏÂÎç01:44:53 
*
 */
public class ConnectionFactory{
	// some Member Variable of   ConnectionFactory
	private static Properties prop;
	private static final String CONFIGNAME = "../../../../db.properties";
	//connections pool
	private static List<Connection> conns;
	private Connection conn;
	// the target table name
	private String tableName;
	// the table structure of table we are target at
	private HashMap<String, String> tableStructure = new HashMap<String, String>();
 
	// the setter and getter function
	 public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public static Properties getProp() {
		return prop;
	}
	public static void setProp(Properties prop) {
		ConnectionFactory.prop = prop;
	}
	public static List<Connection> getConns() {
		return conns;
	}
	public static void setConns(List<Connection> conns) {
		ConnectionFactory.conns = conns;
	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	public static String getConfigname() {
		return CONFIGNAME;
	}
	public void setTableStructure(HashMap<String, String> tableStructure) {
		this.tableStructure = tableStructure;
	}
	public ConnectionFactory()  throws Exception{
		/** 
		 * <p>Title: </p> 
		 * <p>Description: </p> 
		 * @author Xinyu Ge
		 * @date 2016-4-15 02:20:04 
		 * 
		 */
		// TODO Auto-generated constructor stub
		 conns = new ArrayList<Connection>();
		 prop = new Properties();
		 System.out.println(CONFIGNAME);
		 prop.load(this.getClass().getResourceAsStream(CONFIGNAME));
		 try
			{
		 Class.forName(prop.getProperty("DRIVER_CLASS"));
		 System.out.println("Success loading Driver!");
			}
			catch(Exception e)
			{
				System.out.println("Fail loading Driver!");
				e.printStackTrace();
			}

			try
			{
		 for (int i = 0; i < 1; i++) {
		 conn = DriverManager.getConnection(
		 prop.getProperty("CONNECTION_URL"), 
		 prop.getProperty("CONNECTION_USERNAME"),
		 prop.getProperty("CONNECTION_PASSWORD"));
		 conns.add(conn);
		 tableName = "sales";
		 tableStructure = setTableStructure("sales");
		 }
			}

			catch(SQLException e)
			{
				System.out.println("Connection URL or username or password errors!");
				e.printStackTrace();
			}
			
			
			
		 
	}
	 public Connection getConnection(){
		  return conns.remove(0);
		}
	 public void close(Connection conn){
		 if(conn!=null){
			 conns.add(conn);
		 }
	 }
	 /**
	  * @Description read data struture from database base on information_schema
	  * @param tableName
	  * @return
	  * @author  Xinyu Ge
	  * @date 2016-4-15
	  */
	 public HashMap<String, String> setTableStructure(String tableName)
		{		
		 tableStructure = new HashMap<String, String>();
			try
			{
				Connection connection = getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select column_name,data_type" +
						" from information_schema.columns"
						+ " where table_name='"+ tableName+"'");
				
				while(rs.next())
				{
					String dataType = null;
					String dataName = rs.getString("column_name");
					if(rs.getString("data_type").equals("character varying") ||
							rs.getString("data_type").equals("character"))
					{
						dataType = "String";
					}
					else
						dataType = "int";
					tableStructure.put(dataName, dataType);
				}
				
				close(connection);
			}catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("getTableStructure fail");
			}
			return tableStructure;
		}
	 /**
	  * 
	  * @return TableStructure
	  * @author Xinyu Ge
	  * @date 2016-4-15
	  */
	public HashMap<String, String> getTableStructure() {
		return tableStructure;
	}

	 

}
