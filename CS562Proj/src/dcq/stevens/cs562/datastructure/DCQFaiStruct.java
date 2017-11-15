package dcq.stevens.cs562.datastructure;

import java.util.List;

public class DCQFaiStruct {
	
	//Fai operator S, store all projection attributes
	private List<String> operator_S;
	
	//Fai operator N, store the number of group variables
	private Integer operator_N;
	
	//Fai operator V, store grouping attributes
	private List<String> operator_V;
	
	//Fai operator F, store aggregation function list
	private List<String> operator_F;
	
	//Fai operator G, store having clause
	private List<String> operator_G;
	
	//Fai operator Theta, store conditions of grouping;
	private List<String> operator_Theta;
	
	//setters and getters
	
	public List<String> getOperator_S() {
		return operator_S;
	}
	public void setOperator_S(List<String> operator_S) {
		this.operator_S = operator_S;
	}
	public Integer getOperator_N() {
		return operator_N;
	}
	public void setOperator_N(Integer operator_N) {
		this.operator_N = operator_N;
	}
	public List<String> getOperator_V() {
		return operator_V;
	}
	public void setOperator_V(List<String> operator_V) {
		this.operator_V = operator_V;
	}
	public List<String> getOperator_F() {
		return operator_F;
	}
	public void setOperator_F(List<String> operator_F) {
		this.operator_F = operator_F;
	}
	public List<String> getOperator_G() {
		return operator_G;
	}
	public void setOperator_G(List<String> operator_G) {
		this.operator_G = operator_G;
	}
	public List<String> getOperator_Theta() {
		return operator_Theta;
	}
	public void setOperator_Theta(List<String> operator_Theta) {
		this.operator_Theta = operator_Theta;
	}
	
	
}
