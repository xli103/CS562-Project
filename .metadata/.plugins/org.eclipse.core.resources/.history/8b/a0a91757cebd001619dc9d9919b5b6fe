/*
 * Name: Xuan Li,Xinyu Ge
 * CWID: 10409939,10405323
 */
/**  
 * @Title: FileFactory.java 
 * @Package stevens.cs562.SQLKnokout.util 
 * @Description: The fileFactory class is focus on deal with any operation with file, such as read file from disk or writ the final result to file
 * @author Xuan Li
 * @date 2016-4-16 
 * @version V1.3 
 */ 
package stevens.cs562.SQLKnokout.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


import stevens.cs562.SQLKnokout.entity.EMFVariables;
import stevens.cs562.SQLKnokout.entity.OutputFile;

public class FileFactory {
	private String fileName;

	/**
	 * 
	 * @return reader 
	 * @author Xuan Li
	 * @date 2016-4-16
	 * <p>Title: readFile</p> 
	 * <p>Description: The readFile method is help the system to read the 6 variables from file, in this process, we need user input the path of file </p> 
	 */
	public BufferedReader readFile() {
		String path = System.getProperty("user.dir");
		int flag = 1;
		System.out.print("Enter the direction of your file please:[the default path is the src\\](example: example) ");
		System.out.println("");
		Scanner scan = new Scanner(System.in);
		//first we need read the path from user
		fileName = scan.nextLine();
		//fileName = "example";//just for test!!!!!
		String path1 = path+"\\src\\"+fileName;
		// this path is for mac 
		///path = path+"/src/"+fileName;   
		File file = new File(path1);
		BufferedReader bufferedReader = null ;
		try {

			String encoding = "GBK";
			if (file.isFile() && file.exists()) { // the file is exit
				// Reader reader = null;
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				bufferedReader = new BufferedReader(read);
				flag = 0;
			} else {
				System.out.println("system cannot find the file");
				while(flag==1){
					System.out.print("Enter the direction of your file please:[the default path is the src\\](example: example) ");
					System.out.println("");
					fileName = scan.nextLine();
					path = path+"\\src\\"+fileName;
					file = new File(path);
					if (file.isFile() && file.exists()) { // the file is exit
						// Reader reader = null;
						InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
						bufferedReader = new BufferedReader(read);
						flag = 0;
					}
				}
			}
			
		} catch (

		Exception e)

		{
			e.printStackTrace();
		}
		return bufferedReader;
	}
	/**
	 * 
	 * @param connectionFactory
	 * @param varibles
	 * @return String
	 * @throws IOException
	 * @author Xuan Li
	 * @date 2016-4-16
	 * <p>Title: writeFile</p> 
	 * <p>Description: The writeFile method is help the system to write the final result to the disk, the return value is the fileName of output file   </p> 
	 */
	public String writeFile(ConnectionFactory connectionFactory, EMFVariables varibles) throws IOException{
		FileWriter fw = null;
		// initial the output file name and package name
		String className = "Output.java";
		String packageName = "stevens.cs562.SQLKnokout.output";
		OutputFile outputFile = new OutputFile();
		// set attributes of output file 
		outputFile.setFileName(className);
		outputFile.setPackageName(packageName);
		outputFile.setTableName(connectionFactory.getTableStructure());
		outputFile.setVaribles(varibles);
		// initial the importList
		ArrayList<String> importList = new ArrayList<String>();
		importList.add("java.sql.Statement");
		importList.add("java.sql.DriverManager");
		importList.add("java.sql.Connection");
		importList.add("java.util.List");
		importList.add("java.util.ArrayList");
		importList.add("java.util.HashMap");
		importList.add("java.util.Iterator");
		importList.add("java.sql.ResultSet");
		// set importList attribute of output file 
		outputFile.setImportList(importList);
		try{
            
            fw = new FileWriter("src//stevens//cs562//SQLKnokout//output//"+className);
            // write it to the disk
            fw.write(outputFile.write(connectionFactory));

            
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(fw != null){
                fw.close();
            }
        }
		return className;
    }
	
		
		



