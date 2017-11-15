package dcq.stevens.cs548.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import dcq.stevens.cs562.datastructure.DCQConnDB;
import dcq.stevens.cs562.datastructure.DCQFaiStruct;
import dcq.stevens.cs562.datastructure.DCQGraph;

public class DCQprintFunctions {
	
	//init fai
	DCQFaiStruct fai = new DCQFaiStruct();
	//store temp string for construct input java code
	private List<String> tempJava = new ArrayList<String>();	
	//path of target file
	private static final String TARGETFILE = null;
	//path of input file
	private static final String INPUTFILE = null;
	//Graph
	private DCQGraph G = new DCQGraph();
	
	//data type transfer from sql to java
	public String transferType(String str){
		switch(str){
		case "sum":
	 		return "		long ";
	 	case "integer":
	 		return "		int ";
	 	case "character varying":
	 		return "		String ";
	 	case "character":
	 		return "		char ";
	 	default:
	 		return "		int ";
		}
	}

	//function to print strings
	public void printString(String content){
		try{
			 FileWriter fw = new FileWriter(TARGETFILE, true);
			 BufferedWriter bw = new BufferedWriter(fw);
			 bw.write(content+"\r\n");
			 bw.close();
		 } catch(Exception e) {
			 System.out.print("Fail to write");
		 }
	}
	
