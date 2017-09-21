package org.eclipse.emf.refactor.modelsmell;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.refactor.smells.interfaces.IModelSmellFinder;


public final class UnutilizedAbstraction_UnusedClasses implements IModelSmellFinder {

	@Override
	public LinkedList<LinkedList<EObject>> findSmell(EObject root) {
		LinkedList<LinkedList<EObject>> results = new LinkedList<LinkedList<EObject>>();
		List<EClass> classes = getAllEClasses(root);
		for(EClass currentClass : classes)
		{
			if(!isClassUsed(currentClass, classes))
			{
				LinkedList<EObject> result = new LinkedList<EObject>();
				result.add(currentClass);
				results.add(result);
			}
		}		
		
		return results;
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
	
	private boolean isClassUsed(EClass currentClass, List<EClass> allClasses)
	{
		boolean result = false;
		result |= hasClassSuperTypesOrRealizesInterface(currentClass);
		result |= hasSubClasses(currentClass, allClasses);
		result |= isUsedAsType(currentClass, allClasses);		
		return result;
	}
	
	private boolean hasClassSuperTypesOrRealizesInterface(EClass currentClass)
	{
		List<EClass> superTypes = currentClass.getEAllSuperTypes();
		if(superTypes.isEmpty()) return false;
		return true;			
	}
	
	private boolean hasSubClasses(EClass currentClass, List<EClass> allClasses)
	{
		for(EClass clazz : allClasses)
		{
			for(EClass superType : clazz.getESuperTypes())
			{
				if(currentClass.equals(superType))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isUsedAsType(EClass currentClass, List<EClass> allClasses)
	{
		for(EClass clazz : allClasses)
		{
			for(EReference reference : clazz.getEAllReferences())
			{
				if(reference.getEType().equals(currentClass))
				{
					return true;
				}				
			}
		}
		return false;
	}
}