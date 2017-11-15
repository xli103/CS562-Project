/**
 @Title: Output.java
 @Package stevens.cs562.SQLKnokout.output
 @Description: The output File for final query of cs562 project
 @author Xuan Li,Xinyu Ge (Group SQLKnockout)
 @date 2016-12-09
 @version V1.0  
*/ 
package stevens.cs562.SQLKnokout.output;

import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.sql.ResultSet;


 class Table{
	private String prod;
	public String getProd(){
	return prod;
	}
	public void setProd(String prod){
	this.prod = prod;
	}
	private int month;
	public int getMonth(){
	return month;
	}
	public void setMonth(int month){
	this.month = month;
	}
	private int year;
	public int getYear(){
	return year;
	}
	public void setYear(int year){
	this.year = year;
	}
	private String state;
	public String getState(){
	return state;
	}
	public void setState(String state){
	this.state = state;
	}
	private int quant;
	public int getQuant(){
	return quant;
	}
	public void setQuant(int quant){
	this.quant = quant;
	}
	private String cust;
	public String getCust(){
	return cust;
	}
	public void setCust(String cust){
	this.cust = cust;
	}
	private int day;
	public int getDay(){
	return day;
	}
	public void setDay(int day){
	this.day = day;
	}
}

 class MFStruture{
	private String prod;
	public String getProd(){
	return prod;
	}
	public void setProd(String prod){
	this.prod = prod;
	}
	private int month;
	public int getMonth(){
	return month;
	}
	public void setMonth(int month){
	this.month = month;
	}
	private double mf1_count_quant;
	public double getMf1_count_quant(){
	return mf1_count_quant;
	}
	public void setMf1_count_quant(double mf1_count_quant){
	this.mf1_count_quant = mf1_count_quant;
	}
	private double mf1_sum_quant;
	public double getMf1_sum_quant(){
	return mf1_sum_quant;
	}
	public void setMf1_sum_quant(double mf1_sum_quant){
	this.mf1_sum_quant = mf1_sum_quant;
	}
	private double mf2_count_quant;
	public double getMf2_count_quant(){
	return mf2_count_quant;
	}
	public void setMf2_count_quant(double mf2_count_quant){
	this.mf2_count_quant = mf2_count_quant;
	}
	private double mf2_sum_quant;
	public double getMf2_sum_quant(){
	return mf2_sum_quant;
	}
	public void setMf2_sum_quant(double mf2_sum_quant){
	this.mf2_sum_quant = mf2_sum_quant;
	}
	private double mf3_count_quant;
	public double getMf3_count_quant(){
	return mf3_count_quant;
	}
	public void setMf3_count_quant(double mf3_count_quant){
	this.mf3_count_quant = mf3_count_quant;
	}
}
public class  Output {
	public static void main(String[] args) throws Exception {
	long startTime=System.currentTimeMillis();
		Class.forName("org.postgresql.Driver");
	Connection con = DriverManager.getConnection(
	"jdbc:postgresql://localhost:5432/CS562", "postgres", "lxh920929");
	Statement st = con.createStatement();
	ResultSet rs;
	System.out.println("Connect to databse success!!!");

	
	ArrayList<MFStruture> mfs = new ArrayList<MFStruture>(); 
	rs = st.executeQuery("select * from sales" ); 
	while (rs.next()){ 
		boolean flag = false; 
	Table t = new Table();
	t.setProd(rs.getString("prod"));
	t.setMonth(rs.getInt("month"));
	t.setYear(rs.getInt("year"));
	t.setState(rs.getString("state"));
	t.setQuant(rs.getInt("quant"));
	t.setCust(rs.getString("cust"));
	t.setDay(rs.getInt("day"));
	//handle the mfs part
	for (int i = 0; i < mfs.size()&&!flag; i++) {
	// if the tuples have not appear in MFstructure, we need add it to the arraylist 
	if (mfs.get(i).getProd().equals(t.getProd())&&mfs.get(i).getMonth()==t.getMonth()&&!flag){
		flag = true;
	}
	}
		if (!flag){
	MFStruture mfstructue = new MFStruture();
	mfstructue.setProd(t.getProd());
	mfstructue.setMonth(t.getMonth());
	mfstructue.setMf1_count_quant(0.00000001);
	mfstructue.setMf1_sum_quant(0);
	mfstructue.setMf2_count_quant(0.00000001);
	mfstructue.setMf2_sum_quant(0);
	mfstructue.setMf3_count_quant(0.00000001);
	mfs.add(mfstructue);
	}
	}
	
// now we have finished the first step to create our mf structure based on grouping varibles, initial the mf structure	
	
	
	//the 1 time scan the table
	rs = st.executeQuery("select * from sales "); 
	while (rs.next()){ 
	Table t = new Table();
	t.setProd(rs.getString("prod"));
	t.setMonth(rs.getInt("month"));
	t.setYear(rs.getInt("year"));
	t.setState(rs.getString("state"));
	t.setQuant(rs.getInt("quant"));
	t.setCust(rs.getString("cust"));
	t.setDay(rs.getInt("day"));
	for(int i = 0;i < mfs.size(); i ++){ 
	 
	MFStruture mf = mfs.get(i); 
	if ((t.getProd().equals(mfs.get(i).getProd()))&&(t.getMonth() == mf.getMonth()- 1)){
	mf.setMf1_sum_quant(mf.getMf1_sum_quant()+ t.getQuant());
	mf.setMf1_count_quant(mf.getMf1_count_quant()+1);
	mfs.set(i, mf);
	}
	}
	}
	//the 2 time scan the table
	rs = st.executeQuery("select * from sales "); 
	while (rs.next()){ 
	Table t = new Table();
	t.setProd(rs.getString("prod"));
	t.setMonth(rs.getInt("month"));
	t.setYear(rs.getInt("year"));
	t.setState(rs.getString("state"));
	t.setQuant(rs.getInt("quant"));
	t.setCust(rs.getString("cust"));
	t.setDay(rs.getInt("day"));
	for(int i = 0;i < mfs.size(); i ++){ 
	 
	MFStruture mf = mfs.get(i); 
	if ((t.getProd().equals(mfs.get(i).getProd()))&&(t.getMonth() == mf.getMonth()+ 1)){
	mf.setMf2_sum_quant(mf.getMf2_sum_quant()+ t.getQuant());
	mf.setMf2_count_quant(mf.getMf2_count_quant()+1);
	mfs.set(i, mf);
	}
	}
	}
	//the 3 time scan the table
	rs = st.executeQuery("select * from sales "); 
	while (rs.next()){ 
	Table t = new Table();
	t.setProd(rs.getString("prod"));
	t.setMonth(rs.getInt("month"));
	t.setYear(rs.getInt("year"));
	t.setState(rs.getString("state"));
	t.setQuant(rs.getInt("quant"));
	t.setCust(rs.getString("cust"));
	t.setDay(rs.getInt("day"));
	for(int i = 0;i < mfs.size(); i ++){ 
	 
	MFStruture mf = mfs.get(i); 
	if ((t.getProd().equals(mfs.get(i).getProd()))&&(t.getMonth() == mfs.get(i).getMonth())&&(t.getQuant() >(mfs.get(i).getMf1_count_quant() / mfs.get(i).getMf1_sum_quant()))&&(t.getQuant() <(mfs.get(i).getMf2_count_quant() / mfs.get(i).getMf2_sum_quant()))){
	mf.setMf3_count_quant(mf.getMf3_count_quant()+ t.getQuant());
	mfs.set(i, mf);
	}
	}
	}
	System.out.printf("%-8s","prod");
	System.out.print("    ");
	System.out.printf("%-8s","month");
	System.out.print("    ");
	System.out.printf("%-13s","3_count_quant");
	System.out.print("    ");
	System.out.println("");
	System.out.print("========");
	System.out.print("    ");
	System.out.print("========");
	System.out.print("    ");
	System.out.print("=============");
	System.out.print("    ");
	System.out.println("");
	for (int i = 0; i < mfs.size(); i++){
	MFStruture mf = mfs.get(i);

	System.out.printf("%-8s",mf.getProd());
	System.out.print("    ");
	System.out.printf("%8d",mf.getMonth());
	System.out.print("    ");
	System.out.printf("%13f",	mf.getMf3_count_quant());
	System.out.print("    ");
	System.out.println("");
	}
	long endTime=System.currentTimeMillis();
	long Time=endTime-startTime;
	System.out.println("the program run�� "+Time+"ms");
}
}
