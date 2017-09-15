package org.eclipse.emf.refactor.metrics;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.refactor.metrics.interfaces.IMetricCalculator;

public final class NAPTC implements IMetricCalculator {
		
	private List<EObject> context; 
		
	@Override
	public void setContext(List<EObject> context) {
		this.context=context;
	}	
		
	@Override
	public double calculate() {	
		org.eclipse.emf.ecore.EClass in = (org.eclipse.emf.ecore.EClass) context.get(0);
		double ret = 0.0;
		
		for(EAttribute attribute : in.getEAttributes())
		{
			if(attribute.getEAttributeType() != null)
			{
				if(isPrimitiveType(attribute.getEAttributeType()))
				{
					ret++;
				}
			}
		}
		
		return ret;
	}
	
	private boolean isPrimitiveType(EDataType dataType)
	{
		//if(dataType.isInstance(EcorePackage.Literals.EBOOLEAN)) { return true; }
		//if(dataType.isInstance(EcorePackage.Literals.EINT)) { return true; }
		//if(dataType.isInstance(EcorePackage.Literals.ECHAR)) { return true; }
		//if(dataType.isInstance(EcorePackage.Literals.EFLOAT)) { return true; }
		//if(dataType.isInstance(EcorePackage.Literals.EDOUBLE)) { return true; }
		//if(dataType.isInstance(EcorePackage.Literals.EBYTE)) { return true; }
		//if(dataType.isInstance(EcorePackage.Literals.ESHORT)) { return true; }
		//if(dataType.isInstance(EcorePackage.Literals.ELONG)) { return true; }
		//if(dataType.isInstance(EcorePackage.Literals.ESTRING)) { return true; }	
		//if(dataType.getName().equals("EInt")) return true;
		
		if(dataType.getInstanceClass().isPrimitive()) { return true; }
		
		return false;
	}
}