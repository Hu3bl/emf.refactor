package org.eclipse.emf.refactor.modelsmell;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.refactor.smells.interfaces.IModelSmellFinder;

import thesis.detection.smells.util.DetectionHelper;


public final class SpecializationAggregation implements IModelSmellFinder {

	@Override
	public LinkedList<LinkedList<EObject>> findSmell(EObject root) {
		LinkedList<LinkedList<EObject>> results = new LinkedList<LinkedList<EObject>>();
		List<EClass> classes = DetectionHelper.getAllEClasses(root);
		for(EClass currentClass : classes)
		{
			//if(hasClassRedundantContainerRelation(currentClass, classes))
			//{
			//	LinkedList<EObject> result = new LinkedList<EObject>();
			//	result.add(currentClass);
			//	result.addAll(foundClassesWithOppositeReference);
			//	foundClassesWithOppositeReference.clear();
			//	results.add(result);
			//}
		}		
		
		return results;
	}	
	
	//private boolean 
}