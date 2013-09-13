package yifan.dl.owl.representation;

import yifan.dl.owl.elements.*;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

// un ensemble de concepts satisfiable ou non satisfiable
public class Tableaux {
	// indicateur pour les labels de tableaux
	private boolean indicator;	
	
	// add node for the graph representation and as an element of tableaux
	private Noeud node;
	private String x;
	
	// l'ensemble de l'expression enregistre
	// a supprimer
	@SuppressWarnings("rawtypes")
	private List<OWLClassExpression> ensExpression=new ArrayList<OWLClassExpression>();
	
	private List<Concept> ensConcepts=new ArrayList<Concept>();
	
	
	// les fonctions de tableaux
	
	public Tableaux(){
		x=null;
	}
	
	public Tableaux(String name,boolean ind){
		x=name;
		indicator=ind;
	}
	
	public Tableaux(Tableaux tb){
		
		this.indicator=tb.indicator;
		this.x=tb.x;
		
		for(int i=0;i<tb.ensExpression.size();i++)
		{
			ensExpression.add(tb.getExpression(i));
		}
		
		for(int i=0;i<tb.ensConcepts.size();i++)
		{
			ensConcepts.add(new Concept(tb.getExpression(i),tb.ensConcepts.get(i).isExpanded()));
			//ensExpression.add(tb.getExpression(i));
		}
	}
	
	public void setNode(Noeud node){
		this.node=node;
	}
	
	public Noeud getNode(){
		return node;
	}
	
	public void setIndicator(boolean b){
		indicator=b;
	}
	
	public boolean getIndicator(){
		return indicator;
	}
	
	
	public int getSize(){
		return ensConcepts.size();
	}
	public String getStringExpression(int i){
		return ensConcepts.get(i).getAbbExpression();
	}
	public OWLClassExpression getExpression(int i)
	{
		return ensConcepts.get(i).expression;//ensExpression.get(i);
	}
	
	public void setLabel(boolean flag){
		indicator=flag;
	}
	
	public void copy(Tableaux t){
		indicator=t.getIndicator();
		for(int ind=0;ind<t.getSize();ind++){
			//ensConcepts.add(t.getConcept(ind));
			Concept c=new Concept(t.getConcept(ind).getExpression());
			c.setExpanded(t.getConcept(ind).isExpanded());
			ensConcepts.add(c);
		}
		
	}
	
	public void initCopy(Tableaux t){
		indicator=t.getIndicator();
		for(int ind=0;ind<t.getSize();ind++){
			//ensConcepts.add(t.getConcept(ind));
			Concept c=new Concept(t.getConcept(ind).getExpression());
			//c.setExpanded(false);
			ensConcepts.add(c);
		}
	}
	
	public void setName(String name){
		x=name;
	}
	
	public void addDescription(OWLClassExpression exp) {
		ensExpression.add(exp);
		Concept c=new Concept(exp);
		ensConcepts.add(c);
	}
	
	public void addDescription(Concept concept) {

		ensConcepts.add(concept);
	}
	
	public boolean isClash(){
		//TODO
		boolean clash;
		for(int ind=0;ind<ensConcepts.size();ind++){
			for(int j=ind+1;j<ensConcepts.size();j++){
				clash=ensConcepts.get(ind).isComplementOf(ensConcepts.get(j));
				if(clash)
					return true;
			}
		}
		return false;
	}
	
	public boolean isHomoClashT() {
		// TODO Auto-generated method stub
		for(int ind=0;ind<ensConcepts.size();ind++){
			if(ensConcepts.get(ind).isNoThing())
				return true;
		}
		return isClash();
	}

	public boolean isHomoClashF() {
		// TODO Auto-generated method stub
		for(int ind=0;ind<ensConcepts.size();ind++){
			if(ensConcepts.get(ind).isThing())
				return true;
		}
		return isClash();
	}
	
	public boolean isHeteroClash(Tableaux labelF) {
		// TODO Auto-generated method stub
		// if there is a heterogeneous clash it returns true
		boolean clash;
		for(int ind=0;ind<ensConcepts.size();ind++){
			for(int j=0;j<labelF.getConceptsSize();j++){
				clash=ensConcepts.get(ind).isEqual(labelF.getConcept(j));
				if(clash)
					return true;
			}
		}
		return false;
	}
	
	
	public int getConceptsSize() {
		// TODO Auto-generated method stub
		return ensConcepts.size();
	}
	public Concept getConcept(int i){
		return ensConcepts.get(i);
	}

