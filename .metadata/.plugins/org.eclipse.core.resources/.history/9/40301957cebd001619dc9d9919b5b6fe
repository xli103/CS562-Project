/*
 * Name: Xuan Li,Xinyu Ge
 * CWID: 10409939,10405323
 */
/**  
 * @Title: Test.java 
 * @Package stevens.cs562.SQLKnokout.Test 
 * @Description:The entry for the whole program,also include the function for print
* 				the meau;
 * @author Xuan Li
 * @date 2016-4-13 
 * @version V2.5 
 */ 
package stevens.cs562.SQLKnokout.Test;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import stevens.cs562.SQLKnokout.entity.EMFVariables;
import stevens.cs562.SQLKnokout.util.ConnectionFactory;
import stevens.cs562.SQLKnokout.util.FileFactory;
import stevens.cs562.SQLKnokout.util.ReadInput;
/**
 * 
* @ClassName: Test 
* @Description: The entry for the whole program,also include the function for print
* 				the meau;
* @author Xuan li
* @date 2016-4-13 ÏÂÎç08:20:39 
*
 */
public class Test {
	public static void main(String[] args) throws Exception {
	
		ConnectionFactory connectionFactory;
		try{
			connectionFactory = new ConnectionFactory();
	
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("ConnectionFactory create fail!!!");
			return;
		}
		Connection c = connectionFactory.getConnection();
		 HashMap<String, String> tableStructure = connectionFactory.getTableStructure();
		FileFactory f = new FileFactory();
		ReadInput r = new ReadInput();
		// print the meau
		String choose = "Y";
		while(choose.equals("Y")){
		System.out.println("_______________________________________________");
		System.out.println(" 1> Read variables from file");
		System.out.println(" 1> Read variables as input");
		System.out.println(" 3> exit;");
		System.out.println("_______________________________________________");
		
		System.out.print("Please input your choose____");
		Scanner scan = new Scanner(System.in);
		choose = scan.nextLine();
	
		EMFVariables variables = null;
		boolean flag = false;
		boolean first = true;
		while(flag||first){
			first = false;
		if(choose.equals("1")){
			// if user choose read variables from file, we need load variables from file, so we can call the file factory to load the file
			variables = f.load();
		}else if(choose.equals("2")){
			// if user choose read variables from input, we need read variables from user
			
			variables = r.read(scan);
		}else if(choose.equals("3")){
			//othersise the user decide exit the program
			System.out.println("System exit!");
			return;
		}else{
			// we also have to consider the user input data is illegal
			flag = true;
			
			System.out.println("Input is illegal!");
			System.out.println("_______________________________________________");
			System.out.println(" 1> Read variables from file");
			System.out.println(" 1> Read variables as input");
			System.out.println(" 3> exit;");
			System.out.println("_______________________________________________");
			System.out.print("Please input your choose____");
		}
		}

		long startTime=System.currentTimeMillis();  
		String fileName = f.writeFile(connectionFactory,variables);
		System.out.println(fileName+" create success!!!!!!");
		long endTime=System.currentTimeMillis();
		long Time=endTime-startTime;
		System.out.println("the program run£º "+Time+"ms");
		System.out.print("Would you like to run this program again?(Y/N)");
		// if the user want to create another file, he can back the the start point
		choose = scan.nextLine();
		}
		System.out.println("System exit!");
	}

}

