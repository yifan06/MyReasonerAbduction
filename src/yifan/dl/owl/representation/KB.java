/*
 * Copyright 2010 Wu, Kejia (w_kejia{at}cs.concordia.ca)
 * This file is part of Deslog.
 * Deslog is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * Deslog is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with
 * Deslog. If not, see <http://www.gnu.org/licenses/>.
 */
package yifan.dl.owl.representation;

import yifan.dl.owl.elements.Concept;
//import yifan.dl.facility.IDeepCopy;
import yifan.dl.owl.elements.ConceptDefIndex;

//import yifan.dl.owl.representation.tableau.alc.General;
//import yifan.dl.owl.representation.tableau.alc.IGeneral;
//import yifan.dl.owl.representation.tableau.alc.IUnfoldable;
//import yifan.dl.owl.representation.tableau.alc.Unfoldable;

//import deslog.facility.exception.ExceptionDeslog;
//implements IDeepCopy<KB>
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * the description logic KB
 */

public class KB  {

	private List<Concept> concepts;
 	private Set<Concept> conceptIndex;
	private ConceptDefIndex<Concept, Concept> conceptDefinitionIndex;
//	private IGeneral general;
//	private IUnfoldable unfoldable;
//	private List<Concept> axioms;
	
//	private HashMap<Concept,Concept> equivAxioms;
//	private HashMap<Concept,Concept> subAxioms;
	private HashMap<OWLClassExpression,Concept> equivAxioms;
	private HashMap<OWLClassExpression,Concept> subAxioms;

	public KB() {
		concepts=new ArrayList<Concept>();
//		equivAxioms=new HashMap<Concept,Concept>();
//		subAxioms=new HashMap<Concept,Concept>();
		
		equivAxioms=new HashMap<OWLClassExpression,Concept>();
		subAxioms=new HashMap<OWLClassExpression,Concept>();
	}

	public void print(){
		System.out.println("============Concepts=============");
		for(int ind=0;ind<concepts.size();ind++)
			System.out.println(concepts.get(ind).expression);
		System.out.println("============Equivalent Axioms=============");
		Set<OWLClassExpression> concepts=equivAxioms.keySet();
		for(OWLClassExpression c:concepts){
			//System.out.println(c.getExpression()+" = "+ equivAxioms.get(c).getExpression());
			System.out.println(c.toString()+" = "+ equivAxioms.get(c).getExpression());
		}
		System.out.println("============SubClass Axioms=============");
		Set<OWLClassExpression> conceptsS=subAxioms.keySet();
		for(OWLClassExpression c:conceptsS){
			//System.out.println(c.getExpression()+" = "+ subAxioms.get(c).getExpression());
			System.out.println(c.toString()+" = "+ subAxioms.get(c).getExpression());
		}
		

	}
	public void setConcepts(List<Concept> ltConcepts){
		concepts=ltConcepts;
	}
	public void setEquivAxiom(HashMap<OWLClassExpression,Concept> axioms){
		equivAxioms=axioms;
	}
	
	public HashMap<OWLClassExpression,Concept>  getEquivAxiom(){
		return equivAxioms;
	}
	
	public HashMap<OWLClassExpression,Concept>  getSubAxiom(){
		return subAxioms;
	}
	
	public void setSubAxiom(HashMap<OWLClassExpression,Concept> axioms){
		subAxioms=axioms;
	}
	
	public ConceptDefIndex<Concept, Concept> getConceptDefinitionIndex () {
		return conceptDefinitionIndex;
	}

	public void setConceptDefinitionIndex (
	                ConceptDefIndex<Concept, Concept> conceptDefinitionIndex) {
		this.conceptDefinitionIndex = conceptDefinitionIndex;
		this.conceptIndex = conceptDefinitionIndex.keySet();
	}
	
	
	public OWLClassExpression lazyUnfoldingComp(OWLClassExpression key){
		if(equivAxioms.containsKey(key)){
			return equivAxioms.get(key).getExpression();
		}
		else if(subAxioms.containsKey(key)){
			return subAxioms.get(key).getExpression();
		}
		else
			return key;
	}
	
	public OWLClassExpression lazyUnfolding(OWLClassExpression key){
		if(equivAxioms.containsKey(key)){
			return equivAxioms.get(key).getExpression();
		}
		else
			return key;
	}

	public int getNbSubAxiom() {
		// TODO Auto-generated method stub
		return subAxioms.size();
	}
	
	public int getNbEquivAxiom(){
		return equivAxioms.size();
	}

//	public IGeneral getGeneral () {
//		return general;
//	}
//
//	public void setGeneral (IGeneral general) {
//		this.general = general;
//	}
//
//	public IUnfoldable getUnfoldable () {
//		return unfoldable;
//	}
//
//	public void setUnfoldable (IUnfoldable unfoldable) {
//		this.unfoldable = unfoldable;
//	}
}

//	public List<Concept> getAxioms () {
//		return axioms;
//	}
//
//	public void setAxioms (List<Concept> axioms) {
//		this.axioms = axioms;
//	}
//
//	public Set<Concept> getConceptIndex () {
//		return conceptIndex;
//	}
//
//	public void setConceptIndex (Set<Concept> conceptIndex) {
//		this.conceptIndex = conceptIndex;
//	}
//
//	private void sanitize () {
//		for (Concept conc : conceptIndex) {
//			if (conceptIndex == null) {
//				// TODO
//			}
//		}
//	}

//	@Override
//	public KB deepCopy (Object... objects) {
//		KB rv = null;
//		try {
//			rv = new KB();
//			rv.setConceptDefinitionIndex(this.conceptDefinitionIndex.deepCopy());
//			rv.setGeneral( ((General) this.general).deepCopy());
//			rv.setUnfoldable( ((Unfoldable) this.unfoldable).deepCopy());
//			if (this.axioms != null) {
//				if (rv.axioms == null) {
//					rv.axioms = this.axioms.getClass().newInstance();
//				}
//				for (Concept concept : this.axioms) {
//					//rv.axioms.add(concept.deepCopy());
//				}
//			}
//		} catch (Exception e) {
//			//ExceptionDeslog.exit(e);
//		}
//		return rv;
//	}
//
//	@Override
//    public String toString () {
//	    return "KB [conceptIndex=" + conceptIndex + ", conceptDefinitionIndex="
//	                    + conceptDefinitionIndex + ", general=" + general
//	                    + ", unfoldable=" + unfoldable + ", axioms=" + axioms
//	                    + "]";
//    }
//}
