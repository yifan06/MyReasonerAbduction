package yifan.dl.owl.reasoner;

import yifan.dl.owl.elements.Concept;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.mindswap.pellet.KnowledgeBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

import yifan.dl.owl.representation.KB;
import yifan.dl.owl.representation.Tableaux;
import yifan.dl.owl.representation.TableauxInd;
import yifan.dl.owl.representation.TableauxSet;
import yifan.dl.owl.elements.Noeud;

/*
 * this part is written by yifan 
 */
public class SvReasoner
{
    OWLOntology C=null;
    OWLOntology D=null;
//    HashMap<Concept,Concept> axioms;
    KB kb;
    Concept contraint;
    Concept observation;
    String H="";
    
    private ArrayList<String> defClasses;
    private TableauxSet ts;
    OWLReasoner owlReasoner;
    OWLOntologyManager manager;
	OWLDataFactory factory; 
	PelletReasoner rs;
//  rs.isSatisfiable(classExpression);
//	factory.getOWLDataHasValue(arg0, arg1);
	KnowledgeBase pelletKb;
    
    public SvReasoner() {
    manager=OWLManager.createOWLOntologyManager();
    factory= manager.getOWLDataFactory();
	kb=new KB();
	ts=new TableauxSet();
    }
    
    public SvReasoner(OWLOntology rootOntology) {
    	kb=new KB();
    	ts=new TableauxSet();
    	loadKB(rootOntology);
    	ts.setKB(kb);
        manager=OWLManager.createOWLOntologyManager();
        factory= manager.getOWLDataFactory();
        rs= PelletReasonerFactory.getInstance().createReasoner( rootOntology );
        pelletKb=rs.getKB();
        ts.setPelletReasoner(rs);
        
    }
    
    public void nvAbduction(){
    	// get a complete tableau
    	tableauxApproach();
    	
    }
    
    public void PrintTableauxSet(){
    	//printTableauxSet();
    	
    	getHypothesis();
    	
    	//System.out.println("H: "+getHypothesis());
    }
    
    
    public void normalizeOntology(OWLOntology rootOntology) {
  	// charger le TBox
  	 loadKB(rootOntology);
  	 ts.setKB(kb);
    }

    public KB getKB(OWLOntology onto){
  	  	return kb;
    }
    
    @SuppressWarnings("null")
    public void loadKB(OWLOntology onto){
  	  // done get all the axioms in TBOX and put in to kb
  	  HashMap<OWLClassExpression,Concept>equivAxioms =new HashMap();
  	  HashMap<OWLClassExpression,Concept>subAxioms =new HashMap();
  	  
  	  //System.out.println(onto.getAxioms().size());
  	  
  	  //System.out.println(onto.getAxioms(AxiomType.EQUIVALENT_CLASSES).size());
  	  Iterator iterAssAxiom=onto.getAxioms(AxiomType.EQUIVALENT_CLASSES).iterator();
  	  while(iterAssAxiom.hasNext()){
  		  OWLEquivalentClassesAxiom axiom=(OWLEquivalentClassesAxiom)iterAssAxiom.next();
  		  if(axiom.getClassExpressionsAsList().size()==2){
  			  //Concept left=new Concept(axiom.getClassExpressionsAsList().get(0));
  			  OWLClassExpression left=axiom.getClassExpressionsAsList().get(0);
  			  Concept right=new Concept(axiom.getClassExpressionsAsList().get(1));
  			  equivAxioms.put(left, right); 
  		  }
  		  else
  			  System.out.println("axioms no correct");
  		  //System.out.println(axiom.getClassExpressions());
  	  }
  	  //System.out.println(onto.getAxioms(AxiomType.SUBCLASS_OF).size());
  	  Iterator iterSubAxiom=onto.getAxioms(AxiomType.SUBCLASS_OF).iterator();
  	  while(iterSubAxiom.hasNext()){
  		  OWLSubClassOfAxiom axiom=(OWLSubClassOfAxiom)iterSubAxiom.next();
  		  //Concept left=new Concept(axiom.getSubClass());
  		OWLClassExpression left=axiom.getSubClass();
  		  Concept right=new Concept(axiom.getSuperClass());
  		  subAxioms.put(left, right); 

  	  }
  	  kb.setEquivAxiom(equivAxioms);
  	  kb.setSubAxiom(subAxioms);
  	  
  	  ArrayList<Concept> concepts = new ArrayList<Concept>();
  	  Iterator iterClass=onto.getClassesInSignature().iterator();
  	  while(iterClass.hasNext()){
  		  //System.out.println((OWLClassExpression)iterClass.next());
  		  Concept concept=new Concept((OWLClassExpression)iterClass.next());
  		  //OWLClassExpression exp=(OWLClassExpression)iterClass.next();
  		  concepts.add(concept);
  	  }
  	  kb.setConcepts (concepts);
  	  
  	System.out.println(" La connaissance est chargée.");
//  	  System.out.println(onto.getClassesInSignature().size());
//  	  Iterator iterClass=onto.getClassesInSignature().iterator();
//  	  while(iterClass.hasNext()){
//  		  System.out.println(iterClass.next().toString());
  	  
    }
    
