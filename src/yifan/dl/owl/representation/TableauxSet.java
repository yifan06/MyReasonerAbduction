package yifan.dl.owl.representation;

import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;

import yifan.dl.owl.elements.Concept;
import yifan.dl.owl.elements.EdgeLogic;
import yifan.dl.owl.elements.ElementFermeture;
import yifan.dl.owl.elements.EnsembleFermeture;
import yifan.dl.owl.elements.HypoElement;
import yifan.dl.owl.elements.HypotheseAbduction;
import yifan.dl.owl.elements.Noeud;
import yifan.dl.owl.representation.Tableaux;

public class TableauxSet {
/*
 * re-create in 17 may
 */
	public ArrayList<Noeud> tableaux=new ArrayList<Noeud>();
	public ArrayList<EdgeLogic> tabEdge;
	private ArrayList<Noeud> clashInd;
	private ArrayList<String> Hypo=new ArrayList<String>();
	
	private ArrayList<EnsembleFermeture> hyp=new ArrayList<EnsembleFermeture>();
	private HypotheseAbduction res=new HypotheseAbduction();
	
	private HypoElement Hypothese=new HypoElement();
	
	private KB kb;
	private String H="";
	private PelletReasoner pr;
	// create the root node from concept C and D
	public void initRootNoeud(){
		
		Noeud root = new Noeud(1);
		tableaux.add(root);
		
	}
	
	public void setPelletReasoner(PelletReasoner rs){
		pr=rs;
	}
	
	public void addNoeud(Noeud node){
		if(!tableaux.isEmpty()){
			node.setInd(tableaux.size()+1);
			tableaux.add(node);
		}
		else{
			//System.out.println("tableaux is empty, add the node as a root node");
			node.setInd(1);
			tableaux.add(node);
		}
	}
	
	
	public boolean applyRulesInNoeud(){

		boolean change=false;
		int tableauxSize=tableaux.size();
		for(int i=0;i<tableauxSize;i++){
			//&&!tableaux.get(i).isApplied()
			if(!tableaux.get(i).isClash()&& !tableaux.get(i).hasChild()){
				tableaux.get(i).applyUnfolding(kb);
				if(tableaux.get(i).applyRules(tableaux))
					change=true;
				//tableaux.get(i).applyUnfolding(kb);	
				//tableaux.get(i).applyUniversalRules();
			}
		}
		if(!detectClash()){
			System.out.println("tableaux est fermée.");
		}
		return change;
//		return tableaux.size();
		
//		for(int i=0;i<tableaux.size();i++){
//			if(!tableaux.get(i).isApplied()&& !tableaux.get(i).hasChild()){
//				tableaux.get(i).applyUnfolding(kb);
//				tableaux.get(i).applyRules(tableaux);
//				tableaux.get(i).applyUnfolding(kb);
//				
//			}
//			printTableauxSet();
//		}
		
	}
	
	public boolean detectClash(){
		
		boolean flagOuvert=false;
		for(int i=0;i<tableaux.size();i++){
			if(!tableaux.get(i).isClash()&& !tableaux.get(i).hasChild()){
				flagOuvert=true;		
			}
		}
		return flagOuvert;
	}
	
	
	public void checkClash(){
		//cla

		ArrayList<Noeud> childrenRoot=new ArrayList<Noeud>();
		childrenRoot=tableaux.get(0).getLeaves();
		
		
		for(int j=0;j<childrenRoot.size();j++){
//			System.out.println("feuille======");
//			System.out.println(childrenRoot.get(j).getInd());
			if(!childrenRoot.get(j).isClash()){
				// TODO, create a function to get all the hypo possibles
				EnsembleFermeture ens=new EnsembleFermeture();	
				ens.setElement(childrenRoot.get(j).getAllSuccessors());
				ens.setInd(childrenRoot.get(j).getInd());
				ens.removeRedundancy();
				hyp.add(ens);
				res.addHyp(ens);
				//ens.printALL();
			}
		}
		
		for(int i=0;i<hyp.size();i++){
			EnsembleFermeture ens=hyp.get(i);
//			ArrayList<OWLClassExpression> ls=ens.getAllExpression();
//			for(int j=0;j<ls.size();j++){
//				System.out.println(ls.get(j));
//				if(pr.isSatisfiable(ls.get(j))){
//					System.out.println(true);
//				}
//				else
//					System.out.println(false);
//				System.out.println("#########################################");
//			}
			
			
			// test la satisfiablite
			/*
			System.out.println(ens.getIntersectExpression());
			if(pr.isSatisfiable(ens.getIntersectExpression())){
				System.out.println(true);
			}
			else
				System.out.println(false);
			System.out.println("#########################################");
			*/
		}
		
		//getHypothese();
		
		
		
//		for(int i=0;i<tableaux.size();i++){
//			if(!tableaux.get(i).isClash()&& !tableaux.get(i).hasChild()){
//				if(tableaux.get(i).getH().getInd()>0)
//					hyp.add(tableaux.get(i).getH());
//				
//				// TODO, choisir l'ensemble minimume
//				ArrayList<String> h=new ArrayList();
//				h.add(tableaux.get(i).getHypo());//.getHypoList();
//				
//				H=H.concat(tableaux.get(i).getHypo());
//				if(Hypo.isEmpty()){
//					
//					for(int ind=0;ind<h.size();ind++){
//						Hypo.add(h.get(ind));
//					}
//					
//				}
//				else{					
//					for(int ind=0;ind<h.size();ind++){
//						boolean flag=false;
//						for(int j=0;j<Hypo.size();j++){
//							if(h.get(ind).equals(Hypo.get(j))){
//								flag=true;
//								break;
//							}
//						}
//						if(!flag)
//							Hypo.add(h.get(ind));
//					}
//				}
//			}
//		}
	}
	
