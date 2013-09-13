package yifan.dl.owl.elements;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLClassExpression;

import yifan.dl.owl.representation.KB;
import yifan.dl.owl.representation.Tableaux;
import yifan.dl.owl.representation.TableauxInd;


/*
 *  Un noeud est une classe pour une interprétation
 *  attributs:
 *  	nom, noeud
 */

public class Noeud {

	//ind représentation d'un modèle
	public int ind;
	
	boolean isApplied=false;
	boolean isClash=false;
	
	private ArrayList<Noeud> parents=new ArrayList<Noeud>();
	private ArrayList<Noeud> enfants=new ArrayList<Noeud>();
	
	// liste des relations;
	private ArrayList<EdgeLogic> edges=null;
	private ArrayList<Role> etiquette=null;
	
	// fonction de label T et F
	public Tableaux labelT;
	public Tableaux labelF;
	
	// function
	public Noeud(){
		ind=1;
		labelT=new Tableaux();
		labelT.setNode(this);
		labelF=new Tableaux();
		labelF.setNode(this);
		edges=new ArrayList<EdgeLogic>();
		etiquette=new ArrayList<Role>();

	}
	public Noeud(int i){
		ind=i;
		labelT=new Tableaux();
		labelT.setNode(this);
		labelF=new Tableaux();
		labelF.setNode(this);
		edges=new ArrayList<EdgeLogic>();
		etiquette=new ArrayList<Role>();
	}
	
	
	public Noeud(Noeud n){
		ind=n.getInd();
		if(!n.getEtiquette().isEmpty()){
			for(int i=0;i<n.getEtiquette().size();i++)
				etiquette.add(n.getEtiquette().get(i));
		}
		if(!n.getEdges().isEmpty()){
			for(int i=0;i<n.getEdges().size();i++)
				edges.add(n.getEdges().get(i));
		}
		parents.add(this);
		
		labelT.copy(n.getLabelT());
		labelF.copy(n.getLabelF());
	}
	
	public Noeud createAClone(){
		Noeud n=new Noeud();
		n.setInd(ind);
		if(!etiquette.isEmpty()){
			for(int i=0;i<etiquette.size();i++)
				n.getEtiquette().add(etiquette.get(i));
		}
		if(!edges.isEmpty()){
			for(int i=0;i<edges.size();i++)
				n.addEdges(edges.get(i));
		}
		n.addParent(this);
		this.addChild(n);
		
		n.initCopyT(getLabelT());
		n.initCopyF(getLabelF());
//		labelT.copy(n.getLabelT());
//		labelF.copy(n.getLabelF());
		return n;
		
	}
	
	public boolean isClash(){
		checkClash();
		return isClash;
	}
	
	public boolean isApplied() {
		return isApplied;
	}
	
	public boolean isEmptyEtiquette(){
		if(etiquette==null){
			return true;
		}
		else
			return false;
	}
	
	public void addEtiquette(Role r){
		if(isEmptyEtiquette())
			etiquette = new ArrayList<Role>();
		etiquette.add(r);
	}
	
	public void addRole(Role r, Noeud y){
		EdgeLogic e=new EdgeLogic(r,y);
		edges.add(e);
		
	}

	public void setInd(int size) {
		ind=size;
	}
	
	public int getInd(){
		return ind;
	}

	
	public boolean applyRules(ArrayList<Noeud> tableaux) {		
		boolean flagT=false;
		boolean flagF=false;
		if(labelT!=null){
			flagT=labelT.applyT(tableaux,labelF,edges);
		}
		
		if(flagT==false){
			flagF=labelF.applyF(tableaux,labelT,edges);
			//labelF.applyF(tableaux);
			isApplied=true;
			// information of applied nodes.
//			System.out.println("test================");
//			System.out.println(ind);
//			labelF.printTableaux();
//			System.out.println(flagF);
//			System.out.println("test================");
			return flagF;
		}
		else
			return flagT;
	}
	

