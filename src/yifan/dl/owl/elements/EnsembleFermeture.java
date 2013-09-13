package yifan.dl.owl.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings("rawtypes")
public class EnsembleFermeture implements Set{

	private int ind;
	private ArrayList<ElementFermeture> h;
	
	public EnsembleFermeture(){
		ind=0;
		h=new ArrayList<ElementFermeture>();
	}
	
	public EnsembleFermeture(EnsembleFermeture e){
		ind=0;
		h=new ArrayList<ElementFermeture>();
		for(int i=0;i<e.getElementSize();i++){
			h.add(e.getElementFermeture(i));
		}
	}
	
	public void setInd(int i){
		ind=i;
	}
	
	public void addElementFermeture(ElementFermeture e){
		h.add(e);
	}
	
	public int getInd(){
		return ind;
	}
	
	public ElementFermeture getElementFermeture(int i){
		return h.get(i);
	}
	
	public ArrayList<ElementFermeture> getAllElement() {
		// TODO Auto-generated method stub
		return h;
	}
	
	public boolean hasUniqueElement(){
		if(h.size()==1)
			return true;
		else
			return false;
	}
	
	public int getElementSize(){
		return h.size();
	}

	public void setElement(ArrayList<ElementFermeture> allSuccessors) {
		// TODO Auto-generated method stub
		h=allSuccessors;
		
	}

	public void printALL() {
		// TODO Auto-generated method stub
		System.out.println("=================hypo=================");
		System.out.println(ind);
		for(int i=0;i<h.size();i++){
			printElement(i);
		}
		System.out.println("======================================");
		System.out.println(" ");
		
		
	}

	public void printElement(int i) {
		// TODO Auto-generated method stub
		h.get(i).print();
		
	}
	
	public void removeRedundancy(){
//		System.out.println("sizesize : "+h.size());
		for(int i=0;i<h.size()-1;i++){
			for(int j=i+1;j<h.size();j++){
				if(h.get(i).isEqual(h.get(j))){
					h.remove(j);			
					j=j-1;
				}
			}
		}
	}

	public void getMinimumSet() {
		// TODO Auto-generated method stub
		for(int i=0;i<h.size();i++){
			OWLClassExpression exp=h.get(i).exportOwlExpression();
//			if()
		}
		
	}
	
	public ArrayList<OWLClassExpression> getAllExpression(){
		ArrayList<OWLClassExpression> ls=new ArrayList<OWLClassExpression>();
		for(int i=0;i<h.size();i++){
			ls.add(h.get(i).exportOwlExpression());
		}
		return ls;
	}
	
	public OWLClassExpression getIntersectExpression(){
		OWLOntologyManager manager=OWLManager.createOWLOntologyManager();
		OWLDataFactory factory= manager.getOWLDataFactory();
		Set<OWLClassExpression> s=new HashSet();
		for(int i=0;i<h.size();i++){
			s.add(h.get(i).exportOwlExpression());
		}
		OWLClassExpression exp=factory.getOWLObjectIntersectionOf(s);
		return exp;
	}

	@Override
	public boolean add(Object e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean contains(ElementFermeture e){
		for(int i=0;i<h.size();i++){
			if(h.get(i).isEqual(e))
				return true;
		}
		return false;
		
	}
	
	public boolean isegale(EnsembleFermeture e){
//		e.printALL();
//		printALL();
		if(containsAll(e)){
			if(e.containsAll(this)){
//				System.out.println("true");
				return true;
			}
			
		}
		return false;
	}

	public boolean containsAll(EnsembleFermeture e) {
		for(int i=0;i<e.getElementSize();i++){
			if(!contains(e.getElementFermeture(i)))
				return false;
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}


}
