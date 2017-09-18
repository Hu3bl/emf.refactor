package org.eclipse.emf.refactor.modelsmell;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.refactor.smells.interfaces.IModelSmellFinder;


public final class UnutilizedAbstraction_UnusedEnumeration implements IModelSmellFinder {

	@Override
	public LinkedList<LinkedList<EObject>> findSmell(EObject root) {
		LinkedList<LinkedList<EObject>> results = new LinkedList<LinkedList<EObject>>();
		List<EEnum> enums = getAllEEnums(root);
		List<EClass> classes = getAllEClasses(root);
		for(EEnum currentEnum : enums)
		{
			if(!isUsedAsType(currentEnum, classes))
			{
				LinkedList<EObject> result = new LinkedList<EObject>();
				result.add(currentEnum);
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
	
	private List<EEnum> getAllEEnums(EObject root) {
		List<EEnum> enums = new ArrayList<EEnum>();
		TreeIterator<EObject> iter = root.eAllContents();
		while (iter.hasNext()) {
			EObject eObject = iter.next();
			if (eObject instanceof EEnum) {
				EEnum enumeration = (EEnum) eObject;
				enums.add(enumeration);
			}
		}
		return enums;
	}
	
	private boolean isUsedAsType(EEnum currentEnum, List<EClass> allClasses)
	{
		for(EClass clazz : allClasses)
		{
			for(EReference reference : clazz.getEAllReferences())
			{
				if(reference.getEType().equals(currentEnum))
				{
					return true;
				}				
			}
		}
		return false;
	}
}