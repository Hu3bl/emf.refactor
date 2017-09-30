package org.eclipse.emf.refactor.modelsmell;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.refactor.smells.interfaces.IModelSmellFinder;

import thesis.detection.smells.util.DetectionHelper;

/**
 * This class checks if there is a cylce in the hierarchy
 * @author renehahn
 *
 */
public final class CyclicHierarchy implements IModelSmellFinder {

	@Override
	public LinkedList<LinkedList<EObject>> findSmell(EObject root) {
		LinkedList<LinkedList<EObject>> results = new LinkedList<LinkedList<EObject>>();
		List<EClass> classes = DetectionHelper.getAllEClasses(root);
		
		for(EClass currentClass : classes)
		{
			if(hasClassACyclicHierarchy(currentClass))
			{
				LinkedList<EObject> result = new LinkedList<EObject>();
				result.add(currentClass);
				results.add(result);
			}
		}
		
		return results;
	}
	
	private boolean hasClassACyclicHierarchy(EClass currentClass)
	{
		LinkedList<EClass> visitedClasses = new LinkedList<EClass>();
		
		for(EClass superType : getAllESuperTypes(currentClass, visitedClasses))
		{
			if(superType == currentClass)
			{
				return true;
			}
		}
		
		return false;
	}
	
	private LinkedList<EClass> getAllESuperTypes(EClass currentClass, LinkedList<EClass> visitedClasses)
	{
		LinkedList<EClass> superTypes = new LinkedList<EClass>();
		
		for(EClass superType : currentClass.getESuperTypes())
		{
			if(!superTypes.contains(superType))
			{
				superTypes.add(superType);
			}
		}
		visitedClasses.add(currentClass);
		
		for(EClass superType : currentClass.getESuperTypes())
		{
			if(!visitedClasses.contains(superType))
			{
				superTypes.addAll(getAllESuperTypes(superType, visitedClasses));
			}
		}
		
		return superTypes;
	}	
}