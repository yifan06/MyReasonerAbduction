package yifan.dl.owl.elements;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

public class Role {

	OWLClassExpression role;
	OWLObjectPropertyExpression role1;
	Concept target;

	
	public Role(OWLObjectPropertyExpression owlObjectPropertyExpression) {
		// TODO Auto-generated constructor stub
		role=(OWLClassExpression ) owlObjectPropertyExpression;
	}
	
	public Role(OWLObjectPropertyExpression owlObjectPropertyExpression, Concept concept){
//		role=(OWLClassExpression ) owlObjectPropertyExpression;
		role1=owlObjectPropertyExpression;
		target=concept;
	}

	public boolean egale(OWLObjectPropertyExpression e) {
		// TODO Auto-generated method stub
		//if(role1.compareTo(e)==0)
		if(role1.toString().equals(e.toString()))
			return true;
		else 
			return false;
	}
	
	public OWLObjectPropertyExpression getExpression(){
		return role1;
	}
}
