package org.eclipse.emf.refactor.modelsmell;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.refactor.smells.interfaces.IModelSmellFinder;

import thesis.detection.smells.util.DetectionHelper;


public final class MultipathHierarchy implements IModelSmellFinder {
		
	@Override
	public LinkedList<LinkedList<EObject>> findSmell(EObject root) {
		LinkedList<LinkedList<EObject>> results = new LinkedList<LinkedList<EObject>>();
		List<EClass> classes = DetectionHelper.getAllEClasses(root);
		for (EClass cl : classes) {
			if (cl.getESuperTypes().size() > 1) {
				for (int i = 0; i < cl.getESuperTypes().size(); i++) {
					EClass cl1 = cl.getESuperTypes().get(i);
					for (int j = (i+1); j < cl.getESuperTypes().size(); j++) {
						EClass cl2 = cl.getESuperTypes().get(j);
							List<EClass> superclasses1 = DetectionHelper.getAllESuperclasses(cl1);
							List<EClass> superclasses2 = DetectionHelper.getAllESuperclasses(cl2);
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
					}
				}
			}
		}
		return results;
	}	
}