	//get data type from database
	public List<String> getType(List<String> attribute){
		List<String> temp = new ArrayList<String>();
		try{
			DCQConnDB conn = new DCQConnDB();
			Connection ct = conn.getConn();
			PreparedStatement ps = ct.prepareStatement("select * "
					+ "from Information_schema.columns where table_name = 'sales'");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				for(String attr : attribute){
					if(rs.getString("column_name").equals(attribute)){
						temp.add(transferType(rs.getString("data_type") + attr + ";"));
						tempJava.add(attr + (transferType(rs.getString("data_type")).equals("\t\tString ") ? " = null" : " = 0") + ";");
					}
				}
			}
			conn.closeConn(ct, ps, rs);
		}catch(Exception e){
			e.printStackTrace();
		}
		return temp;
	}
	
	//input grouping attributes and aggregation functions into java code
	public void printAttributes() {
		//grouping attribute
		List<String> list = getType(fai.getOperator_V());
		for (String str : list)
			printString(str);
		//aggregate function
		for (String agg : fai.getOperator_F()) {
			String[] arr = agg.split("_");
			printString(transferType(arr[0]) + agg + ";");
			tempJava.add(agg + " = 0;");
		}
	}
	
	//input constructor part of fai struct into java code
	public void printConstructor(){
		printString("\r\n		FaiStruct() {");
		for (String str : tempJava)
			printString("			" + str);
		printString("		}\r\n");
	}
	
	public void printSegment(int begin, int end){
		File file = new File("code segment for generator.txt");
		try {
			FileReader fr = new FileReader(file);
			BufferedReader bf = new BufferedReader(fr);
			String line;
			int count = 1;	//line number
			while((line = bf.readLine()) != null){
				if (begin <= count && count <= end) 
					printString(line);
				count++;
			}
			bf.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//read input sql and initialize fai structure
	public void readInput(){
		File file = new File(INPUTFILE);
		try{
			//read file line by line and store it into linelist
			List<String> lineList = new ArrayList<String>();
			FileReader fr = new FileReader(file);
			BufferedReader bf = new BufferedReader(fr);
			// read line function
			String line;
			while((line = bf.readLine()) != null){
				lineList.add(line);
			}
			
			//initialize fai structure
			List<String> attributeSet = null;
			
			//initialize projection attr
			String[] arr = lineList.get(1).split(", ");
			attributeSet = Arrays.asList(arr);
			fai.setOperator_S(attributeSet);
			System.out.println(fai.getOperator_S());
			
			
			String str = lineList.get(3);
			fai.setOperator_N(Integer.parseInt(str));
			G.setGraph(Integer.parseInt(str)+1);
			System.out.println(fai.getOperator_N());
			
			//initialize grouping variable
			arr = lineList.get(5).split(", ");
			attributeSet = Arrays.asList(arr);
			fai.setOperator_V(attributeSet);
			System.out.println(fai.getOperator_V());
			
			//initialize aggregate functions
			arr = lineList.get(7).split(", ");
			attributeSet = Arrays.asList(arr);
			fai.setOperator_F(attributeSet);
			System.out.println(fai.getOperator_F());
			
			//initialize theta, select condition
			arr = lineList.get(9).split(", ");
			attributeSet = Arrays.asList(arr);
			fai.setOperator_Theta(attributeSet);
			System.out.println(fai.getOperator_Theta());
			
			//initialize G, having condition
			arr = lineList.get(11).split(", ");
			attributeSet = Arrays.asList(arr);
			fai.setOperator_G(attributeSet);
			System.out.println(fai.getOperator_G());
			
			//TODO ???
			for (int i = 13; i < lineList.size(); i++) {
				arr = lineList.get(i).split(" ");
				G.InsertEdge(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));	//insert edge
			}
			
			//close
			bf.close();
			fr.close();
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//function to generate code
	public void generatorCode(){
		 try{
			 File file = new File(TARGETFILE);
			 if(!file.exists()) 
				 file.createNewFile(); 
			 //initial code
			 FileWriter fw = new FileWriter(TARGETFILE);
			 BufferedWriter bw = new BufferedWriter(fw);
			 bw.write("");
			 bw.close();
		 } catch(Exception e) {
			 System.out.print("Fail to create");
		 }
	 } 
	
	//function to generate java code
	public void generateAlgorithm(){
		//store fai
		List<String> projAttr = fai.getOperator_S();
		List<String> selectCondition = fai.getOperator_Theta();
		List<String> groupingAttr = fai.getOperator_V();
		List<String> aggreFunction = fai.getOperator_F();
		List<String> havingCondition = fai.getOperator_G();
		int scanNums = fai.getOperator_N() + 1;
		
		//scan fai.getON + 1 times
		printString("\t\t for(int i=0; i<" + String.valueOf(scanNums) + "; i++) {");
		printSegment(79, 82);
		printString("\t\t\t	while(more) {");
		//store attribute type
		String attrType = null;
		printString("\t\t\t\t	if(i==0) {");
		printString("\t\t\t\t\t	" + selectCondition.get(0) + " {");
		
		//get datatype of grouping attributes
		List<String> attriTypeList = new ArrayList<String>();
		String key = "";
		for(int l = 0; l < groupingAttr.size(); l++){
			if(groupingAttr.get(l).equals("cust") || groupingAttr.get(l).equals("prod")){
				attrType = "String";
			}else if(groupingAttr.get(l).equals("state")){
				attrType = "CharacterStream";
			}else{
				attrType = "Int";
			}
			attriTypeList.add(attrType);
			if(l == groupingAttr.size() - 1){
				key += ("rs.get" + attrType + "(\"" + groupingAttr.get(l) + "\")");  
			}else{
				key += ("rs.get" + attrType + "(\"" + groupingAttr.get(l) + "\") + ");  
			}
		}
		
		printString("\t\t\t\t\t\t	key = " + key + ";");
		printString("\t\t\t\t\t\t	if(map.containsKey(key)){");
		

		String aggreResultNew = null; 
		List<String> aggreResultNewList = new ArrayList<String>(); 
		LinkedList<Integer> nodeSet = G.topoSort();
		if (nodeSet != null) {
			for (int i = 0; i < nodeSet.size(); i++) {
				//Generate code for updating aggregate values:
				for(int k = 0; k < aggreFunction.size(); k++){
					// avg_quant_1, split every aggregate function
					String[] arr = aggreFunction.get(k).split("_");
					String aggreResultUpdate = null;
					if(Integer.parseInt(arr[2]) == nodeSet.get(i)){  
						switch (arr[0]){
					 	case "sum": 
					 		aggreResultUpdate = "map.get(key)." + aggreFunction.get(k) + " += rs.getInt(\"" + arr[1] + "\");";
					 		aggreResultNew = "fs." + aggreFunction.get(k) + " += rs.getInt(\"" + arr[1] + "\");";
					 		aggreResultNewList.add("\t" + aggreResultNew);
				 		break;
					 	case "cnt":
					 		aggreResultUpdate = "map.get(key)." + aggreFunction.get(k) + " ++;";
					 		aggreResultNew = "fs." + aggreFunction.get(k) + " ++;"; 
					 		aggreResultNewList.add("\t" + aggreResultNew);
				 		break;
					 	case "max":
					 		aggreResultUpdate = "if (map.get(key)." + aggreFunction.get(k) + " < rs.getInt(\"" + arr[1]+ "\"))\n"
					 				+ "\t\t\t\t\t\t\t	map.get(key)." + aggreFunction.get(k) + " = rs.getInt(\"" + arr[1]+ "\");";
					 		aggreResultNew = "fs." + aggreFunction.get(k) + " = rs.getInt(\"" + arr[1] + "\");"; 
					 		aggreResultNewList.add("\t" + aggreResultNew);
				 		break;
					 	case "min":
					 		aggreResultUpdate = "if (map.get(key)." + aggreFunction.get(k) + " > rs.getInt(\"" + arr[1]+ "\"))\n"
					 				+ "\t\t\t\t\t\t\t	map.get(key)." + aggreFunction.get(k) + " = rs.getInt(\"" + arr[1]+ "\");";
					 		aggreResultNew = "fs." + aggreFunction.get(k) + " = rs.getInt(\"" + arr[1] + "\");";
					 		aggreResultNewList.add("\t" + aggreResultNew);
				 		break;
					 	case "avg":  
					 		aggreResultUpdate = "map.get(key)." + aggreFunction.get(k) + " = (int) (map.get(key).sum_" + arr[1] + "_" + arr[2] + "/map.get(key).cnt_" + arr[1] + "_" + arr[2] + ");"; 
					 		aggreResultNew = "fs." + aggreFunction.get(k) + " = (int) (fs.sum_" + arr[1] + "_" + arr[2] + "/fs.cnt_" + arr[1] + "_" + arr[2] + ");";
					 		aggreResultNewList.add("\t" + aggreResultNew);
				 		break;
						}	
					}
					if (aggreResultUpdate != null)
						printString("\t\t\t\t\t\t\t\t	" + aggreResultUpdate); 
				}
			}	
		}
		
		printString("\t\t\t\t\t\t	} else {");
		printString("\t\t\t\t\t\t\t	FaiStruct fs = new FaiStruct();");
		
		//Generate code for assigning attribute values
		for (int l = 0; l < groupingAttr.size(); l++) {
		//Parse to "fs.cust = rs.getString("cust");"
		printString("\t\t\t\t\t\t\t			fs." + groupingAttr.get(l) + " = rs.get"
				+ attriTypeList.get(l) + "(\"" + groupingAttr.get(l) + "\");");
	}
	
		//Generate code for assigning aggregate values
		for(int m = 0; m < aggreResultNewList.size(); m++){
			//Parse to "fs.sum_quant_1 += rs.getInt("quant");"
			printString("\t\t\t\t\t\t\t			" + aggreResultNewList.get(m));
		}
		
		printString("\t\t\t\t\t\t\t	map.put(key, fs);\n"
				+ "\t\t\t\t\t\t	}\n"
				+ "\t\t\t\t\t	}\n"); 
		printString("\t\t\t\t	}else {");
		printSegment(88, 92);
		
		nodeSet = G.topoSort();
		int loopNum = 1;
		while(nodeSet != null) {
			//Generate code for updating aggregate values
			printString("\t\t\t\t\t\t	case " + String.valueOf(loopNum++) + ":");
			for (int i = 0; i < nodeSet.size(); i++) {
				printString("\t\t\t\t\t\t\t	" + selectCondition.get(nodeSet.get(i)) + " {");
				for(int k = 0; k < aggreFunction.size(); k++){
					String[] arr = aggreFunction.get(k).split("_");
					String aggreResultUpdate = null; 
					if(Integer.parseInt(arr[2]) == nodeSet.get(i)){  
						switch (arr[0]){
					 	case "sum":
					 		//Parse to "map.get(key).sum_quant_1 += rs.getInt("quant")";
					 		aggreResultUpdate = "map.get(key)." + aggreFunction.get(k) + " += rs.getInt(\"" + arr[1] + "\");"; 
					 		break;
					 	case "cnt":
					 		//Parse to "map.get(key).cnt_quant_1++";
					 		aggreResultUpdate = "map.get(key)." + aggreFunction.get(k) + " ++;"; 
					 		break;
					 	case "max":
					 		//Parse to "if (map.get(key).max_quant_1 < rs.getInt("quant"))
					 		aggreResultUpdate = "if (map.get(key)." + aggreFunction.get(k) + " < rs.getInt(\"" + arr[1]+ "\"))\n"
					 				+ "\t\t\t\t\t\t\t\t\t	map.get(key)." + aggreFunction.get(k) + " = rs.getInt(\"" + arr[1]+ "\");";
					 		break;
					 	case "min":
					 		aggreResultUpdate = "if (map.get(key)." + aggreFunction.get(k) + " > rs.getInt(\"" + arr[1]+ "\"))\n"
					 				+ "\t\t\t\t\t\t\t\t\t	map.get(key)." + aggreFunction.get(k) + " = rs.getInt(\"" + arr[1]+ "\");";
					 		break;
					 	case "avg":
					 		//Parse to "map.get(key).avg_quant_1 = (int) (map.get(key).sum_quant_1/map.get(key).cnt_quant_1)";
					 		aggreResultUpdate = "map.get(key)." + aggreFunction.get(k) + " = (int) (map.get(key).sum_" + arr[1] + "_" + arr[2] + "/map.get(key).cnt_" + arr[1] + "_" + arr[2] + ");"; 
					 		break;
						}
						if (aggreResultUpdate != null)
							printString("\t\t\t\t\t\t\t\t	" + aggreResultUpdate);
					}
				}
				printString("\t\t\t\t\t\t\t	}");
			}
			printString("\t\t\t\t\t\t\t	break;");
			nodeSet = G.topoSort();
		}
		printSegment(98, 100);
		
		printString("\t\t\t\t\t	}");
		printString("\t\t\t\t	}");
		printString("\t\t\t\t	more = rs.next();");
		printString("\t\t\t	}");
		printString("\t\t	}");
		
		//Generate code for printing out projected attributes and aggregate functions
		for(int i = 0; i < projAttr.size(); i++){
			if(i == projAttr.size() - 1){
				printString("\t\t	System.out.printf(\"%-7s  \\n\", \"" + projAttr.get(i) + "\");");
			}else{
				printString("\t\t	System.out.printf(\"%-7s  \", \"" + projAttr.get(i) + "\");"); 
			}
		}
		
		//Generate code for printing out showing results statements
		printString("\t\t	Iterator<String> iter = map.keySet().iterator();");
		printString("\t\t	while(iter.hasNext()){");
		printString("\t\t\t		FaiStruct fs = map.get(iter.next());");
		
		printString("\t\t\t		" + havingCondition.get(0) + " {");
		
		//For output, add "fs." before every attribute and aggregate functions
		List<String> parseProjAttributes = new ArrayList<String>();
		String[] columArray = {"cust","prod","day","month","year","state","quant"};
		List<String> columnList = Arrays.asList(columArray);
		for(int i = 0; i < projAttr.size(); i++){
			String temp = projAttr.get(i);
			StringBuilder sb = new StringBuilder(temp);
			//Add "fs." to every attributes and aggregate functions in Computation Expression. E.g. "avg_quant_1/avg_quant_2" to "fs.avg_quant_1/fs.avg_quant_2"
			if(projAttr.get(i).contains("+") || projAttr.get(i).contains("-") || 
					projAttr.get(i).contains("*") || projAttr.get(i).contains("/")){
					for(int j = 0; j < aggreFunction.size(); j++){
					if(projAttr.get(i).contains(aggreFunction.get(j))){
					int position = temp.indexOf(aggreFunction.get(j));
					sb.insert(position, "fs.");
					temp = sb.toString();
					while(position < projAttr.get(i).length()){
						position = temp.indexOf(aggreFunction.get(j), position + aggreFunction.get(j).length());
						if(position != -1){
							sb.insert(position, "fs.");
							temp = sb.toString();
						}else{
							break;
						}
					}
				}
				temp = sb.toString();
			}
			}else{
				//Add "fs." before every attribute and aggregate functions
				sb.insert(0, "fs.");
			}
			parseProjAttributes.add(sb.toString());
			
		}
		
		//Generate codes for outputting results
		for(int i = 0; i < parseProjAttributes.size(); i++){
			if(i == parseProjAttributes.size() - 1){
				if(columnList.contains(projAttr.get(i))){
					printString("\t\t\t\t	System.out.printf(\"%-7s  \\n\", " + parseProjAttributes.get(i) + ");");
				}else{
					printString("\t\t\t\t	System.out.printf(\"%11s  \\n\", " + parseProjAttributes.get(i) + ");");
				}
			}else{
				if(columnList.contains(projAttr.get(i))){
					printString("\t\t\t\t	System.out.printf(\"%-7s  \", " + parseProjAttributes.get(i) + ");");
				}else{
					printString("\t\t\t\t	System.out.printf(\"%11s  \", " + parseProjAttributes.get(i) + ");");
				}
			}
		}
		printString("\t\t\t	}");
		printString("\t\t	}");
	}
}