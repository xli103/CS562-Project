/*
 * Name: Xuan Li,Xinyu Ge
 * CWID: 10409939,10405323
 */
/**
 * @Title: OutputFile.java
 * @Package stevens.cs562.SQLKnokout.entity
 * @Description: The most important class in whole program, it will return content of the output file
 * @author Xuan Li,Xinyu Ge
 * @date 2016-4-18
 * @version V1.0
 */
package stevens.cs562.SQLKnokout.entity;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import stevens.cs562.SQLKnokout.util.ConnectionFactory;

/**
 * 
* @ClassName: OutputFile 
* @Description: The most important class in whole program, it will return content of the output file
* @author Xuan li, Xinyu Ge
* @date 2016-4-18 ÏÂÎç10:57:28 
*
 */
public class OutputFile {
	// table tabel structure 
	private HashMap<String,String> tableName;
	// output file name
	private String fileName;
	// package name
	private String packageName;
	// 6 Emfvariables 
	private EMFVariables varibles;
	// import String list
	private List<String> importList;
	// the real content of output file
	private StringBuffer returnValue;
	// the output attributes in output file
	private ArrayList<String> mfvaribles;


	public ArrayList<String> getMfvaribles() {
		return mfvaribles;
	}
	public void setMfvaribles(ArrayList<String> mfvaribles) {
		this.mfvaribles = mfvaribles;
	}
	public HashMap<String, String> getTableName() {
		return tableName;
	}
	public void setTableName(HashMap<String, String> tableName) {
		this.tableName = tableName;
	}
	public StringBuffer getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(StringBuffer returnValue) {
		this.returnValue = returnValue;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public List<String> getImportList() {
		return importList;
	}
	public void setImportList(List<String> importList) {
		this.importList = importList;
	}

	public EMFVariables getVaribles() {
		return varibles;
	}
	public void setVaribles(EMFVariables varibles) {
		this.varibles = varibles;
	}
	public OutputFile(String fileName, String packageName,
			List<String> importList) {
		super();
		this.fileName = fileName;
		this.packageName = packageName;
		this.importList = importList;
		returnValue = new StringBuffer();
	}

	/**
	 *
	 * @param
	 * @return
	 * @author Xuan Li
	 * @date 2016-4-19
	 * @Description appent the content of  table class to returnValue 
	 */
	private void printEntity(){
		returnValue.append("\r\n class Table{\r\n");
		for(Map.Entry<String, String> entry:tableName.entrySet())
		{	
			returnValue.append("\tprivate " + entry.getValue() + " " + entry.getKey() + ";\r\n");
			String upperCaseString =  entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
			//set function
			returnValue.append("\tpublic "+ entry.getValue() +" get"+upperCaseString+"(){\r\n" );
			returnValue.append("\treturn "+entry.getKey()+";\r\n");
			returnValue.append("\t}\r\n");
			//get function
			returnValue.append("\tpublic void set"+upperCaseString+"("+ entry.getValue()+" "+entry.getKey() +"){\r\n" );
			returnValue.append("\tthis."+entry.getKey()+" = "+entry.getKey()+";\r\n");
			returnValue.append("\t}\r\n");


		}

		returnValue.append("}\r\n");
	}


	/**
	 * @parameterFromMfVariablse V,F,S
	 * @param
	 * @return
	 * @author Xuan Li
	 * @date 2016-4-20
	 * @version: 1.1
	 * @param connectionFactory 
	 * @Description appent the content of MFStruture class to returnValue 
	 */
	public void printMFStruture(ConnectionFactory connectionFactory){
		returnValue.append("\r\n class MFStruture{\r\n");
		ArrayList<String> attrubutes = new ArrayList<String>();
		
		
		// handle V part
		ArrayList list  = varibles.getV();
		for (int i = 0; i < list.size(); i++) {
			String name = (String) list.get(i);
			String type = "int";
			for(Map.Entry<String, String> entry:tableName.entrySet()){

				if(entry.getKey()==name||entry.getKey().equals(name)){
					type = entry.getValue();
					break;
				}
			}
			attrubutes.add(name);
			returnValue.append("\tprivate " + type + " " + name + ";\r\n");
			String 	upperCaseString =  name.substring(0, 1).toUpperCase() + name.substring(1);
			//set function
			returnValue.append("\tpublic "+ type +" get"+upperCaseString+"(){\r\n" );
			returnValue.append("\treturn "+name+";\r\n");
			returnValue.append("\t}\r\n");
			//get function
			returnValue.append("\tpublic void set"+upperCaseString+"("+ type+" "+name +"){\r\n" );
			returnValue.append("\tthis."+name+" = "+name+";\r\n");
			returnValue.append("\t}\r\n");

		}
		// handle F part
		list  = varibles.getF();
		

		for (int i = 0; i < list.size(); i++) {
			String name = (String) list.get(i);
			String type = "int";
			type = "double";
			name = "mf"+name;
			// if the attribute contains avg, we need split the avg to count and sum, and re-calculate it at last
			if(name.contains("avg_")){
				
				name = name.replace("avg_", "count_");
				attrubutes.add(name);
				returnValue.append("\tprivate " + type + " " + name + ";\r\n");
				String 	upperCaseString =  name.substring(0, 1).toUpperCase() + name.substring(1);
				//set function
				returnValue.append("\tpublic "+ type +" get"+upperCaseString+"(){\r\n" );
				returnValue.append("\treturn "+name+";\r\n");
				returnValue.append("\t}\r\n");
				//get function
				returnValue.append("\tpublic void set"+upperCaseString+"("+ type+" "+name +"){\r\n" );
				returnValue.append("\tthis."+name+" = "+name+";\r\n");
				returnValue.append("\t}\r\n");
				//sum
				name = name.replace("count_", "sum_");
				attrubutes.add(name);
				returnValue.append("\tprivate " + type + " " + name + ";\r\n");
				upperCaseString =  name.substring(0, 1).toUpperCase() + name.substring(1);
				//set function
				returnValue.append("\tpublic "+ type +" get"+upperCaseString+"(){\r\n" );
				returnValue.append("\treturn "+name+";\r\n");
				returnValue.append("\t}\r\n");
				//get function
				returnValue.append("\tpublic void set"+upperCaseString+"("+ type+" "+name +"){\r\n" );
				returnValue.append("\tthis."+name+" = "+name+";\r\n");
				returnValue.append("\t}\r\n");

			}else{

				attrubutes.add(name);
				returnValue.append("\tprivate " + type + " " + name + ";\r\n");
				String 	upperCaseString =  name.substring(0, 1).toUpperCase() + name.substring(1);
				//set function
				returnValue.append("\tpublic "+ type +" get"+upperCaseString+"(){\r\n" );
				returnValue.append("\treturn "+name+";\r\n");
				returnValue.append("\t}\r\n");
				//get function
				returnValue.append("\tpublic void set"+upperCaseString+"("+ type+" "+name +"){\r\n" );
				returnValue.append("\tthis."+name+" = "+name+";\r\n");
				returnValue.append("\t}\r\n");
			}
		}
		// handleS part
		ArrayList<String> S = varibles.getS();
		for (int j = 0; j < S.size(); j++) {
			String name = S.get(j);
			if(!(attrubutes.contains(name))&&!(name.contains("_"))){
				String type = connectionFactory.getTableStructure().get(name);
				attrubutes.add(name);
				returnValue.append("\tprivate " + type + " " + name + ";\r\n");
				String 	upperCaseString =  name.substring(0, 1).toUpperCase() + name.substring(1);
				//set function
				returnValue.append("\tpublic "+ type +" get"+upperCaseString+"(){\r\n" );
				returnValue.append("\treturn "+name+";\r\n");
				returnValue.append("\t}\r\n");
				//get function
				returnValue.append("\tpublic void set"+upperCaseString+"("+ type+" "+name +"){\r\n" );
				returnValue.append("\tthis."+name+" = "+name+";\r\n");
				returnValue.append("\t}\r\n");

			}
		}
		this.mfvaribles = attrubutes;
		
		returnValue.append("}\r\n");
	}




	/**
	 *
	 * 
	 * @return
	 * @author Xuan Li
	 * @date 2016-4-18
	 * @Description appent the content of  comments part to returnValue 
	 */
	public void printComments(){
		Date d=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String comments= "/**"+
			"\r\n @Title: "+fileName+
			"\r\n @Package "+packageName+
			"\r\n @Description: The output File for final query of cs562 project"+
			"\r\n @author Xuan Li,Xinyu Ge (Group SQLKnockout)"+
			"\r\n @date "+df.format(d)+
			"\r\n @version V1.0  "+
			"\r\n*/ \r\n";
		returnValue.append(comments);
	}
	/**
	 *
	 * @param bf
	 * @return
	 * @author Xuan Li
	 * @date 2016-4-18
	 * @Description appent the content of  package part to returnValue
	 */
	public void printPackage(){
		String packageName= "package "+this.packageName+";\r\n\r\n";
		returnValue.append(packageName);
		//return bf;

	}
	/**
	 *
	 * @param
	 * @return
	 * @author Xuan Li
	 * @date 2016-4-18
	 * @Description appent the content of  import part to returnValue
	 */
	public void printImport(){
		for (int i = 0; i < importList.size(); i++) {
			String importItem = importList.get(i);
			returnValue.append("import "+importItem+";\r\n");

		}
		returnValue.append("\r\n");

	}
	/**
	 *
	 * @param
	 * @return
	 * @author Xuan Li
	 * @param connectionFactory
	 * @date 2016-4-18
	 * @Description appent the content of  databse part to returnValue, this part is a part of printClass
	 */
	public void printDatabaseConnt(ConnectionFactory connectionFactory){
		StringBuffer database = new StringBuffer();
		 Properties prop =  connectionFactory.getProp();
		 String driver = prop.getProperty("DRIVER_CLASS");
		 String url = prop.getProperty("CONNECTION_URL");
		 String user = prop.getProperty("CONNECTION_USERNAME");
		 String password = prop.getProperty("CONNECTION_PASSWORD");
		 database.append("\tClass.forName(\""+driver+"\");\r\n");
		 database.append("\tConnection con = DriverManager.getConnection(\r\n");
		 database.append("\t\""+url+"\", \""+user+"\", \""+password+"\");\r\n");
		 database.append("\tStatement st = con.createStatement();\r\n");
		 database.append("\tResultSet rs;\r\n");
		 database.append("\tSystem.out.println(\"Connect to databse success!!!\");\r\n");

		returnValue.append("\t"+database+"\r\n");

		returnValue.append("\t\r\n");

	}

	/**
	 * @parameterFromMfVariablse WHERE,V£¬F,S(because when we create the mf scructure, we use s), so in this part, we can just traverse the mfvariables
	 * @param
	 * @return
	 * @author Xinyu Ge
	 * @param connectionFactory
	 * @date 2016-4-19
	 * @Description appent the content of MF-strucute initial to returnValue, this part is a part of printClass
	 */
	public void printFirstScan(ConnectionFactory connectionFactory){
		// we initial a variable and append it at last
		StringBuffer firstScan = new StringBuffer();

		String tableNameString = connectionFactory.getTableName();
		firstScan.append("\tArrayList<MFStruture> mfs = new ArrayList<MFStruture>(); \r\n");
		// Traversal every tuples of the whole table
		firstScan.append("\trs = st.executeQuery(\"select * from "+ tableNameString);
		// if this mfsql have a where clause, we append the where part to the sql part
		if(varibles.getWhere()!=null){
			firstScan.append(" where "+varibles.getWhere());
		}
		firstScan.append("\" ); \r\n");
		firstScan.append("\twhile (rs.next()){ \r\n");
		firstScan.append("\t	boolean flag = false; \r\n");

		firstScan.append("\tTable t = new Table();\r\n");
		//create a temp variable t for temp store the information for each tuple
		for(Map.Entry<String, String> entry:tableName.entrySet()){
			String upperCaseString =  entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
			String upperCaseString1 =  entry.getValue().substring(0, 1).toUpperCase() + entry.getValue().substring(1);
			firstScan.append("\tt.set"+upperCaseString+"(rs.get"+ upperCaseString1+ "(\""+entry.getKey()+"\"));\r\n");
		}
		
		
		ArrayList<String> F = varibles.getF();
		ArrayList<String> agg0 = new ArrayList<String>();
		for (int i = 0; i < F.size(); i++) {
			String f = F.get(i);
			if(f.contains("0")){
				agg0.add(f);
			}
		}
		
		//handle the mfs part
		firstScan.append("\t//handle the mfs part\r\n");
		firstScan.append("\tfor (int i = 0; i < mfs.size()&&!flag; i++) {\r\n");
		// if the tuples have not appear in MFstructure, we need add it to the arraylist
		firstScan.append("\t// if the tuples have not appear in MFstructure, we need add it to the arraylist \r\n");
		firstScan.append("\tif (");
		ArrayList<String> tupleV = varibles.getV();
		for (int j = 0; j < tupleV.size(); j++) {
			String upperCaseString =  tupleV.get(j).substring(0, 1).toUpperCase() + tupleV.get(j).substring(1);
			if(connectionFactory.getTableStructure().get(tupleV.get(j)).equals("int")){
				firstScan.append("mfs.get(i).get"+upperCaseString+"()==t.get"+upperCaseString+"()&&");
			}else{

				firstScan.append("mfs.get(i).get"+upperCaseString+"().equals(t.get"+upperCaseString+"())&&");
			}
		}

		firstScan.append("!flag){\r\n");
		firstScan.append("\t\tflag = true;\r\n");
		firstScan.append("\t}\r\n");
		firstScan.append("\t}\r\n");
		firstScan.append("\t");
		// if part
		// if this combine condition have not appear in mf structure, we need add this condition to the mf structure
		firstScan.append("\tif (!flag){\r\n");

		firstScan.append("\tMFStruture mfstructue = new MFStruture();\r\n");
		for (int j = 0; j < tupleV.size(); j++) {
			String upperCaseString =  tupleV.get(j).substring(0, 1).toUpperCase() + tupleV.get(j).substring(1);
			firstScan.append("\tmfstructue.set"+upperCaseString+"(t.get"+upperCaseString+"());\r\n");
		}
		// handle 0 part
		for (int i = 0; i < this.mfvaribles.size(); i++) {
			String mfvariable = mfvaribles.get(i);
			if(mfvariable.contains("0_")){
				String fakeMfvariable = mfvariable;
			
				if(fakeMfvariable.contains("avg_")){
					String attr = mfvariable.replace("mf0_avg_", "");
					fakeMfvariable = fakeMfvariable.replace("mf", "Mf");
					String mfcount = fakeMfvariable.replace("avg_", "count_");
					String mfsum =  fakeMfvariable.replace("avg_", "sum_");

					String Uattr =  attr.substring(0, 1).toUpperCase() +attr.substring(1);
					firstScan.append("\tmfstructue.set"+mfsum+"(t.get"+Uattr+"()+mfstructue.get"+mfsum+"());\r\n");
					firstScan.append("\tmfstructue.set"+mfcount+"(mfstructue.get"+mfsum+"()+1);\r\n");
				}
				else{
					
					String attr = mfvariable.replace("mf0_sum_", "").replace("mf0_max_", "").replace("mf0_min_", "").replace("mf0_count_", "");
					String Uattr =  attr.substring(0, 1).toUpperCase() +attr.substring(1);
					fakeMfvariable = fakeMfvariable.replace("mf", "Mf");
					if(mfvariable.contains("_sum_")){
						firstScan.append("\tmfstructue.set"+fakeMfvariable+"(t.get"+Uattr+"()+mfstructue.get"+fakeMfvariable+"());\r\n");
					}else if(mfvariable.contains("_count_")){
						firstScan.append("\tmfstructue.set"+fakeMfvariable+"(mfstructue.get"+fakeMfvariable+"()+1);\r\n");
					}else if(mfvariable.contains("_max_")){
						firstScan.append("\tif(mfstructue.get"+fakeMfvariable+"() <  t.get"+Uattr+"()){\r\n");
						firstScan.append("\tmfstructue.set"+fakeMfvariable+"( t.get"+Uattr+"());\r\n");
						firstScan.append("}\r\n");
		
					}else if(mfvariable.contains("_min_")){
						firstScan.append("\tif(mfstructue.get"+fakeMfvariable+"() > t.get"+Uattr+"()){\r\n");
						firstScan.append("\tmfstructue.set"+fakeMfvariable+"( t.get"+Uattr+"());\r\n");
						firstScan.append("}\r\n");
		
					}
					
				}

				
				
				
				
			}else if(connectionFactory.getTableStructure().containsKey(mfvariable)){
				if(!tupleV.contains(mfvariable)){
					String upperCaseString =  mfvariable.substring(0, 1).toUpperCase() + mfvariable.substring(1);
					firstScan.append("\tmfstructue.set"+upperCaseString+"(t.get"+upperCaseString+"());\r\n");
				}
				
			}else if(this.mfvaribles.contains(mfvariable)&&!(mfvariable.contains("0_"))){
				if(mfvariable.contains("avg_")){
					String fakeMfvariable = mfvariable.replace("mf", "Mf");
					String mfcount = fakeMfvariable.replace("avg_", "count_");
					String mfsum =  fakeMfvariable.replace("avg_", "sum_");

					firstScan.append("\tmfstructue.set"+mfsum+"(0);\r\n");
					firstScan.append("\tmfstructue.set"+mfcount+"0.00000001);\r\n");
				}
				else{
					if(mfvariable.contains("count_")){
						String fakeMfvariable = mfvariable.replace("mf", "Mf");
						firstScan.append("\tmfstructue.set"+fakeMfvariable+"(0.00000001);\r\n");
					}else{
						String fakeMfvariable = mfvariable.replace("mf", "Mf");
						firstScan.append("\tmfstructue.set"+fakeMfvariable+"(0);\r\n");
					}
				}
			}
		}

		
		// add it
		firstScan.append("\tmfs.add(mfstructue);\r\n");


		firstScan.append("\t}\r\n");
		//end of if part

		firstScan.append("\t}\r\n");
		firstScan.append("\t\r\n");
		firstScan.append("// now we have finished the first step to create our mf structure based on grouping varibles, initial the mf structure");
		firstScan.append("\t\r\n");
		firstScan.append("\t\r\n");
		firstScan.append("\t\r\n");

		returnValue.append(firstScan);
	}
	/**
	 * @parameterFromMfVariablse N,WHERE,SIGMA,F
	 * @param
	 * @return
	 * @author Xuan Li
	 * @param connectionFactory
	 * @date 2016-4-17
	 * @Description appent the content of MF-strucute update of each Traversal to returnValue, this part is a part of printClass
	 * 				We need get N, where, sigma, F from mf variables
	 */

	private void printHandleMFstructure(ConnectionFactory connectionFactory) {
		StringBuffer handleMFstructure = new StringBuffer();
		// we need get the times we need scan the table, that is N 
		int number = this.varibles.getN();
		// we need get the situation we need update our mf structure, that is sigmas(such that), we can divide the whole table into different groups
		ArrayList<ArrayList> sigmas = varibles.getSigma();
		// we nned know the type of basic attributes, if it is int , we use == and !=, if that is not , we use equals and !equals, that is very important
		String tableNameString = connectionFactory.getTableName();
		for (int i = 0; i < number; i++) {
			ArrayList sigma = sigmas.get(i);
			handleMFstructure.append("\t//the "+ (i+1) + " time scan the table\r\n");
			handleMFstructure.append("\trs = st.executeQuery(\"select * from "+ tableNameString+" ");
			// if this mfsql have a where clause, we append the where part to the sql part
			if(varibles.getWhere()!=null){
				handleMFstructure.append(" where "+varibles.getWhere());
			}

			handleMFstructure.append("\"); \r\n");
			handleMFstructure.append("\twhile (rs.next()){ \r\n");
			handleMFstructure.append("\tTable t = new Table();\r\n");
			//create a temp variable t for temp store the information for each tuple
			for(Map.Entry<String, String> entry:tableName.entrySet()){
				String upperCaseString =  entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
				String upperCaseString1 =  entry.getValue().substring(0, 1).toUpperCase() + entry.getValue().substring(1);
				handleMFstructure.append("\tt.set"+upperCaseString+"(rs.get"+ upperCaseString1+ "(\""+entry.getKey()+"\"));\r\n");
			}
			handleMFstructure.append("\tfor(int i = 0;i < mfs.size(); i ++){ \r\n");
			handleMFstructure.append("\t \r\n");
			handleMFstructure.append("\tMFStruture mf = mfs.get(i); \r\n");

			// begin of the if condition --- to handle the grouping by part
			handleMFstructure.append("\tif (");

			for (int j = 0; j < sigma.size(); j++) {
				String sg = (String) sigma.get(j);
				String type = "";
				String left = "";
				String right = "";
				String operater = "";
				HashMap<String, String> tableStructure = connectionFactory.getTableStructure();
				if(sg.contains("=")){
					operater = "=";
				}else if(sg.contains("<>")){
					operater = "<>";
				}else if(sg.contains("<")){
					operater = "<";
				}else if(sg.contains(">")){
					operater = ">";
				}
					//left part  we assume that the left part must come from table
					left = sg.split(operater)[0].trim().split("_")[1].trim();
					right = sg.split(operater)[1].trim();

					type = tableStructure.get(left);
					if(operater.equals("=")){
						if(type.equals("int")){
							operater = "=";
						}else{
							operater = "equals";
						}
					}
					if(operater.equals("<>")){
						if(type.equals("int")){
							operater = "!=";
						}else{
							operater = "!equals";
						}
					}
					String upperCaseLf =  left.substring(0, 1).toUpperCase() + left.substring(1);
					if(operater.equals("=")){
						handleMFstructure.append("(t.get"+upperCaseLf+"() == ");
					}else if(operater.equals("equals")){
						handleMFstructure.append("(t.get"+upperCaseLf+"().equals(");
					}else if(operater.equals("!=")){
						handleMFstructure.append("(t.get"+upperCaseLf+"() !=");
					}else if(operater.equals("!equals")){
						handleMFstructure.append("!(t.get"+upperCaseLf+"().equals(");
					}else{
						handleMFstructure.append("(t.get"+upperCaseLf+"() "+operater);
					}



					//right part  we consider the right part from different aspect
					//1.  if the right part come from MFStructure (none agg part)

					String fakeRight =  right.substring(0, 1).toUpperCase() + right.substring(1);
					if(mfvaribles.contains(right)){
						if(operater.equals("equals")||operater.equals("!equals")){
							handleMFstructure.append("mfs.get(i).get"+fakeRight+"()))");
						}else{
							handleMFstructure.append("mfs.get(i).get"+fakeRight+"())");
						}

					}else{
						//2.  if the right part come from MFStructure( agg part)
							fakeRight = "mf"+right;
							// if the right part contains avg, we need split the avg to count and sum, and re-calculate it at last
							if(fakeRight.contains("avg_")){
								fakeRight = fakeRight.replace("mf", "Mf");
								String mfcount = fakeRight.replace("avg_", "count_");
								String mfsum =  fakeRight.replace("avg_", "sum_");
								handleMFstructure.append("(mfs.get(i).get"+mfcount+"() / mfs.get(i).get"+mfsum+"()))");
							}
							else if(mfvaribles.contains(fakeRight)){
							if(operater.equals("=")){
								handleMFstructure.append("mfs.get(i).get"+fakeRight+"())");
							}else{
								handleMFstructure.append("mfs.get(i).get"+fakeRight+"()))");
							}

						}else{
							//3.  if the right part is a equation
							// In this part ,when we spilt the string ,we need consider that the +/*- is special sign, so we need tranlate it us \\
							if((right.contains("+")||right.contains("-")||right.contains("*"))||right.contains("/")){
								if(right.contains("+")){
									String l = right.split("\\+")[0].trim();
									String r = right.split("\\+")[1].trim();
									if(this.mfvaribles.contains(l)){
										l =  l.substring(0, 1).toUpperCase() + l.substring(1);
										l = "mf.get"+l+"()";

									}else if(this.mfvaribles.contains(r)) {
										r =  r.substring(0, 1).toUpperCase() + l.substring(1);
										r = "mf.get"+r+"()";

									}
									handleMFstructure.append(l +"+ "+ r+")");
								}else if(right.contains("-")){
									String l = right.split("\\-")[0].trim();
									String r = right.split("\\-")[1].trim();
									if(this.mfvaribles.contains(l)){
										l =  l.substring(0, 1).toUpperCase() + l.substring(1);
										l = "mf.get"+l+"()";

									}else if(this.mfvaribles.contains(r)) {
										r =  r.substring(0, 1).toUpperCase() + l.substring(1);
										r = "mf.get"+r+"()";

									}
									handleMFstructure.append(l +"- "+ r+")");
								}else if(right.contains("*")){
									String l = right.split("\\*")[0].trim();
									String r = right.split("\\*")[1].trim();
									if(this.mfvaribles.contains(l)){
										l =  l.substring(0, 1).toUpperCase() + l.substring(1);
										l = "mf.get"+l+"()";

									}else if(this.mfvaribles.contains(r)) {
										r =  r.substring(0, 1).toUpperCase() + l.substring(1);
										r = "mf.get"+r+"()";

									}
									handleMFstructure.append(l +"* "+ r+")");
								}else if(right.contains("/")){
									String l = right.split("\\/")[0].trim();
									String r = right.split("\\/")[1].trim();
									if(this.mfvaribles.contains(l)){
										l =  l.substring(0, 1).toUpperCase() + l.substring(1);
										l = "mf.get"+l+"()";

									}else if(this.mfvaribles.contains(r)) {
										r =  r.substring(0, 1).toUpperCase() + l.substring(1);
										r = "mf.get"+r+"()";

									}
									handleMFstructure.append(l +"/ "+ r+")");
								}
								
							}else{
								right = right.replace("'", "\"");
								handleMFstructure.append(right+"))");

							}

								
						}


					}




					handleMFstructure.append("&&");

			}
			// delete the last &&, because it is useless
			handleMFstructure.deleteCharAt(handleMFstructure.length()-1);
			handleMFstructure.deleteCharAt(handleMFstructure.length()-1);
			handleMFstructure.append("){\r\n");
			
			
			// now we need consider the F part!
			ArrayList<String> f = varibles.getF();

			for (int j = 0; j < f.size(); j++) {
				String fect = f.get(j);
				int x = i+1;
				if (fect.contains(x+"_")) {
					// if the f contains avg, we need split the avg to count and sum, and re-calculate it at last
					if(fect.contains("avg_")){
						String mfcount = "Mf"+x+"_count_"+fect.split("_")[2];
						String mfcount1 = "Mf"+x+"_sum_"+fect.split("_")[2];
						String upperCaseString =  fect.split("_")[2].substring(0, 1).toUpperCase() + fect.split("_")[2].substring(1);
						handleMFstructure.append("\tmf.set"+mfcount1+"(mf.get"+mfcount1+"()+ t.get"+upperCaseString+"());\r\n");

						handleMFstructure.append("\tmf.set"+mfcount+"(mf.get"+mfcount+"()+1);\r\n");
					}else if(fect.contains("max_")){{

						String mMax= "Mf"+x+"_max_"+fect.split("_")[2];

						String upperCaseString =  fect.split("_")[2].substring(0, 1).toUpperCase() + fect.split("_")[2].substring(1);
						handleMFstructure.append("\tif(mf.get"+mMax+"() <  t.get"+upperCaseString+"()){\r\n");

						handleMFstructure.append("\tmf.set"+mMax+"( t.get"+upperCaseString+"());\r\n");
						handleMFstructure.append("}\r\n");
					}
					}else if(fect.contains("min_")){
						String mMin= "Mf"+x+"_min_"+fect.split("_")[2];

						String upperCaseString =  fect.split("_")[2].substring(0, 1).toUpperCase() + fect.split("_")[2].substring(1);
						handleMFstructure.append("\tif(mf.get"+mMin+"() >  t.get"+upperCaseString+"()){\r\n");

						handleMFstructure.append("\tmf.set"+mMin+"( t.get"+upperCaseString+"());\r\n");
						handleMFstructure.append("}\r\n");

					}else if(fect.contains("count_")){
						String mfcount = "Mf"+x+"_count_"+fect.split("_")[2];
						String upperCaseString =  fect.split("_")[2].substring(0, 1).toUpperCase() + fect.split("_")[2].substring(1);
						handleMFstructure.append("\tmf.set"+mfcount+"(mf.get"+mfcount+"()+ t.get"+upperCaseString+"());\r\n");


					}else if(fect.contains("sum_")){

						String mfcount = "Mf"+x+"_sum_"+fect.split("_")[2];
						String upperCaseString =  fect.split("_")[2].substring(0, 1).toUpperCase() + fect.split("_")[2].substring(1);
						handleMFstructure.append("\tmf.set"+mfcount+"(mf.get"+mfcount+"()+ t.get"+upperCaseString+"());\r\n");

					}
					handleMFstructure.append("\tmfs.set(i, mf);\r\n");

				}
			}



			//end of if condition




			//handleMFstructure.append("ssss"+sigma+"\r\n");
			handleMFstructure.append("\t}\r\n");


			handleMFstructure.append("\t}\r\n");
			handleMFstructure.append("\t}\r\n");

		}

		returnValue.append(handleMFstructure);
	}


	/**
	 *
	 * @param
	 * @return
	 * @author Xuan Li
	 * @param connectionFactory
	 * @date 2016-4-22
	 * @parameterFromMfVariablse G
	 * @Description appent the content of MF-strucute update(delete) to returnValue, this part is a part of printClass
	 * 				We need delete some elements of mf-sturcure, base on having clause.
	 */
	private void printHavingClause(ConnectionFactory connectionFactory) {
		StringBuffer havingClause = new StringBuffer();
		ArrayList<String> havings = varibles.getG();
		if(havings.size()!=0){
			havingClause.append("for(int i = 0;i < mfs.size(); i ++){ \r\n");
			havingClause.append("\tMFStruture mf = mfs.get(i);\r\n");
			// if contiditon insert
			havingClause.append("if(!(");
			for (int i = 0; i < havings.size(); i++) {
				String left = "";
				String right = "";
				String operater = "";
				String sg = havings.get(i);
				if(sg.contains("=")){
					operater = "=";
				}else if(sg.contains("<>")){
					operater = "<>";
				}else if(sg.contains("<")){
					operater = "<";
				}else if(sg.contains(">")){
					operater = ">";
				}
					//left part  we assume that the left part
					left = sg.split(operater)[0].trim();
					right = sg.split(operater)[1].trim();
					// if the left part contains avg, we need split the avg to count and sum, and re-calculate it at last
					if(left.contains("avg_")){
						String sum = left.replace("avg_", "sum_");
						String count = left.replace("avg_", "count_");
						sum = "Mf"+sum;
						count = "Mf"+count;
						havingClause.append("(mf.get"+sum+"() / mf.get"+count+"())");


					}else{
						String fakeLeft = "mf"+left;
						if(mfvaribles.contains(fakeLeft)){
							havingClause.append("(mf.get"+fakeLeft+"()");

						}
					}
					if(operater.equals("<>")){
						operater = "!=";
					}
					if(operater.equals("=")){
						operater = "==";
					}
					havingClause.append(operater);
					//right part  we consider the right part from different aspect
					//1.  if the right part come from MFStructure (none agg part)

					String fakeRight = "mf"+right;
					if(mfvaribles.contains(right)){
						fakeRight =  right.substring(0, 1).toUpperCase() + right.substring(1);
						havingClause.append("(mf.get"+fakeRight+"()");

					}
					// if the right contains avg, we need split the avg to count and sum, and re-calculate it at last
					if(right.contains("avg_")){
						String sum = right.replace("avg_", "sum_");
						String count = right.replace("avg_", "count_");
						sum = "Mf"+sum;
						count = "Mf"+count;

						havingClause.append("(mf.get"+sum+"() / mf.get"+count+"())");
					}

						if(mfvaribles.contains(fakeRight)){


								fakeRight =  right.substring(0, 1).toUpperCase() + right.substring(1);
								havingClause.append("(mf.get"+fakeRight+"()");


						}

							//3.  if the right part is a equation
						else if((right.contains("+")||right.contains("-")||right.contains("*"))||right.contains("/")){
								if(right.contains("+")){
									String l = right.split("+")[0];
									String r = right.split("+")[1];
									if(this.mfvaribles.contains(l)){
										l =  l.substring(0, 1).toUpperCase() + l.substring(1);
										l = "mf.get"+l+"()";

									}else if(this.mfvaribles.contains(r)) {
										r =  r.substring(0, 1).toUpperCase() + l.substring(1);
										r = "mf.get"+r+"()";

									}
									havingClause.append(l +"+ "+ r+")");
								}else if(right.contains("-")){
									String l = right.split("-")[0];
									String r = right.split("-")[1];
									if(this.mfvaribles.contains(l)){
										l =  l.substring(0, 1).toUpperCase() + l.substring(1);
										l = "mf.get"+l+"()";

									}else if(this.mfvaribles.contains(r)) {
										r =  r.substring(0, 1).toUpperCase() + l.substring(1);
										r = "mf.get"+r+"()";

									}
									havingClause.append(l +"- "+ r+")");
								}else if(right.contains("*")){
									String l = right.split("*")[0];
									String r = right.split("*")[1];
									if(this.mfvaribles.contains(l)){
										l =  l.substring(0, 1).toUpperCase() + l.substring(1);
										l = "mf.get"+l+"()";

									}else if(this.mfvaribles.contains(r)) {
										r =  r.substring(0, 1).toUpperCase() + l.substring(1);
										r = "mf.get"+r+"()";

									}
									havingClause.append(l +"* "+ r+")");
								}else if(right.contains("/")){
									String l = right.split("/")[0];
									String r = right.split("/")[1];
									if(this.mfvaribles.contains(l)){
										l =  l.substring(0, 1).toUpperCase() + l.substring(1);
										l = "mf.get"+l+"()";

									}else if(this.mfvaribles.contains(r)) {
										r =  r.substring(0, 1).toUpperCase() + l.substring(1);
										r = "mf.get"+r+"()";

									}
									havingClause.append(l +"/ "+ r+")");
								}
							}







					havingClause.append("&&");


			}
			// delete the last &&, because it is useless
			havingClause.deleteCharAt(havingClause.length()-1);
			havingClause.deleteCharAt(havingClause.length()-1);
			havingClause.append(")){\r\n");
			havingClause.append("\tmfs.remove(i);\r\n");
			havingClause.append("\ti--;\r\n");




			havingClause.append("}\r\n");

			havingClause.append("}\r\n");
		}
		returnValue.append(havingClause);


	}

	/**
	 *
	 * @param
	 * @return
	 * @author Xuan Li
	 * @param connectionFactory
	 * @date 2016-4-23
	 * @parameterFromMfVariablse S
	 * @Description appent the content of FinalResult to returnValue, this part is a part of printClass
	 * 				we need output the final result, and show it to our user
	 */

	private void printFinalResult(ConnectionFactory connectionFactory) {
		StringBuffer finalString = new StringBuffer();
		// the output items are base on SELECT ATTRIBUTE(S):
		ArrayList<String> select = varibles.getS();
		ArrayList<Integer> lengths = new ArrayList<Integer>();
		for (int i = 0; i < select.size(); i++) {
			int length = 0;
			String attr = select.get(i);
			
			length = attr.length();
			if(connectionFactory.getTableStructure().containsKey(attr)){
				length = 8;
			}
			if(attr.contains("_")){
				finalString.append("\tSystem.out.printf(\"%-"+length+"s\",\""+attr+"\");\r\n");
			}else if(attr.contains("+")||attr.contains("-")||attr.contains("*")||attr.contains("/")){
				finalString.append("\tSystem.out.printf(\"%-"+length+"d\",\""+attr+"\");\r\n");
			}else if(connectionFactory.getTableStructure().containsKey(attr)){
				if(connectionFactory.getTableStructure().get(attr).equals("int")){
					finalString.append("\tSystem.out.printf(\"%-"+length+"s\",\""+attr+"\");\r\n");
				}else{
					finalString.append("\tSystem.out.printf(\"%-"+length+"s\",\""+attr+"\");\r\n");
				}
			}else{
				finalString.append("\tSystem.out.printf(\"%-"+length+"s\",\""+attr+"\");\r\n");
			}
			finalString.append("\tSystem.out.print(\"    \");\r\n");
			lengths.add(length);
		}
		finalString.append("\tSystem.out.println(\"\");\r\n");
		for (int i = 0; i < select.size(); i++) {
			int length = lengths.get(i);
			finalString.append("\tSystem.out.print(\"");
			for (int j = 0; j < length; j++) {
				finalString.append("=");
			}
			finalString.append("\");\r\n");
			finalString.append("\tSystem.out.print(\"    \");\r\n");
		}
		finalString.append("\tSystem.out.println(\"\");\r\n");
		finalString.append("\tfor (int i = 0; i < mfs.size(); i++){\r\n");
		finalString.append("\tMFStruture mf = mfs.get(i);\r\n");


		finalString.append("\r\n");
		
		for (int i = 0; i < select.size(); i++) {
			String attr = select.get(i);
			// if the output is not just a attribute,we need consider the ouput is a equation
			int length = lengths.get(i);
		
			if(attr.contains("/")||attr.contains("+")||attr.contains("*")||attr.contains("-")){
				finalString.append("\tSystem.out.printf(\"");
				finalString.append("%"+length);
				finalString.append("f\",");
				String left = "";
				String right = "";
				String operater = "";
				if(attr.contains("+")){
					operater = "+";
				}else if(attr.contains("-")){
					operater = "-";
				}else if(attr.contains("*")){
					operater = "*";
				}else if(attr.contains("/")){
					operater = "/";
				}
				left = attr.split(operater)[0].trim();
				right = attr.split(operater)[1].trim();
				// if the left part contains avg, we need combine the sum and count we split before and calculate it
				if (left.contains("avg_")) {
					String sum = left.replace("avg_", "sum_").trim();
					String count = left.replace("avg_", "count_").trim();;
					sum = "Mf"+sum;
					count = "Mf"+count;
					finalString.append("\tDouble.parseDouble(mf.get"+sum+"())+\"\" / Double.parseDouble(mf.get"+count+"()+\"\")+);\r\n");
				}else{
					String agg = "Mf"+left;
					finalString.append("Double.parseDouble(mf.get"+agg+"()+\"\")");
				}
				finalString.append(operater);
				//right part
				// if the right part contains avg, we need combine the sum and count we split before and calculate it
				if (right.contains("avg_")) {
					String sum = right.replace("avg_", "sum_").trim();
					String count = right.replace("avg_", "count_").trim();;
					sum = "Mf"+sum;
					count = "Mf"+count;
					finalString.append("Double.parseDouble(mf.get"+sum+"())+\"\" / Double.parseDouble(mf.get"+count+"()+\"\")+);\r\n");
				}else{
					String agg = "Mf"+right;
					finalString.append("Double.parseDouble(mf.get"+agg+"()+\"\")");

				}
				finalString.append(");\r\n");




			}else if(attr.contains("_")){
				// if the attr contains avg, we need combine the sum and count we split before and calculate it
				finalString.append("\tSystem.out.printf(\"");
				finalString.append("%"+length);
				finalString.append("f\",");
				if (attr.contains("avg_")) {
					String sum = attr.replace("avg_", "sum_").trim();
					String count = attr.replace("avg_", "count_").trim();;
					sum = "Mf"+sum;
					count = "Mf"+count;
					finalString.append("\tDouble.parseDouble(mf.get"+sum+"()+\"\") / Double.parseDouble(mf.get"+count+"()+\"\"));\r\n");
				}else{
					String agg = "Mf"+attr;
					finalString.append("\tmf.get"+agg+"());\r\n");
					}
			}else{
				finalString.append("\tSystem.out.printf(\"");
				if(connectionFactory.getTableStructure().get(attr).equals("int")){
					finalString.append("%"+length);
					finalString.append("d\",");
				}else{
					finalString.append("%-"+length);
					finalString.append("s\",");
				}
			
				String upperCaseString =  attr.substring(0, 1).toUpperCase() + attr.substring(1);
				finalString.append("mf.get"+upperCaseString+"());\r\n");
			}
			finalString.append("\tSystem.out.print(\"    \");\r\n");
		}
		finalString.append("\tSystem.out.println(\"\");\r\n");
		
		finalString.append("\t}\r\n");






		returnValue.append(finalString);
	}
	/**
	 *
	 * @param
	 * @return
	 * @author Xuan Li
	 * @param connectionFactory
	 * @date 2016-4-20
	 * @parameterFromMfVariablse 
	 * @Description appent the content of main Output class to returnValue,
	 * 				In this method, we need call some other print function, such as printDatabaseConnt,printFirstScan,printHandleMFstructure,printHavingClause,printFinalResult
	 * 			
	 */
	public void printClass(ConnectionFactory connectionFactory){
		
		String classString= "public class  "+fileName.split("\\.")[0]+" {\r\n";
		returnValue.append(classString);
		String mainString = "\tpublic static void main(String[] args) throws Exception {\r\n";
		
		returnValue.append(mainString);
		//for test time
		returnValue.append("\tlong startTime=System.currentTimeMillis();\r\n");
		// print connect to database
		printDatabaseConnt(connectionFactory);
		// print the first scan

		printFirstScan(connectionFactory);
		// print the update mf sturcute part
		printHandleMFstructure(connectionFactory);
		// print having part, delete some element from mfs
		printHavingClause(connectionFactory);
		// print the final result based on seletion part
		printFinalResult(connectionFactory);
		//for test time
		returnValue.append("\tlong endTime=System.currentTimeMillis();\r\n");
		returnValue.append("\tlong Time=endTime-startTime;\r\n");
		returnValue.append("\tSystem.out.println(\"the program run£º \"+Time+\"ms\");\r\n");
		
		//for main function
		returnValue.append("}\r\n");
		// for class
		returnValue.append("}\r\n");

	}



	public OutputFile() {
		super();
	}
	/**
	 * @author Xuan Li
	 * @param varibles
	 * @param connectionFactory
	 * @Title: write
	 * @Description:  this part is the entry for this class, during some process, this function will return returnVaule and that is the reuslt we want to output to file
	 * @return String
	 */
	public  String write(ConnectionFactory connectionFactory) {
		
		returnValue= new StringBuffer();
		
		//print comment part to return value
		printComments();
		//print package part to return value
		printPackage();
		//print import part to return value
		printImport();
		//print table part to return value, this part output the table strucute of target table
		printEntity();
		//print mf structure part to return value, print the object stucture of MF/EMF
		printMFStruture(connectionFactory);
		// print the most import part, this part will handle and process the mf procession
		printClass(connectionFactory);
		// return the final result 
		return returnValue.toString();
	}

}

