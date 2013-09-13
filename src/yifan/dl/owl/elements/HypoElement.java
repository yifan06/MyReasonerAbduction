package yifan.dl.owl.elements;

import java.util.ArrayList;

public class HypoElement {

	private ArrayList<String> roles;
	private ArrayList<String> concepts;
	
	public HypoElement(){
		roles=new ArrayList<String>();
		concepts=new ArrayList<String>();
	}
	
	public void addRoles(String r){
		roles.add(r);
	}
	
	public String getRoles(int i){
		return roles.get(i);
	}
	
	public void addConcepts(String c){
		concepts.add(c);
	}
	
	public String getConcept(int i){
		return concepts.get(i);
	}
}
