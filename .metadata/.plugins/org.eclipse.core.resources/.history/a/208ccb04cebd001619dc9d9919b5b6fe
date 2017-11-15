/*
 * Name: Xuan Li,Xinyu Ge
 * CWID: 10409939,10405323
 */
/**  
 * @Title: EMFVariables.java 
 * @Package stevens.cs562.SQLKnokout.EMF 
 * @Description: TODO:
 * @author Xinyu Ge
 * @date 2016-4-30 
 * @version V1.0  
 */ 
package stevens.cs562.SQLKnokout.entity;

import java.util.ArrayList;

/**
 * 
* @ClassName: EMFVariables 
* @Description: The entity calss includes 6 EMF variables and the getter and setter function for those variables also include the equals and hashcode function
* @author Xinyu Ge
* @date 2016-4-30  ÏÂÎç09:39:09 
*
 */
public class EMFVariables {
	
	private ArrayList<String>  S;//SELECT ATTRIBUTE(S)
	private Integer N;//NUMBER OF GROUPING VARIABLES(n)
	private ArrayList<String> V;//GROUPING ATTRIBUTES(V)
	private ArrayList<String> F;//F-VECT([F])
	private ArrayList<ArrayList> Sigma;//SELECT CONDITION-VECT([¦Ò])
	private ArrayList<String> G;//HAVING_CONDITION(G)
	private String where;//WHERE CLAUSE
	/**
	 * 
	 * @return ArrayList
	 * @author Xinyu Ge
	 * @date 2016-4-30
	 */
	public ArrayList<String> getS() {
		return S;
	}
	/**
	 * 
	 * @param s
	 * @author Xinyu Ge
	 * @date 2016-4-30
	 */
	public void setS(ArrayList<String> s) {
		S = s;
	}
	/**
	 * 
	 * @return ArrayList
	 * @author Xinyu Ge
	 * @date 2016-4-30
	 */
	public Integer getN() {
		return N;
	}
	/**
	 * 
	 * @param n
	 * @author Xinyu Ge
	 * @date 2016-4-30
	 */
	public void setN(Integer n) {
		N = n;
	}
	/**
	 * 
	 * @return ArrayList
	 * @author Xinyu Ge
	 * @date 2016-4-30
	 */
	public ArrayList<String> getV() {
		return V;
	}
	/**
	 * 
	 * @param v
	 * @author Xinyu Ge
	 * @date 2016-4-30
	 */
	public void setV(ArrayList<String> v) {
		V = v;
	}
	/**
	 * 
	 * @return ArrayList
	 * @author Xinyu Ge
	 * @date 2016-4-30
	 */
	public ArrayList<String> getF() {
		return F;
	}
	/**
	 * 
	 * @param f
	 * @author Xinyu Ge
	 * @date 2016-4-30
	 */
	public void setF(ArrayList<String> f) {
		F = f;
	}
	/**
	 * 
	 * @return ArrayList
	 * @author Xinyu Ge
	 * @date 2016-4-30
	 */
	public ArrayList<ArrayList> getSigma() {
		return Sigma;
	}
	/**
	 * 
	 * @param varibles_sigmas
	 * @author Xinyu Ge
	 * @date 2016-4-30
	 */
	public void setSigma(ArrayList<ArrayList> varibles_sigmas) {
		Sigma = varibles_sigmas;
	}
	/**
	 * 
	 * @return ArrayList
	 * @author Xinyu Ge
	 * @date 2016-4-30
	 */
	public ArrayList<String> getG() {
		return G;
	}
	/**
	 * 
	 * @param g
	 * @author Xinyu Ge
	 * @date 2016-4-30
	 */
	public void setG(ArrayList<String> g) {
		G = g;
	}
	/**
	 * 
	 * @return String
	 * @author Xinyu Ge
	 * @date 2016-4-30
	 */
	public String getWhere() {
		return where;
	}
	/**
	 * 
	 * @param where
	 * @author Xinyu Ge
	 * @date 2016-4-30
	 */
	public void setWhere(String where) {
		this.where = where;
	}
	@Override
	public String toString() {
		return "EMFVariables [S=" + S + ", N=" + N + ", V=" + V + ", F=" + F
				+ ", Sigma=" + Sigma + ", G=" + G + ", where=" + where + "]";
	}
	/**
	 * 	
	 * 
	 * @param s
	 * @param n
	 * @param v
	 * @param f
	 * @param sigma
	 * @param g
	 * @param where
	 * @author Xinyu Ge
	 * @date 2016-4-30
	 */
	public EMFVariables(ArrayList<String> s, Integer n, ArrayList<String> v,
			ArrayList<String> f, ArrayList<ArrayList> sigma,
			ArrayList<String> g, String where) {
		super();
		S = s;
		N = n;
		V = v;
		F = f;
		Sigma = sigma;
		G = g;
		this.where = where;
	}
	public EMFVariables() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((F == null) ? 0 : F.hashCode());
		result = prime * result + ((G == null) ? 0 : G.hashCode());
		result = prime * result + ((N == null) ? 0 : N.hashCode());
		result = prime * result + ((S == null) ? 0 : S.hashCode());
		result = prime * result + ((Sigma == null) ? 0 : Sigma.hashCode());
		result = prime * result + ((V == null) ? 0 : V.hashCode());
		result = prime * result + ((where == null) ? 0 : where.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EMFVariables other = (EMFVariables) obj;
		if (F == null) {
			if (other.F != null)
				return false;
		} else if (!F.equals(other.F))
			return false;
		if (G == null) {
			if (other.G != null)
				return false;
		} else if (!G.equals(other.G))
			return false;
		if (N == null) {
			if (other.N != null)
				return false;
		} else if (!N.equals(other.N))
			return false;
		if (S == null) {
			if (other.S != null)
				return false;
		} else if (!S.equals(other.S))
			return false;
		if (Sigma == null) {
			if (other.Sigma != null)
				return false;
		} else if (!Sigma.equals(other.Sigma))
			return false;
		if (V == null) {
			if (other.V != null)
				return false;
		} else if (!V.equals(other.V))
			return false;
		if (where == null) {
			if (other.where != null)
				return false;
		} else if (!where.equals(other.where))
			return false;
		return true;
	}
	
	
	

}

