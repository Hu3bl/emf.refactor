package org.eclipse.emf.refactor.modelsmell;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.refactor.smells.interfaces.IModelSmellFinder;

import thesis.detection.smells.util.DetectionHelper;


public final class RedundantContainerRelation implements IModelSmellFinder {

	private LinkedList<EObject> foundClassesWithOppositeReference = new LinkedList<EObject>();
	
	@Override
	public LinkedList<LinkedList<EObject>> findSmell(EObject root) {
		LinkedList<LinkedList<EObject>> results = new LinkedList<LinkedList<EObject>>();
		List<EClass> classes = DetectionHelper.getAllEClasses(root);
		for(EClass currentClass : classes)
		{
			if(hasClassRedundantContainerRelation(currentClass, classes))
			{
				LinkedList<EObject> result = new LinkedList<EObject>();
				result.add(currentClass);
				result.addAll(foundClassesWithOppositeReference);
				foundClassesWithOppositeReference.clear();
				results.add(result);
			}
		}		
		
		return results;
	}
	
	private boolean hasClassRedundantContainerRelation(EClass currentClass, List<EClass> allClasses)
	{
		boolean result = false;
		for(EClass iterClass : allClasses)
		{
			if(iterClass.eContainer().equals(currentClass))
			{
				if(hasOppositeReferences(currentClass, iterClass))
				{
					foundClassesWithOppositeReference.add(iterClass);
					result = true;
				}
			}
		}
		return result;
	}
	
	private boolean hasOppositeReferences(EClass firstClass, EClass secondClass)
	{
		EReference firstReference = null;
		EReference secondReference = null;
		
		for(EReference reference : firstClass.getEAllReferences())
		{
			if(reference.getEType().equals(secondClass))
			{
				firstReference = reference;
			}				
		}
		
		for(EReference reference : secondClass.getEAllReferences())
		{
			if(reference.getEType().equals(firstClass))
			{
				secondReference = reference;
			}				
		}
		
		if(firstReference != null && secondReference != null)
		{
			if(firstReference.getEOpposite().equals(secondReference) 
			&& secondReference.getEOpposite().equals(firstReference))
			{
				return true;
			}
		}
		return false;
	}	
}