package org.eclipse.emf.refactor.modelsmell;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.refactor.smells.interfaces.IModelSmellFinder;


public final class MultipathHierarchy implements IModelSmellFinder {
		
	@Override
	public LinkedList<LinkedList<EObject>> findSmell(EObject root) {
		LinkedList<LinkedList<EObject>> results = new LinkedList<LinkedList<EObject>>();
		// begin custom code
		List<EClass> classes = getAllEClasses(root);
		for (EClass cl : classes) {
			if (cl.getESuperTypes().size() > 1) {
				for (int i = 0; i < cl.getESuperTypes().size(); i++) {
					EClass cl1 = cl.getESuperTypes().get(i);
//				}
//				for (EClass cl1 : cl.getESuperTypes()) {
					for (int j = (i+1); j < cl.getESuperTypes().size(); j++) {
						EClass cl2 = cl.getESuperTypes().get(j);
//					}
//					for (EClass cl2 : cl.getESuperTypes()) {
//						if (cl1 != cl2) {
							List<EClass> superclasses1 = getAllESuperclasses(cl1);
							List<EClass> superclasses2 = getAllESuperclasses(cl2);
							for (EClass superclass : superclasses1) {
								if (superclasses2.contains(superclass)) {
									LinkedList<EObject> result = new LinkedList<EObject>();
									result.add(cl);
									result.add(cl1);
									result.add(cl2);
									result.add(superclass);
									results.add(result);
								}
							}
//						}
					}
				}
			}
		}
		// end custom code
		return results;
	}

	private List<EClass> getAllESuperclasses(EClass cl) {
		List<EClass> superclasses = new ArrayList<EClass>();
		if (cl.getESuperTypes().isEmpty()) return superclasses;
		superclasses.addAll(cl.getESuperTypes());
		for (EClass superclass : cl.getESuperTypes()) {
			superclasses.addAll(getAllESuperclasses(superclass));
		}
		return superclasses;
	}

	private List<EClass> getAllEClasses(EObject root) {
		List<EClass> classes = new ArrayList<EClass>();
		TreeIterator<EObject> iter = root.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (eObject instanceof EClass) {
				EClass cl = (EClass) eObject;
				classes.add(cl);
			}
		}
		return classes;
	}
	
}