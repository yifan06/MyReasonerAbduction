package yifan.dl.owl.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

import org.semanticweb.owlapi.model.OWLClassExpression;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;


public class HypotheseAbduction {

	private ArrayList<EnsembleFermeture> hyp;
	private Set<ElementFermeture> temp1;

	private ArrayList<ElementFermeture> res;
	private EnsembleFermeture resCoherence;
	private ArrayList<ElementFermeture> temp;
	private ArrayList<EnsembleFermeture> result;
	
	private HashMap<ElementFermeture,Integer> union;
	                                                                     
	public HypotheseAbduction(){
		hyp=new ArrayList<EnsembleFermeture>();
		res=new ArrayList<ElementFermeture>();
		resCoherence= new EnsembleFermeture();
		temp1=new CopyOnWriteArraySet<ElementFermeture>();
		temp=new ArrayList<ElementFermeture>();
		union=new HashMap<ElementFermeture,Integer>();
		result=new ArrayList<EnsembleFermeture>();
	}
	
	public void  setHyp(ArrayList<EnsembleFermeture> h){
		hyp=h;
	}
	
	public void  addHyp(EnsembleFermeture h){
		hyp.add(h);
	} 
	
	public ArrayList<ElementFermeture> getRes(){
		//return res;
		return resCoherence.getAllElement();
	}
	
	public void minimumSet(){
		

		
	}
	
	public void removeRedundancy(ArrayList<ElementFermeture> r){
		for(int i=0;i<r.size();i++){
			for(int j=i;j<r.size();j++){
				if(r.get(i).isEqual(r.get(j)))
					r.remove(j);			
			}
		}
	}

	public void removeComplement(ArrayList<ElementFermeture> r) {
		// TODO Auto-generated method stub
		for(int i=0;i<r.size();i++){
			for(int j=i;j<r.size();j++){
				if(r.get(i).isComplementOf(r.get(j)))
					r.remove(j);			
			}
		}
	}
	
	
	
	public void calculRes(){
		for(int i=0;i<hyp.size();i++){
			Set<ElementFermeture> temp2=new CopyOnWriteArraySet<ElementFermeture>();
			for(int j=0;j<hyp.get(i).getElementSize();j++){
	//				temp.add(hyp.get(i).getElementFermeture(j));
				temp2.add(hyp.get(i).getElementFermeture(j));
			}
			temp1.addAll(temp2);
	//			res.addAll(temp);
		}
	//		removeRedundancy(res);
	//		removeComplement(res);
	//		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
	//		for(int i=0;i<res.size();i++){
	//			res.get(i).print();
	//			
	//		}
	//		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		System.out.println("+++++++++++++++++++++++x++++++++++++++++++++++++++");
		Iterator<ElementFermeture> iter=temp1.iterator();
		while(iter.hasNext()){
			iter.next().print();
		}
		System.out.println("+++++++++++++++++++++++x++++++++++++++++++++++++++");
		
	}
	
