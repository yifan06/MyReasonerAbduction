package yifan.dl.owl.representation;

import yifan.dl.owl.representation.Tableaux;

import java.util.ArrayList;

import org.semanticweb.owlapi.model.OWLClassExpression;

// ancient classe
public class TableauxInd {

	private ArrayList<Tableaux> tc;
	private ArrayList<Tableaux> fc;
	private ArrayList<Tableaux> tr;
	private ArrayList<Tableaux> fr;
	
	private String ind;
	
	public TableauxInd(){
		
		tc=new ArrayList<Tableaux>();
		fc=new ArrayList<Tableaux>();
		tr=new ArrayList<Tableaux>();
		fr=new ArrayList<Tableaux>();
		tc.add(new Tableaux());
		fc.add(new Tableaux());
		tr.add(new Tableaux());
		fr.add(new Tableaux());
		
	}
	
	public TableauxInd(String ind){
		this.ind=ind;
		
		tc=new ArrayList<Tableaux>();
		fc=new ArrayList<Tableaux>();
		tr=new ArrayList<Tableaux>();
		fr=new ArrayList<Tableaux>();
//		tc.add(new Tableaux(ind,true,true));
//		fc.add(new Tableaux(ind,false,true));
//		tr.add(new Tableaux(ind,true,false));
//		fr.add(new Tableaux(ind,false,false));
		
	}
	
	public void setName(String name)
	{
		for(int i=0;i<tc.size();i++)
			tc.get(i).setName(name);
		for(int i=0;i<fc.size();i++)
			fc.get(i).setName(name);
		for(int i=0;i<tr.size();i++)
			tr.get(i).setName(name);
		for(int i=0;i<fr.size();i++)
			fr.get(i).setName(name);

		
	}
	
	public String getName(){
		return ind;
	}
	
	public void addExpressionTC(OWLClassExpression expNnf){
		// init the first tableaux
		tc.get(0).addDescription(expNnf);
	}
	
	public void addExpressionFC(OWLClassExpression expNnf){
		
		fc.get(0).addDescription(expNnf);
	}
	
	public void apply(){
//		for(int i=0;i<tc.size();i++)
////			tc.get(i).applyT();
//			//tc.get(i).applyConjunction();
//		for(int i=0;i<fc.size();i++)
//		{
//			//fc.get(i).applyF(fc);
//			//fc.get(i).
//			fc.get(i).applyExistentialQuantification();
//			//fc.get(i).applyConjunction(fc);
//			//fc.get(i).applyDisjunction();
////			fc.get(i).applyConjunction();
////			fc.get(i).applyDisjunction();
//		}
//			
////		tc.applyConjunction();
////		fc.applyConjunction();
////		fc.applyDisjunction();
	}
	
	public void printTableaux(){
		
		 for(int i=0;i<fc.size();i++)
		 {
			 System.out.println("**********************************");
			 fc.get(i).printTableaux();
			 System.out.println("**********************************");
		 }
	}
	
	
}