	public void  checkClash(){
		//System.out.println(ind);
		if(labelT.isHomoClashT()){
			isClash=true;
		}
		else if(labelF.isHomoClashF())
			isClash=true;
		else{
			isClash=labelT.isHeteroClash(labelF);
			//System.out.println("heteroClash   "+isClash);
		}
		if(isClash==false){
			if(!edges.isEmpty()){
				for(int i=0;i<edges.size()&&!isClash;i++){
					ArrayList<Noeud> children=edges.get(i).getSuccessor().getLeaves();
					for(int j=0;j<children.size();j++){
						if(children.get(j).isClash()){
							isClash=true;
							break;
						}
					}
				}
			}
		}
			
		
	}
	
	public void setLabelF(Tableaux dis) {
		// TODO Auto-generated method stub
		labelF=dis;
		labelF.setNode(this);
		
	}
	
	public Tableaux getLabelF(){
		return labelF;
	}
	
	public Tableaux getLabelT(){
		return labelT;
	}
	
	public void copyLabelT(Tableaux dis){
		labelT.copy(dis);
		labelT.setNode(this);
	}
	
	public void initCopyT(Tableaux dis){
		labelT.initCopy(dis);
		labelT.setNode(this);
	}
	
	public void copyLabelF(Tableaux dis){
		labelF.copy(dis);
		labelF.setNode(this);
	}
	
	public void initCopyF(Tableaux dis){
		labelF.initCopy(dis);
		labelF.setNode(this);
	}
	
	public void setLabelT(Tableaux dis){
		labelT=dis;
		labelT.setNode(this);
	}
	public void printTableaux(){
		System.out.println("Noeud:"+ind);
		if(!parents.isEmpty()){
			System.out.println("Parent Noeud:"+parents.get(0).getInd());
		}
		if(!enfants.isEmpty()){
			for(int i=0;i<enfants.size();i++)
				System.out.println("Children Noeud:"+enfants.get(i).getInd());
		}
		System.out.println("labelT:");
		if(labelT!=null)
			labelT.printTableaux();
		else
			System.out.println("null.");
		System.out.println("labelF:");
		labelF.printTableaux();
		
		if(etiquette!=null){
			if(!etiquette.isEmpty()){
				for(int i=0;i<etiquette.size();i++){
					System.out.println("etiquette : "+etiquette.get(i).getExpression().toString());
				}
			}
		}
		
		if(edges!=null){
			if(!edges.isEmpty()){
				for(int i=0;i<edges.size();i++){
					System.out.println("relation : "+edges.get(i).getSuccessor().getInd());
				}
			}
		}
		System.out.println("clash:"+isClash);
		
	}

	public void setEdges(ArrayList<EdgeLogic> edges2) {
		// TODO Auto-generated method stub
		for(int ind=0;ind<edges2.size();ind++)
			edges.add(edges2.get(ind));
		
	}
	
	public void addEdges(EdgeLogic e) {
		// TODO Auto-generated method stub
		edges.add(e);
		
	}
	
	public ArrayList<EdgeLogic> getEdges(){
		return edges;
	}
	
	public String getHypo(){
		String rSeq="";
		String h="";
//		if(!edges.isEmpty()){
//			for(int ind=0;ind<edges.size();ind++){
//				rSeq=rSeq.concat(edges.get(ind).getExpression()+" only ");
//			}
//		}
		
		if(edges!=null){
			if(etiquette!=null){
				for(int ind=0;ind<etiquette.size();ind++){
					rSeq=rSeq.concat(etiquette.get(ind).getExpression().toString()+" only ");
				}
			}
			System.out.println("rseq" +rSeq);
			for(int ind=0;ind<labelF.getSize();ind++){
				if(!labelF.getConcept(ind).isExpanded())
					h=h.concat(rSeq+labelF.getExpression(ind).toString()+" and ");
//					h=h.concat(rSeq+labelF.getStringExpression(ind)+" and ");
			}
			System.out.println("h" +h);
		}
		return h;
	
	}
	
	
	public EnsembleFermeture getH(){
		ArrayList<Role> roles=new ArrayList<Role>();
		if(!etiquette.isEmpty()){
			for(int i=0;i<etiquette.size();i++){
				roles.add(etiquette.get(i));
			}
		}
		EnsembleFermeture ens=new EnsembleFermeture();
		for(int j=0;j<labelF.getSize();j++){
			if(!labelF.getConcept(j).isExpanded()){
				ElementFermeture e=new ElementFermeture(labelF.getConcept(j),roles);
				ens.addElementFermeture(e);
			}
//			String type = labelF.getConcept(j).getExpression().getClassExpressionType().toString();
//			if(type.equals("ObjectComplementOf")||type.equals("Class")){
//			if(!labelF.getConcept(j).isExpanded()){
//				ElementFermeture e=new ElementFermeture(labelF.getConcept(j),roles);
//				ens.addElementFermeture(e);
			//}
		}
		if(ens.getElementSize()>0)
			ens.setInd(ind);
		return ens;
	}
	
	
	public ArrayList<String> getHypoRoleList(){
		ArrayList<String> l=new ArrayList();
		if(etiquette!=null){
			for(int ind=0;ind<etiquette.size();ind++){
				l.add(etiquette.get(ind).getExpression().toString());
			}
		}
		return l;
	}
	