	public void applyConjunction()
	{
		if(indicator == true)
		{
			//int size=ensExpression.size();
			int size=ensConcepts.size();
			for(int ind=0;ind<ensConcepts.size();ind++)
			{
				if(!ensConcepts.get(ind).isExpanded())
				{
					OWLClassExpression expression=(OWLClassExpression)ensConcepts.get(ind).expression;
					String type = expression.getClassExpressionType().toString();
					if (type.equals("ObjectIntersectionOf")) {
				      Set intersect = expression.asConjunctSet();
				      Iterator iter1 = intersect.iterator();
				      int flag=1;
				      while(iter1.hasNext())
				      {
				    	  OWLClassExpression descrizione=(OWLClassExpression)iter1.next();
				    	  ensExpression.add(descrizione);
				    	  //exploreItem(descrizione, item);
				      }
					}
					ensConcepts.get(ind).setExpanded(true);
				}
			}
		}		
	}
	public void applyConjunction(ArrayList<Tableaux> fc){
		//TODO 
		int size=ensConcepts.size();
		for(int ind=0;ind<ensConcepts.size();ind++)
		{
			if(!ensConcepts.get(ind).isExpanded())
			{
				ensConcepts.get(ind).setExpanded(true);
				OWLClassExpression expression=(OWLClassExpression)ensConcepts.get(ind).expression;
				String type = expression.getClassExpressionType().toString();
				if (type.equals("ObjectIntersectionOf")) {
			      Set intersect = expression.asConjunctSet();
			      Iterator iter1 = intersect.iterator();
			      int flag=1;
			      while(iter1.hasNext())
			      {
			    	  OWLClassExpression descrizione=(OWLClassExpression)iter1.next();
			    	  if(iter1.hasNext())
			    	  {
				    	  Tableaux dis=new Tableaux(this);
				    	  dis.ensExpression.add(descrizione);
				    	  fc.add(dis);
			    	  }
			    	  else
			    		  ensExpression.add(descrizione);
			    	  //exploreItem(descrizione, item);
			      }
				}
				
			}
		}
	}
	
	public void applyDisjunction()
	{
		if(indicator == true)
			System.out.println(" not allow for the nnf form");
		else
		{
			int size=ensExpression.size();
			for(int ind=0;ind<ensExpression.size();ind++)
			{
				OWLClassExpression expression=(OWLClassExpression)ensExpression.get(ind);
				String type = expression.getClassExpressionType().toString();
				if (type.equals("ObjectIntersectionOf")) {
			      Set intersect = expression.asConjunctSet();
			      Iterator iter1 = intersect.iterator();
			      int flag=1;
			      while(iter1.hasNext())
			      {
			    	  OWLClassExpression descrizione=(OWLClassExpression)iter1.next();
			    	  ensExpression.add(descrizione);
			    	  //exploreItem(descrizione, item);
			      }
				}
			}
		}
		
	}
	
	public void applyExistentialQuantification(){
		int size=ensConcepts.size();
		for(int ind=0;ind<ensConcepts.size();ind++)
		{
//			if(!ensConcepts.get(ind).isExpanded())
//			{
				ensConcepts.get(ind).setExpanded(true);
				OWLClassExpression expression=(OWLClassExpression)ensConcepts.get(ind).expression;
				String type = expression.getClassExpressionType().toString();
				if (type.equals("ObjectAllValuesFrom")) {
			      Set intersect = expression.asConjunctSet();
			      Set property=expression.getObjectPropertiesInSignature();
			      Iterator iter1 = property.iterator();
			      int flag=1;
			      while(iter1.hasNext())
			      {
			    	  OWLObjectProperty descrizione=(OWLObjectProperty)iter1.next();
			    	  System.out.println("test ==:"+descrizione);
			    	  //exploreItem(descrizione, item);
			      }
				}
//				
//			}
		}
	}
	
