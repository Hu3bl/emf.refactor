package org.eclipse.emf.refactor.modelsmell;

import java.util.LinkedList;
import java.util.List;

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
		return iterateAllSuperTypesAndCheckForAlreadyKnownClass(currentClass, currentClass, visitedClasses);
		
		/* Not working because not all supertypes are available in this list
		for(EClass superType : currentClass.getEAllSuperTypes())
		{
			if(superType == currentClass)
			{
				return true;
			}
		}
		return false;
		*/
	}

	private boolean iterateAllSuperTypesAndCheckForAlreadyKnownClass(EClass lookingFor, EClass currentClass,
			LinkedList<EClass> visitedClasses) {
		for(EClass superType : currentClass.getESuperTypes())
		{
			if(visitedClasses.contains(superType))
			{
				continue;
			}
			visitedClasses.add(superType);
			
			if(superType == lookingFor)
			{
				return true;
			}
			
			return iterateAllSuperTypesAndCheckForAlreadyKnownClass(currentClass, superType, visitedClasses);
		}
		return false;
	}
	
}