    // initiate the root noeud with the concept C and D
    // and add all the axioms( implication) in the label D 
    public void testInit(OWLOntology onto){
    
  	  Noeud root=new Noeud(1);
  	  Set individuals = onto.getIndividualsInSignature();
      Iterator iter1 = individuals.iterator();
      while(iter1.hasNext())
      {    	 
     	  
      	  OWLNamedIndividual ind=(OWLNamedIndividual)iter1.next();
      	  
          Set express = ind.getTypes(onto);
          Tableaux label=new Tableaux();
          for (Iterator desc = express.iterator(); ((Iterator)desc).hasNext(); ) { 
        	  OWLClassExpression exp = (OWLClassExpression)((Iterator)desc).next();
        	  OWLClassExpression expNnf=exp.getNNF();
        	  //System.out.println("**ind expression:  "+expNnf.toString());
        	  Concept c =new Concept(expNnf);
        	  label.addDescription(c);
          }
          String name = nameExtraction(ind.asOWLNamedIndividual().getIRI().toString());
          if(name.equals("C")){
        	  label.setIndicator(true);
              root.setLabelT(label);  
              System.out.println("=========================================");
              System.out.println("La contrainte:");
              label.printTableaux();
              System.out.println("=========================================");
          }

          if(name.equals("D")){
        	  label.setIndicator(false);
              root.setLabelF(label);
              System.out.println("=========================================");
              System.out.println("L'observation:");
              label.printTableaux();
              System.out.println("=========================================");
          }
      }
      // ajouter all the axioms of inclusion to the label F
      ts.addNoeud(root);
      ts.unfoldTBox();
    }
    
    
    public void tableauxApproach(){
  	  //ts.applyRules();
      int size=0;
      int nvSize=1;
      // TODO: modify the condition of termination
//      while(size!=nvSize){
//    	  size=nvSize;
//    	  nvSize=ts.applyRulesInNoeud();
//      }
      
      boolean change=true;
      while(change){
    	  change=ts.applyRulesInNoeud();
      }
      
  	  //ts.applyRulesInNoeud();
  	  ts.checkClash();
  	
    }
    
    public String getHypothesis(){
  	  
  	  return ts.getHypothese();
    }
    
    
    public void printTableauxSet(){
  	  ts.printTableauxSet();
    }
    
  	public static String nameExtraction(String name)
  	{
  	//  if (name.contains("Thing")) {
  	//    return name;
  	//  }
  	  int posizione = name.indexOf("#");
  	  String nom = name.substring(posizione + 1, name.length());
  	  return nom;
  	}
}

/*
* end
* */

