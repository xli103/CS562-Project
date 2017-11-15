/*
 * Name: Xuan Li,Xinyu Ge
 * CWID: 10409939,10405323
 */
/**  
 * @Title: ReadInput.java 
 * @Package stevens.cs562.SQLKnokout.util 
 * @Description: The tools program to help user read variables from user input.
 * @author Xuan Li
 * @date 2016-4-18
 * @version V1.2  
 */ 
package stevens.cs562.SQLKnokout.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


import stevens.cs562.SQLKnokout.entity.EMFVariables;
/**
 * 
* @ClassName: ReadInput 
* @Description: The tools program to help user read variables from user input.
* @author Xuan li
* @date 2016-4-18 ÏÂÎç04:25:59 
*
 */
public class ReadInput {
/**
 * 
 * @param scan
 * @return EMFVariables
 * @throws IOException
 * @author Xuan Li
 * @date 2016-4-18
 * @Description: the read function get the parameter of scan, so it can use scan to read data from user input
 * 				The most important part for this method is it can return EMFVariables just like the read file dose.
 */
	public EMFVariables read(Scanner scan) throws IOException {
		System.out.println("Welcome to use our input process!");
		boolean flag = true;
		EMFVariables varibles = new EMFVariables();
		String choose = "";
		System.out.println("First please input #SELECT ATTRIBUTE(S):");
		//#SELECT ATTRIBUTE(S)
		flag = true;
		ArrayList<String> SELECT = new ArrayList<String>();
		while(flag){
		
		String s = scan.nextLine().trim();
		SELECT.add(s);
		System.out.println("Do you want to input other SELECT attribute(Y/N)");
		choose = scan.nextLine().trim();
		if(!choose.equalsIgnoreCase("y")){
			flag = false;
		}
		}
		varibles.setS(SELECT);
		System.out.println("#NUMBER OF GROUPING VARIABLES(n):");
		//#NUMBER OF GROUPING VARIABLES(n)
		flag = true;
		String n = scan.nextLine().trim();
		int N = Integer.parseInt(n);
		varibles.setN(N);
		
		System.out.println("#GROUPING ATTRIBUTES(V):");
		//#GROUPING ATTRIBUTES(V)
		flag = true;
		ArrayList<String> GROUPING = new ArrayList<String>();
		while(flag){
		
		String g = scan.nextLine().trim();
		GROUPING.add(g);
		System.out.println("Do you want to input other GROUPING attribute(Y/N)");
		choose = scan.nextLine().trim();
		if(!choose.equalsIgnoreCase("y")){
			flag = false;
		}
		}
		varibles.setV(GROUPING);
		
		System.out.println("#F-VECT([F]):");
		//#F-VECT([F])
		flag = true;
		ArrayList<String> VECT = new ArrayList<String>();
		while(flag){
		
		String v = scan.nextLine().trim();
		VECT.add(v);
		System.out.println("Do you want to input other F-VECT(Y/N)");
		choose = scan.nextLine().trim();
		if(!choose.equalsIgnoreCase("y")){
			flag = false;
		}
		}
		varibles.setF(VECT);
		
		System.out.println("#SELECT CONDITION-VECT([sigma]):");
		//#SELECT CONDITION-VECT([sigma])  number
		flag = true;
		ArrayList<ArrayList> Sigma = new ArrayList<ArrayList>();
		int loop = varibles.getN();
		for (int i = 0; i < loop; i++) {
			flag = true;
			ArrayList sgs = new ArrayList<String>();
		
			while(flag){
			System.out.println("input the "+(i+1)+"th having condition ):");
			String sg = scan.nextLine().trim();
			sgs.add(sg);
			System.out.println("Do you want to input other sigma for the "+(i+1)+"th varibles(Y/N)");
			choose = scan.nextLine().trim();
			if(!choose.equalsIgnoreCase("y")){
				flag = false;
			}
			}
			
			Sigma.add(sgs);
			
		}
		varibles.setSigma(Sigma);
		System.out.println("do you have having condition? please input (y/n)");
		choose = scan.nextLine().trim();
		if(choose.equalsIgnoreCase("y")){
		System.out.println("#HAVING CONDITION(G):");
		//#HAVING CONDITION(G)
		flag = true;
		ArrayList<String> HAVING = new ArrayList<String>();
		while(flag){
		
		String h = scan.nextLine().trim();
		HAVING.add(h);
		System.out.println("Do you want to input other HAVING CONDITION(Y/N)");
		choose = scan.nextLine().trim();
		if(!choose.equalsIgnoreCase("y")){
			flag = false;
		}
		}
		varibles.setG(HAVING);
		}else{
			varibles.setG(new ArrayList<String>());
		}
		System.out.println("do you have where condition? please input (y/n)");
		choose = scan.nextLine().trim();
		if(choose.equalsIgnoreCase("y")){
		System.out.println("#WHERE CONDITION:(if you don't have having condition, please input n)");
		//#WHERE CONDITION
		flag = true;
		String where = scan.nextLine().trim();
		varibles.setWhere(where);
		}else{
			varibles.setG(new ArrayList<String>());
		}
		
		return varibles;
		
		}
	
	



}