package yifan.dl.owl.elements;

import java.util.ArrayList;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntologyManager;


public class ElementFermeture {

	private Concept c;
	private ArrayList<Role> r;
	
	public ElementFermeture(){
		c=new Concept();
		r=new ArrayList<Role>();
	}
	
	public ElementFermeture(Concept concept,ArrayList<Role> roles){
		c=concept;
		r=roles;
	}
	
	public void addRole(Role role){
		r.add(role);
	}
	
	public Concept getConcept(){
		return c;
	}
	
	public void setConcept(Concept c){
		this.c=c;
	}
	
	public Role getRole(int i){
		return r.get(i);
	}
	
	public ArrayList<Role> getAllRoles(){
		return r;
	}
	
	public int getRolesSize(){
		return r.size();
	}
	
	public boolean isEqual(ElementFermeture e){
		boolean flag=false;
		int compte=0;
		if(c.isEqual(e.getConcept())){
			if(getRolesSize()==e.getRolesSize()){
				for(int i=0;i<getRolesSize();i++){
					for(int j=0;j<e.getRolesSize();j++){
						if(getRole(i).egale(e.getRole(j).getExpression())){
							compte++;
							break;
						}
					}
				}
				if(compte==getRolesSize())
					flag=true;
			}
		}
		return flag;
	}
	
	public boolean isComplementOf(ElementFermeture e){
		boolean flag=false;
		int compte=0;
		if(c.isComplementOf(e.getConcept())){
			if(getRolesSize()==e.getRolesSize()){
				for(int i=0;i<getRolesSize();i++){
					for(int j=0;j<e.getRolesSize();j++){
						if(getRole(i).egale(e.getRole(j).getExpression())){
							compte++;
							break;
						}
					}
				}
				if(compte==getRolesSize())
					flag=true;
			}
		}
		return flag;
	}
	
	public boolean equals(Object e){
		return isEqual((ElementFermeture)e);//isComplementOf((ElementFermeture)e)||
			
	}

	public void print() {
		// TODO Auto-generated method stub
		for(int i=0;i<r.size();i++){
			System.out.print(r.get(i).getExpression().toString()+"  ");
		}
		System.out.println(c.getExpression().toString());
	}
	
	public OWLClassExpression exportOwlExpression(){
		OWLClassExpression exp = null;
		OWLClassExpression temp=c.getExpression();
	    OWLOntologyManager manager=OWLManager.createOWLOntologyManager();
		OWLDataFactory factory= manager.getOWLDataFactory();
		if(r.isEmpty())
			exp=temp;
		else{
			for(int i=r.size()-1;i>=0;i--){
				exp=(OWLClassExpression)factory.getOWLObjectAllValuesFrom(r.get(i).getExpression(),temp);
				temp=exp;
			}
			
		}

//		System.out.println(exp.toString());
		return exp;
	}
}