	public ArrayList<String> getHypoList(){
		ArrayList<String> l=new ArrayList();
		String h="";
		for(int ind=0;ind<labelF.getSize();ind++){
			if(!labelF.getConcept(ind).isExpanded()){
				h=labelF.getExpression(ind).toString();
				for(int j=0;j<l.size();j++){
					//if(!h.equals(l.get(j)))
					l.add(h);
						//l.add(labelF.getExpression(ind).toString());
				}
				
//				h=h.concat(rSeq+labelF.getStringExpression(ind)+" and ");
			}
		}
		return l;
	}
	
	
	public void applyUnfolding(KB kb) {
		// TODO Auto-generated method stub
		labelT.applyUnfolding(kb);
		labelF.applyUnfolding(kb);
		
	}
	
	public void addChild(Noeud child){
		enfants.add(child);
	}
	
	public void addParent(Noeud parent){
		parents.add(parent);
	}
	public boolean hasChild() {
		if(enfants.isEmpty())
			return false;
		else
			return true;
	}
	
	
	/* l'ensemble de feuilles d'un noeud */  
    public ArrayList<Noeud> getLeaves() {  
    	ArrayList<Noeud> leavesList = new ArrayList<Noeud>();  
    	ArrayList<Noeud> childList = this.getChild();  
        if (childList.isEmpty()) {
        	leavesList.add(this);
            return leavesList;  
        } else {  
            int childNumber = childList.size();  
            for (int i = 0; i < childNumber; i++) {  
            	Noeud junior = childList.get(i);
            	if(!junior.hasChild())
            		leavesList.add(junior);  
                leavesList.addAll(junior.getLeaves());  
            }  
            return leavesList;  
        }  
    }  
    
	public ArrayList<ElementFermeture> getAllSuccessors() {
		// TODO Auto-generated method stub
		ArrayList<ElementFermeture> hypoList=new ArrayList<ElementFermeture>();
		if(!edges.isEmpty()){
			for(int i=0;i<edges.size();i++){
				ArrayList<Noeud> children=edges.get(i).getSuccessor().getLeaves();
				
				for(int k=0;k<children.size();k++){
					ArrayList<ElementFermeture> e=children.get(k).getAllSuccessors();
					for(int j=0;j<e.size();j++){
						hypoList.add(e.get(j));
					}					
				}
			}
		}
		EnsembleFermeture ens=getH();
		for(int k=0;k<ens.getElementSize();k++){
			hypoList.add(ens.getElementFermeture(k));
		}
		
		return hypoList;
	}
    
    
    
    
	
	
	
	public void addLabelF(OWLClassExpression exp) {
		// TODO Auto-generated method stub
//		
		labelF.addDescription(exp);
	}
	public ArrayList<Role> getEtiquette() {
		// TODO Auto-generated method stub
		return etiquette;
	}
	public void copyEtiquette(ArrayList<Role> etiquette2) {
		// TODO Auto-generated method stub
		//etiquette=etiquette2;
		if(!etiquette2.isEmpty()){
			for(int i=0;i<etiquette2.size();i++){
				etiquette.add(etiquette2.get(i));
			}
		}
		
	}
	public ArrayList<Noeud> getChild() {
		// TODO Auto-generated method stub
		return enfants;
		
	}



}
