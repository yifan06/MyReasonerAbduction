import yifan.dl.owl.reasoner.SvReasoner;


import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.mindswap.pellet.KnowledgeBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;


public class TestAbduction {

	    // ontology path
		static String ONTO_DIR = "./dataset/abduction_contraction/CerebralExemple.owl";
		
		static OWLOntology onto = null;
		static OWLOntology onto_restric=null;
		static OWLOntology onto_demand = null;
		
		static SvReasoner reasoner = null;
		
		public static void main(String args[]){
			
			OWLOntologyManager manager=OWLManager.createOWLOntologyManager();
			File file = new File(ONTO_DIR);
			try{
				
				// test to get signature
				onto = manager.loadOntologyFromOntologyDocument(file);
                Set superClasses;
                Set subClasses;
				for (OWLClass cls : onto.getClassesInSignature()) {					
					      superClasses = cls.getSuperClasses(onto);
					      subClasses=cls.getSubClasses(onto);
//					      System.out.println(cls+" subclass "+subClasses);
				}
				
				// init reasoner
				reasoner = new SvReasoner(onto);
						
				
				//Init tableau T(x) F(x)
				reasoner.testInit(onto);
						        
		        // start new abduction by construct a new tableau tree
				// and get an hypothesis of explanation
		        reasoner.nvAbduction();
		        
		        System.out.println("=========================================");
		        reasoner.PrintTableauxSet();
		        System.out.println("=========================================");
		        
					
			}catch(Exception e){
				e.printStackTrace();
			}
		}
}

/*  
 * 读取ontology 并删除 prefix
 * 	OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new File("aaaaaa.owl"));
	// Obtaining the location where an ontology was loaded from
	IRI documentIRI = manager.getOntologyDocumentIRI(ontology);
	System.out.println(" from: " + documentIRI);
	//extracting the list of all axioms
	Set ax = ontology.getAxioms();
	// Removing the prefix from the text
	Pattern pattern = Pattern.compile("http://(.+?)#");
	Iterator itlist= ax.iterator();
	while(itlist.hasNext()){
	String result=itlist.next().toString();
	//Removing the prefix
	Matcher matcherx = pattern.matcher(result);
	String temp=matcherx.replaceAll("");
	System.out.println(temp);
 * */



/*
 * //Initiate everything
OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
String base = "http://www.example.org/";
OWLOntology ontology = manager.createOntology(IRI.create(base + "ontology.owl"));
OWLDataFactory factory = manager.getOWLDataFactory();
//Add the stuff to the ontology
OWLDataProperty hasName = factory.getOWLDataProperty(IRI.create(base + "hasName"));
OWLNamedIndividual john = factory.getOWLNamedIndividual(IRI.create(base + "john"));
OWLLiteral lit = factory.getOWLLiteral("John");
OWLDataPropertyAssertionAxiom ax = 
                  factory.getOWLDataPropertyAssertionAxiom(hasName, john, lit);
AddAxiom addAx = new AddAxiom(ontology, ax);
manager.applyChange(addAx);

//Init of the reasoner
//I use Hermit because it supports the construct of interest
OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
OWLReasoner reasoner = reasonerFactory.createReasoner(ontology);
reasoner.precomputeInferences();

//Prepare the expression for the query
OWLDataProperty p = factory.getOWLDataProperty(IRI.create(base + "hasName"));
OWLClassExpression ex = 
                factory.getOWLDataHasValue(p, factory.getOWLLiteral("John"));

//Print out the results, John is inside
Set<OWLNamedIndividual> result = reasoner.getInstances(ex, true).getFlattened();        
for (OWLNamedIndividual owlNamedIndividual : result) {
    System.out.println(owlNamedIndividual);
}
*/