	public boolean applyT(ArrayList<Noeud> fc,Tableaux labelF,ArrayList<EdgeLogic> edges){
			//int size=ensExpression.size();
			boolean change=false;
			
			int size=ensConcepts.size();
			for(int ind=0;ind<ensConcepts.size();ind++)
			{
				if(!ensConcepts.get(ind).isExpanded())
				{
					//TODO if type class apply lazy unfolding
					OWLClassExpression expression=(OWLClassExpression)ensConcepts.get(ind).expression;
					String type = expression.getClassExpressionType().toString();
					if (type.equals("ObjectIntersectionOf")) {
				      Set intersect = expression.asConjunctSet();
				      Iterator iter1 = intersect.iterator();
				      int flag=1;
				      while(iter1.hasNext())
				      {
				    	  OWLClassExpression descrizione=(OWLClassExpression)iter1.next();
				    	  //ensExpression.add(descrizione);
				    	  ensConcepts.add(new Concept(descrizione));
				    	  //exploreItem(descrizione, item);
				      }
				      ensConcepts.get(ind).setExpanded(true);
				      change=true;
					}
					else if(type.equals("ObjectUnionOf")){
					  ensConcepts.get(ind).setExpanded(true);
				      Set intersect = expression.asDisjunctSet();
				      Iterator iter1 = intersect.iterator();
				      int flag=1;
				      while(iter1.hasNext())
				      {
				    	  OWLClassExpression descrizione=(OWLClassExpression)iter1.next();
				    	  Tableaux dis=new Tableaux(this);
				    	  dis.ensConcepts.add(new Concept(descrizione));
				    	  Noeud disNode=new Noeud(fc.size()+1);
				    	  disNode.setLabelT(dis);
				    	  dis.setNode(disNode);
				    	  disNode.copyLabelF(labelF);
				    	  if(edges!=null)
				    		  disNode.setEdges(edges);
				    	  labelF.getNode().addChild(disNode);
				    	  disNode.addParent(labelF.getNode());
				    	  
					      if(!labelF.getNode().isEmptyEtiquette()){
				    		  disNode.copyEtiquette(labelF.getNode().getEtiquette());
				    	  }
					      fc.add(disNode);
				      }

				      ensConcepts.get(ind).setExpanded(true);
				      change=true;
					}
					else if(type.equals("ObjectAllValuesFrom")){
//						System.out.println("all test");
						OWLObjectAllValuesFrom allExp=(OWLObjectAllValuesFrom)expression;
						System.out.println(allExp.getFiller());
						System.out.println(allExp.getProperty());
						OWLObjectPropertyExpression pExp=allExp.getProperty();
						if(edges!=null){
							for(int i=0;i<edges.size();i++){
								if(edges.get(i).egale(pExp)){
									edges.get(i).getSuccessor().getLabelT().addDescription(allExp.getFiller());
								}
							}
						}
						change=true;
					}
					else if(type.equals("ObjectSomeValuesFrom")){
						//System.out.println("T no existence");
						OWLObjectSomeValuesFrom someExp=(OWLObjectSomeValuesFrom)expression;
						Concept c=new Concept(someExp.getFiller());
//						System.out.println(allExp.getFiller());
						Role r=new Role(someExp.getProperty(),c);
//						System.out.println(allExp.getProperty());
						
						// create a new node and add C to labelF
						Noeud y=new Noeud(fc.size()+1);
						Tableaux tab=new Tableaux();
						tab.addDescription(c);
						y.addEtiquette(r);
						y.setLabelT(tab);
						fc.add(y);
						labelF.getNode().addRole(r, y);
						ensConcepts.get(ind).setExpanded(true);
						change=true;
					}
					
				}
			}

			return change;
	}
	
	
	public boolean applyF(ArrayList<Noeud> fc,Tableaux labelT,ArrayList<EdgeLogic> edges){
		
		// l'ordre des règles est important.
		boolean change=false;
		
		int size=ensConcepts.size();
		
		for(int ind=0;ind<ensConcepts.size();ind++)
		{
			if(!ensConcepts.get(ind).isExpanded())
			{
				
				OWLClassExpression expression=(OWLClassExpression)ensConcepts.get(ind).expression;
				String type = expression.getClassExpressionType().toString();
				if(type.equals("ObjectAllValuesFrom")){
					//System.out.println("all test");
					OWLObjectAllValuesFrom allExp=(OWLObjectAllValuesFrom)expression;
					Concept c=new Concept(allExp.getFiller());
//					System.out.println(allExp.getFiller());
					Role r=new Role(allExp.getProperty(),c);
//					System.out.println(allExp.getProperty());
					
					if(edges!=null){
						for(int i=0;i<edges.size();i++){
							if(edges.get(i).egale(allExp.getProperty())){
								edges.get(i).getSuccessor().getLabelF().addDescription(allExp.getFiller());
								Noeud s=edges.get(i).getSuccessor();
								ArrayList<Noeud> sChildren=s.getLeaves();
								for(int j=0;j<sChildren.size();j++){
									sChildren.get(j).getLabelF().addDescription(allExp.getFiller());
								}
							}
						}
					}
					
					
					// create a new node and add C to labelF
					Noeud y=new Noeud(fc.size()+1);
					Tableaux tab=new Tableaux();
					tab.addDescription(c);
					y.addEtiquette(r);
					y.setLabelF(tab);
					fc.add(y);
					labelT.getNode().addRole(r, y);
					ensConcepts.get(ind).setExpanded(true);
					change=true;
				}
			}
			
			
		}
		
		for(int ind=0;ind<ensConcepts.size();ind++)
		{
			if(!ensConcepts.get(ind).isExpanded())
			{
				
				OWLClassExpression expression=(OWLClassExpression)ensConcepts.get(ind).expression;
				String type = expression.getClassExpressionType().toString();
				if(type.equals("ObjectUnionOf"))
				{
					Set intersect = expression.asDisjunctSet();
				    Iterator iter1 = intersect.iterator();
				    int flag=1;
				    while(iter1.hasNext())
				    {
				      OWLClassExpression descrizione=(OWLClassExpression)iter1.next();
				      ensConcepts.add(new Concept(descrizione));
				    }
				    ensConcepts.get(ind).setExpanded(true);
				    change=true;
				}
				else if(type.equals("ObjectSomeValuesFrom")){
					//System.out.println("some test");
					OWLObjectSomeValuesFrom someExp=(OWLObjectSomeValuesFrom)expression;
		//			System.out.println(someExp.getFiller());
		//			System.out.println(someExp.getProperty());
					OWLObjectPropertyExpression pExp=someExp.getProperty();
					if(edges!=null){
						for(int i=0;i<edges.size();i++){
							if(edges.get(i).egale(pExp)){
								edges.get(i).getSuccessor().getLabelF().addDescription(someExp.getFiller());
								Noeud s=edges.get(i).getSuccessor();
								ArrayList<Noeud> sChildren=s.getLeaves();
								for(int j=0;j<sChildren.size();j++){
									sChildren.get(j).getLabelF().addDescription(someExp.getFiller());
								}
								ensConcepts.get(ind).setExpanded(true);
								change=true;
							}
						}
					}
					

				}
			}
		}
				
		
		
		for(int ind=0;ind<ensConcepts.size();ind++)
		{
			if(!ensConcepts.get(ind).isExpanded())
			{
				
				OWLClassExpression expression=(OWLClassExpression)ensConcepts.get(ind).expression;
				String type = expression.getClassExpressionType().toString();
				if (type.equals("ObjectIntersectionOf")) {
				  ensConcepts.get(ind).setExpanded(true);
			      Set intersect = expression.asConjunctSet();
			      Iterator iter1 = intersect.iterator();
			      int flag=1;
			      
			      // get all the disjunction a copy of relation
			      //HashMap<>
			      ArrayList<EdgeLogic> ls=new ArrayList<EdgeLogic>();
			      if(edges!=null){
				      
		    		  for(int j=0;j<edges.size();j++){
		    			  Noeud s=edges.get(j).getSuccessor();
		    			  if(s.hasChild()){
		    				  ArrayList<Noeud> sChildren=s.getLeaves();
		    				  for(int jj=0;jj<sChildren.size();jj++){
		    					  //Noeud c=sChildren.get(jj).createAClone();
		    					  EdgeLogic e=new EdgeLogic(edges.get(j).getAllTRoles(),sChildren.get(jj));
		    					  ls.add(e);
		    					  
		    				  }
		    				  
		    			  }
		    			  else{
		    				  EdgeLogic e=new EdgeLogic(edges.get(j).getAllTRoles(),s);
	    					  ls.add(e);
		    				  
		    			  }
				      }
			      }
	    				  
			      
			      while(iter1.hasNext())
			      {
			    	  OWLClassExpression descrizione=(OWLClassExpression)iter1.next();
			    	  Tableaux dis=new Tableaux(this);
			    	  //dis.ensConcepts.add(new Concept(descrizione));
			    	  dis.addDescription(descrizione);
			    	  Noeud disNode=new Noeud(fc.size()+1);
			    	  disNode.setLabelF(dis);
			    	  dis.setNode(disNode);
			    	  disNode.copyLabelT(labelT);
			    	  if(edges!=null){
			    		 // disNode.setEdges(edges);
			    		  for(int j=0;j<ls.size();j++){
			    			  Noeud s=ls.get(j).getSuccessor();
			    			  Noeud c=s.createAClone();
		    				  c.setInd(fc.size()+1);
		    				  fc.add(c);
	    					  EdgeLogic e=new EdgeLogic(ls.get(j).getAllFRoles(),c);
	    					  disNode.addEdges(e);	    				  
			    	      }			    		  
			    	  }
			    		  
			    	  if(!labelT.getNode().isEmptyEtiquette()){
			    		  disNode.copyEtiquette(labelT.getNode().getEtiquette());
			    	  }
			    	  labelT.getNode().addChild(disNode);
			    	  disNode.addParent(labelT.getNode());
			    	  disNode.setInd(fc.size()+1);
			    	  fc.add(disNode);
			      }
			      ensConcepts.get(ind).setExpanded(true);
			      change=true;
				}				
			}
		}
		return change;
	}
	
