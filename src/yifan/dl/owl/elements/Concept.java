package yifan.dl.owl.elements;

import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;

import yifan.dl.owl.representation.Tableaux;

public class Concept
{
  public String name;
  public OWLClassExpression expression;
  public boolean denied;
  public boolean isExpanded;

  public Concept(){
	  expression=null;
	  isExpanded=false;
  }
  public Concept(OWLClassExpression exp){
	  expression =exp;
	  isExpanded=false;
  }
  public Concept(OWLClassExpression exp,boolean expanded){
	  expression =exp;
	  isExpanded=expanded;
  }  
 
  public boolean isExpanded(){
	  return isExpanded;
  }
  
  public void setExpanded(boolean expand){
	  isExpanded=expand;
  }
  public OWLClassExpression getExpression(){
	  return expression;
	  
  }
  
  public boolean isEqual(Concept concept) {
	// test la egalité de 2 concepts
	String type = expression.getClassExpressionType().toString();
	OWLClassExpression exp=concept.getExpression();
	String type2=exp.getClassExpressionType().toString();
	if(type.equals("Class")&&type2.equals("Class")){
		//System.out.println("class clash");
		
		String x=nameExtraction(expression.toString());
		String y=nameExtraction(exp.toString());
		//System.out.println(x);
		//System.out.println(y);
		if(x.equals(y)){
			//System.out.println(true);
			return true;
		}
		else{
			//System.out.println(false);
			return false;
		}
	}
	else if(type.equals("ObjectComplementOf")&&type2.equals("ObjectComplementOf")){
		OWLObjectComplementOf expComp1=(OWLObjectComplementOf)expression;
		OWLObjectComplementOf expComp2=(OWLObjectComplementOf)exp;
		String x=nameExtraction(expComp1.getOperand().toString());
		String y=nameExtraction(expComp2.getOperand().toString());
		if(x.equals(y))
			return true;
		else
			return false;
	}
	else if(type.equals("ObjectAllValuesFrom")&&type2.equals("ObjectAllValuesFrom")){
		System.out.println("all clash");
		OWLObjectAllValuesFrom allExp1=(OWLObjectAllValuesFrom)expression;
		String filler1=nameExtraction(allExp1.getFiller().toString());
		String role1=nameExtraction(allExp1.getProperty().toString());
		
		OWLObjectAllValuesFrom allExp2=(OWLObjectAllValuesFrom)exp;
		String filler2=nameExtraction(allExp2.getFiller().toString());
		String role2=nameExtraction(allExp2.getProperty().toString());
		
		if(filler1.equals(filler2)&&role1.equals(role2))
			return true;
		else 
			return false;
		

	}
	else if(type.equals("ObjectSomeValuesFrom")&&type2.equals("ObjectSomeValuesFrom")){
		//System.out.println("some clash");
		OWLObjectSomeValuesFrom someExp1=(OWLObjectSomeValuesFrom)expression;
		String filler1=nameExtraction(someExp1.getFiller().toString());
		String role1=nameExtraction(someExp1.getProperty().toString());
		//System.out.println(filler1);
		//System.out.println(role1);
		
		OWLObjectSomeValuesFrom someExp2=(OWLObjectSomeValuesFrom)exp;
		String filler2=nameExtraction(someExp2.getFiller().toString());
		String role2=nameExtraction(someExp2.getProperty().toString());
		//System.out.println(filler2);
		//System.out.println(role2);
		
		
		if(filler1.equals(filler2)&&role1.equals(role2))
			return true;
		else 
			return false;
	}
	else 
		return false;
  }
  
  public boolean isComplementOf(Concept concept) {
	// test le complémentaire d'un concept
	String type = expression.getClassExpressionType().toString();
	OWLClassExpression exp=concept.getExpression();
	String type2=exp.getClassExpressionType().toString();
	if (type.equals("ObjectComplementOf")){
		OWLObjectComplementOf e=(OWLObjectComplementOf)expression;
//		System.out.println("##########################################");
//		System.out.println(e.toString());
//		System.out.println(e.getOperand().toString());
//		System.out.println(exp.toString());
//		System.out.println("##########################################");
		if(type2.equals("Class")){
			String comp=nameExtraction(e.getOperand().toString());
			String cla=nameExtraction(exp.toString());
			if(comp.equals(cla))
				return true;
			else
				return false;
		}
		else
			return false;
	}
	else if(type2.equals("ObjectComplementOf")){
		OWLObjectComplementOf e=(OWLObjectComplementOf)exp;
//		System.out.println("##########################################");
//		System.out.println(e.toString());
//		System.out.println(e.getOperand().toString());
//		System.out.println(expression.toString());
//		System.out.println("##########################################");
		if(type.equals("Class")){
			String comp=nameExtraction(e.getOperand().toString());
			String cla=nameExtraction(expression.toString());
			if(comp.equals(cla))
				return true;
			else
				return false;
		}
		else
			return false;
	}
	else if(type.equals("ObjectAllValuesFrom")){
//			System.out.println("all test");
		if(type2.equals("ObjectSomeValuesFrom")){
			OWLObjectAllValuesFrom allExp=(OWLObjectAllValuesFrom)expression;
			OWLObjectPropertyExpression pExp=allExp.getProperty();
			OWLClassExpression fExp=allExp.getFiller();
			OWLObjectAllValuesFrom someExp=(OWLObjectAllValuesFrom)exp.getComplementNNF();
			OWLObjectPropertyExpression pExp2=someExp.getProperty();
			OWLClassExpression fExp2=someExp.getFiller();
			if(pExp.equals(pExp2)&&fExp.equals(fExp2)){
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	else if(type.equals("ObjectSomeValuesFrom")){
		if(type2.equals("ObjectAllValuesFrom")){
			OWLObjectAllValuesFrom allExp=(OWLObjectAllValuesFrom)expression.getComplementNNF();
			OWLObjectPropertyExpression pExp=allExp.getProperty();
			OWLClassExpression fExp=allExp.getFiller();
			OWLObjectAllValuesFrom someExp=(OWLObjectAllValuesFrom)exp;
			OWLObjectPropertyExpression pExp2=someExp.getProperty();
			OWLClassExpression fExp2=someExp.getFiller();
			if(pExp.equals(pExp2)&&fExp.equals(fExp2)){
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	else
		return false;
  }
  
  
  public boolean isThing(){
	  return expression.isOWLThing();
  }
  public boolean isNoThing(){
	  return expression.isOWLNothing();
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
  
  public String getAbbExpression(){
	  return nameExtraction(expression.toString());
  }
  
  public Concept getNnfComplement(){
//	  Concept c=new Concept(expression.getComplementNNF());
	  Concept c=new Concept(expression.getObjectComplementOf().getNNF());
	  return c;
  }
}