	/**
	 * 
	 * @return EMFVariables
	 * @throws IOException
	 * @author Xuan Li
	 * @date 2016-4-16
	 * <p>Title: load</p> 
	 * <p>Description: The load method is focus on read the 6 variables from file   </p> 
	 */
	public EMFVariables load() throws IOException {
		boolean flag = false;
		EMFVariables varibles = new EMFVariables();
		BufferedReader bf = readFile();
		String selectAttribute = bf.readLine();
		//#SELECT ATTRIBUTE(S)
		//System.out.println(selectAttribute);
		String attribute = bf.readLine();
		String[] attributes = attribute.split(",");
		ArrayList<String> varibles_s = new ArrayList<String>();
		for (int i = 0; i < attributes.length; i++) {
			String att = attributes[i].trim();
			varibles_s.add(att);
		}
		//System.out.println(varibles_s);
		
		
		//#NUMBER OF GROUPING VARIABLES(n)
		String numberCluse = bf.readLine();
		//System.out.println(numberCluse);
		String number = bf.readLine();
		//System.out.println(number);
		
		
		//#GROUPING ATTRIBUTES(V)
		String group = bf.readLine();
		//System.out.println(group);
		String groupAttr = bf.readLine();
		String[] groupAttrs = groupAttr.split(",");
		ArrayList<String> varibles_v = new ArrayList<String>();
		for (int i = 0; i < groupAttrs.length; i++) {
			String att = groupAttrs[i].trim();
			varibles_v.add(att);
		}
		//System.out.println(varibles_v);
		//#F-VECT([F])
		String f = bf.readLine();
		//System.out.println(f);
		String fv = bf.readLine();
		String[] fvs = fv.split(",");
		ArrayList<String> varibles_f = new ArrayList<String>();
		for (int i = 0; i < fvs.length; i++) {
			String vaf = fvs[i].trim();
			varibles_f.add(vaf);
		}
		//System.out.println(varibles_f);
		//#SELECT CONDITION-VECT([sigma])  number
		String sigmaClause = bf.readLine();
		//System.out.println(sigmaClause);
		
		ArrayList<ArrayList> varibles_sigmas = new ArrayList<ArrayList>();
		
		for (int i = 0; i <  Integer.parseInt(number); i++) {
			String sigma = bf.readLine();
			String[] sigmas = sigma.split("and");
			ArrayList<String> varibles_sigma = new ArrayList<String>();
			for (int j = 0; j < sigmas.length; j++) {
				String sigmaValue = sigmas[j].trim();
				varibles_sigma.add(sigmaValue);
			}
			varibles_sigmas.add(varibles_sigma);
		}
		
		//System.out.println(varibles_sigmas);
		//#HAVING CONDITION(G)
		String HavingCluse = bf.readLine();
		//System.out.println(HavingCluse);
		String HavingCondition = bf.readLine();
		ArrayList<String> varibles_g = new ArrayList<String>();
		if(!HavingCondition.startsWith("#")){
			String[] havingConditions = HavingCondition.split("and");
			
			for (int i = 0; i < havingConditions.length; i++) {
				String att = havingConditions[i].trim();
				varibles_g.add(att);
			}
			//System.out.println(varibles_g);
		}else{
			flag = true;
		}
	
		//#WHERE CONDITION
		String where ;
		if(!flag){
			String WHEREClause = bf.readLine();
			//System.out.println(WHEREClause);
			 where = bf.readLine();
			//System.out.println(where);
		}else{
			String WHEREClause = HavingCondition;
			//System.out.println(WHEREClause);
			 where = bf.readLine();
			//System.out.println(where);
		}
		varibles.setN(Integer.parseInt(number));
		varibles.setS(varibles_s);
		varibles.setV(varibles_v);
		varibles.setF(varibles_f);
		varibles.setSigma(varibles_sigmas);
		varibles.setG(varibles_g);
		varibles.setWhere(where);
		
		//System.out.println(varibles);
		
		return varibles;
		
	}
	
	

}

