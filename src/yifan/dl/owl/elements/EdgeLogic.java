package yifan.dl.owl.elements;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;


public class EdgeLogic {
	//private Noeud p;
	private Noeud s;
	private ArrayList<Role> labelT;
	private ArrayList<Role> labelF;
	
	
	private OWLClassExpression expression;
	
	public EdgeLogic(){
		labelT=new ArrayList<Role>();
		labelF=new ArrayList<Role>();
		
	}
	
	public EdgeLogic(Role r, Noeud y){
		labelT=new ArrayList<Role>();
		labelF=new ArrayList<Role>();
		labelT.add(r);
		s=y;
	}
	
	public EdgeLogic( ArrayList<Role> r, Noeud y){
		labelT=new ArrayList<Role>();
		labelF=new ArrayList<Role>();
		for(int i=0;i<r.size();i++)
			labelT.add(r.get(i));
		s=y;
	}
	
	public boolean egale(OWLObjectPropertyExpression e){
		for(int ind=0;ind<labelT.size();ind++){
			if(labelT.get(ind).egale(e))
				return true;
		}
		
		return false;
	}
	
	public ArrayList<Role> getAllTRoles(){
		return labelT;
	}
	
	public ArrayList<Role> getAllFRoles(){
		return labelT;
	}
	
	
	public Role getRole(int i) {
		// TODO Auto-generated method stub
		return labelT.get(i);
	}
	
	public Noeud getSuccessor(){
		return s;
	}
	
	public String getExpression(){
		return nameExtraction(expression.toString());
	}
	
	public static String nameExtraction(String name)
	  {
	    if (name.contains("Thing")) {
	      return name;
	    }
	    int posSignStart = name.indexOf("#");
	    int posSignFinish= name.indexOf(">");
	    String nome = name.substring(posSignStart + 1, posSignFinish);
	    return nome;
	  }

}