	// TODO get the hypothesis
	public String getHypothese() {
		
		System.out.println("hypo list ");
//		for(int i=0;i<Hypo.size();i++){
//			System.out.println(Hypo.get(i));
//		}
		

		//res.calculRes();
		
		res.greedy(pr);
		
		ArrayList<OWLClassExpression> results=res.getExplication();
		for(int i=0;i<results.size();i++){
			System.out.println(results.get(i));
		}
		
//		res.greedy();
//		if(pr.isSatisfiable(res.getHypothese())){
//			System.out.println("cohérent résultat");
//		}
//		else
//			System.out.println("no cohérent");
		return H;

//		 KnowledgeBase.isSubClassOf
//		 pr.
//		pr.getKB().isSubClassOf(c1, c2);
		
	}
	
	public void setKB(KB k){
		kb=k;
	}
	
	
	public void printTableauxSet(){

		Iterator iterInd=tableaux.iterator();
		while(iterInd.hasNext())
		{
			System.out.println("**********************************");
			((Noeud)iterInd.next()).printTableaux();
		}
		
		
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return tableaux.size();
	}

	public void unfoldTBox() {
		// TODO Auto-generated method stub
	  HashMap<OWLClassExpression, Concept> axioms=kb.getSubAxiom();	
	  Set<OWLClassExpression> ens=axioms.keySet();
	  Iterator iter=ens.iterator();
	  int size;
      while(iter.hasNext()){
    	  //System.out.println(tableaux.size());
		  OWLClassExpression cl= (OWLClassExpression)iter.next();
		  OWLClassExpression crO= axioms.get(cl).getExpression();
		  String type = crO.getClassExpressionType().toString();
		  OWLClassExpression cr;
		  if(type.equals("ObjectComplementOF")){
			  OWLObjectComplementOf expComp1=(OWLObjectComplementOf)crO;
			  cr=expComp1.getOperand();
		  }
		  else{
			  cr=crO.getComplementNNF();
		  }
		  size=getSize();
    	  for(int j=0;j<size;j++){
    		  if(!tableaux.get(j).hasChild()){
    			  Noeud left=new Noeud(getSize()+1);
    			  Noeud right=new Noeud(getSize()+2);
    			  left.initCopyT(tableaux.get(j).getLabelT());
    			  left.initCopyF(tableaux.get(j).getLabelF());
    			  right.initCopyT(tableaux.get(j).getLabelT());
    			  right.initCopyF(tableaux.get(j).getLabelF());
    			  left.addLabelF(cl);
    			  right.addLabelF(cr);
    			  tableaux.get(j).addChild(left);
    			  tableaux.get(j).addChild(right);
    			  left.addParent(tableaux.get(j));
    			  right.addParent(tableaux.get(j));
    			  tableaux.add(left);
    			  tableaux.add(right);
    		  }
    	  }
    	  
      }
	}
	
	
	
/*
 * end 
 */
	
	
	
	
	
//	
//		private ArrayList<Tableaux> tab;
//		
//		private Map<String,TableauxInd> tabN=new HashMap<String,TableauxInd>();
//		
//		public TableauxSet(){
//			tab=new ArrayList<Tableaux>();
//			
//		}
//		
//		public void add(TableauxInd individual){
//			tabN.put(individual.getName(), individual);
//		}
//		
//		public void add(Tableaux tc){
//			tab.add(tc);
//		}
//		
//		public void applyRules(){
////			Iterator<Tableaux> iter=tab.iterator();
////			while(iter.hasNext())
////			{
////				iter.next().apply();
////			}
//			// tabN.value is java collection search the functionality
//			Collection tabInd=tabN.values();
//			Iterator<TableauxInd> iterInd=tabInd.iterator();
//			while(iterInd.hasNext())
//			{
//				iterInd.next().apply();
//			}
//			
//		}
//		
//		public void applyDisjunction(){
//			Iterator<Tableaux> iter=tab.iterator();
////			while(iter.hasNext())
////			{
////				iter.next().apply();
////			}
//		}
//
//
//		

}
