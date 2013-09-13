package yifan.dl.owl.elements;

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

import yifan.dl.facility.IDeepCopy;
//import deslog.facility.exception.ExceptionDeslog;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class designates an index of all concepts definitions in an ontology.
 * This class is designed with singleton pattern and has a hash table data
 * structure: concept as key, and concept representing the definition as value.
 */
public class ConceptDefIndex<K extends Concept, V extends Concept> implements
                IDeepCopy<ConceptDefIndex<K, V>> {

	private Map<K, V> concepts;
	{
		concepts = new HashMap<K, V>();
	}

	public ConceptDefIndex() {
	}

	/**
	 * The `getInstance()' method of the singleton pattern implementation.
	 * 
	 * @return the single ConceptDefIndex instance
	 */
	/*
	 * public static ConceptDefIndex getInstance () {
	 * if (concepts == null) {
	 * concepts = new ConceptDefIndex();
	 * concept_number = 0;
	 * }
	 * return concepts;
	 * }
	 */
	/**
	 * This method adds a key-value pair, designating a concept, to the index.
	 * 
	 * @param concept_id
	 *            id of the concept
	 * @param concept_reference
	 *            reference (i.e. pointer) to the reference
	 */
	public void index (K concept, V def) {
		try {
			if (!concepts.containsKey(concept)) {
				concepts.put(concept, def);
			}
		} catch (Exception e) {
			//ExceptionDeslog.exit(e);
		}
	}

	/**
	 * This method removes an indexed concept.
	 * 
	 * @param concept
	 *            the concept id of the concept to be removed
	 */
	public void delete (K concept) {
		try {
			concepts.remove(concept);
		} catch (Exception e) {
			//ExceptionDeslog.exit(e);
		}
	}
	
	public Set<K> keySet() {
		return this.concepts.keySet();
	}
	
	public V get(K key) {
		return this.concepts.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConceptDefIndex<K, V> deepCopy (Object... objects) {
		ConceptDefIndex<K, V> rv = null;
		try {
			rv = new ConceptDefIndex<K, V>();
			for (K k : this.concepts.keySet()) {
//				rv.concepts.put((K) k.deepCopy(), (V) this.concepts.get(k)
//				                .deepCopy());
			}
		} catch (Exception e) {
			//ExceptionDeslog.exit(e);
		}
		return rv;
	}

	@Override
    public String toString () {
	    return "ConceptDefIndex [concepts=" + concepts + "]";
    }
}