	public void greedy(){
		// calculate the union of all the element to close the tableaux
				for(int i=0;i<hyp.size();i++){
					Set<ElementFermeture> temp2=new CopyOnWriteArraySet<ElementFermeture>();
					for(int j=0;j<hyp.get(i).getElementSize();j++){
	//						temp.add(hyp.get(i).getElementFermeture(j));
						temp2.add(hyp.get(i).getElementFermeture(j));
					}
					temp1.addAll(temp2);
	//					res.addAll(temp);
				}
				
				Iterator<ElementFermeture> iter=temp1.iterator();
				while(iter.hasNext()){
					ElementFermeture e=iter.next();
					//e.print();
					Integer k=0;
					for(int i=0;i<hyp.size();i++){
						if(hyp.get(i).contains(e))
							k++;
					}
					//System.out.println(k);
					//ElementFermeture e=iter.next();
					union.put(e, k);
				}
				
				Set<ElementFermeture> key=union.keySet();
				Iterator it=key.iterator();
				while(it.hasNext()){
					ElementFermeture l=(ElementFermeture)it.next();
					//l.print();
					//System.out.println(union.get(l));
				}
				
				
				List<Map.Entry<ElementFermeture, Integer>> infoIds =
					    new ArrayList<Map.Entry<ElementFermeture, Integer>>(union.entrySet());
				
				for (int i = 0; i < infoIds.size(); i++) {
				    infoIds.get(i).getKey().print();
				    //System.out.println(infoIds.get(i).getValue());
				}
				
				Collections.sort(infoIds, new Comparator<Map.Entry<ElementFermeture, Integer>>() {   
				    public int compare(Map.Entry<ElementFermeture, Integer> o1, Map.Entry<ElementFermeture, Integer> o2) {      
				        return (o2.getValue() - o1.getValue()); 
				        //return (o1.getKey()).toString().compareTo(o2.getKey());
				    }
				}); 
		 
				for (int i = 0; i < infoIds.size(); i++) {
				    //String id = infoIds.get(i).toString();
				    infoIds.get(i).getKey().print();
				    //System.out.println(infoIds.get(i).getValue());
				}
				 
				ArrayList<EnsembleFermeture> hypClone=(ArrayList<EnsembleFermeture>) hyp.clone();
				for (int i = 0; i < infoIds.size(); i++) {
					ElementFermeture l=infoIds.get(i).getKey();
					//l.print();
					for(int j = hypClone.size() - 1; j >= 0; j--){
				    	if(hypClone.get(j).contains(l)){
				    		hypClone.remove(j);
				    		//i=i-1;
				    	}
				    		
				    }
					//System.out.println(res.size());
					//System.out.println(resCoherence.getElementSize());
					for (int ind = 0; ind < res.size(); ind++) {
						res.get(ind).print();
						resCoherence.getElementFermeture(ind).print();
					}
				    res.add(infoIds.get(i).getKey());
				    resCoherence.addElementFermeture(infoIds.get(i).getKey());
				    if(hypClone.isEmpty())
				    	break;
				}
				 
				//System.out.println(res.size());
				//System.out.println(resCoherence.getElementSize());
				for (int i = 0; i < res.size(); i++) {
					res.get(i).print();
					resCoherence.getElementFermeture(i).print();
				}
				
				//print all the hyp possible
				System.out.println("Les hypothèses potentielles");
				for(int i=0;i<hyp.size();i++){
					hyp.get(i).printALL();
				}
		
	}

	public void greedy( PelletReasoner pr){
		ArrayList<EnsembleFermeture> t1=new ArrayList<EnsembleFermeture>();
		ArrayList<EnsembleFermeture> t2=new ArrayList<EnsembleFermeture>();
		
		for(int i=0;i<hyp.size();i++){
			if(i==0){
				for(int x=0;x<hyp.get(0).getElementSize();x++){
					EnsembleFermeture e=new EnsembleFermeture();
					e.addElementFermeture(hyp.get(0).getElementFermeture(x));
					t1.add(e);
				}		
			}
			else{
				int y=hyp.get(i).getElementSize();
				for(int j=0;j<y;j++){
					for(int ti=0;ti<t1.size();ti++){
						EnsembleFermeture e=new EnsembleFermeture(t1.get(ti));
						e.addElementFermeture(hyp.get(i).getElementFermeture(j));
						e.removeRedundancy();
						t2.add(e);
					}	
				}
				t1.clear();
				for(int z=0;z<t2.size();z++){
					if(pr.isSatisfiable(t2.get(z).getIntersectExpression())){
						boolean flag=true;
						for(int ind=0;ind<t1.size();ind++){
							if(t1.get(ind).isegale(t2.get(z)))
								flag=false;
						}
						if(flag)
							t1.add(t2.get(z));
					}
						
				}
				t2.clear();
			}
		}
		result.addAll(t1);
	}
	
	public ArrayList<OWLClassExpression> getExplication(){
		 ArrayList<OWLClassExpression> explications=new ArrayList<OWLClassExpression> ();
		for(int i=0;i<result.size();i++){
			explications.add(result.get(i).getIntersectExpression());
		}
		return explications;
	}
	
	public OWLClassExpression getHypothese(){
		return resCoherence.getIntersectExpression();
	}
	
}