	public void printTableaux(){
		//int size=ensExpression.size();
		System.out.println(ensConcepts.size());
		for(int ind=0;ind<ensConcepts.size();ind++)
		{
			
			OWLClassExpression expression=(OWLClassExpression)ensConcepts.get(ind).expression;
			String type = expression.getClassExpressionType().toString();
			System.out.println("type:  "+type);
			System.out.println("ind expression:  "+expression.toString());
			//System.out.println("ind abbreviation expression:  "+ensConcepts.get(ind).getAbbExpression());
		}
	}

	public void applyUnfolding(KB kb) {
		// TODO Auto-generated method stub
		for(int ind=0;ind<ensConcepts.size();ind++)
		{
			//System.out.println(ind);
			//System.out.println(ensConcepts.size());
			OWLClassExpression expression=(OWLClassExpression)ensConcepts.get(ind).expression;
			String type = expression.getClassExpressionType().toString();
			if (type.equals("Class")) {
				OWLClassExpression uexp;
				if(indicator==false){
					uexp=kb.lazyUnfolding(ensConcepts.get(ind).getExpression());
				}
				else{
					uexp=kb.lazyUnfoldingComp(ensConcepts.get(ind).getExpression());
				}
				//System.out.println("unfolding  "+uexp.toString());
				Concept uc=new Concept(uexp);
				if(!uc.isEqual(ensConcepts.get(ind))){
					if(indicator==true){
						node.getLabelF().addDescription(uc.getNnfComplement());
						ensConcepts.remove(ind);
					}
					
					else{
						this.addDescription(uc);
						ensConcepts.remove(ind);
					}
					
				}
					
			}
			else if(type.equals("ObjectComplementOf")){
				OWLObjectComplementOf expComp1=(OWLObjectComplementOf)ensConcepts.get(ind).expression;
				OWLClassExpression exp=expComp1.getOperand();
				String typeFiller= exp.getClassExpressionType().toString();
				if (typeFiller.equals("Class")) {
					OWLClassExpression uexp;
					if(indicator==true){
						uexp=kb.lazyUnfoldingComp(exp);
					}
					else{
//						System.out.println("unfolding ++  "+exp);
//						System.out.println("unfolding -- "+ensConcepts.get(ind).getExpression());
						uexp=kb.lazyUnfolding(exp);
					}
					//System.out.println("unfolding  "+uexp.toString());
					Concept uc=new Concept(uexp);
					if(!uc.isEqual(ensConcepts.get(ind))){
						if(indicator==true){
							node.getLabelF().addDescription(uc);
							ensConcepts.remove(ind);
//							System.out.println("unfolding true  "+uc.getExpression().toString());
						}
						else{
							this.addDescription(uc.getNnfComplement());
							ensConcepts.remove(ind);
//							System.out.println("unfolding false  "+uc.getNnfComplement().getExpression().toString());
						}
					}
				}
			}
		}
		
	}
}
