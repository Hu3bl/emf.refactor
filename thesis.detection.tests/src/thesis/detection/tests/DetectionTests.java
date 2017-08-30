package thesis.detection.tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import thesis.detection.util.EcoreBuilder;
import thesis.detection.util.SmellFinder;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.refactor.smells.runtime.core.ModelSmellFinder;
import org.eclipse.emf.refactor.smells.runtime.core.Result;
import org.eclipse.emf.refactor.modelsmells.DeepHierarchy;
import org.eclipse.emf.refactor.modelsmells.WideHierarchy;
import org.eclipse.emf.refactor.smells.core.*;

public class DetectionTests {

	@Test
	public void validateWideHierarchyDetection_DifferentLimits()
	{
		EcoreBuilder.initStandalone();
		
		EPackage testPackage = EcoreBuilder.createPackage("testPackage", "testPackage", "http://testPackage");
		
		EClass superType = EcoreBuilder.createEClass("SuperType");
		testPackage.getEClassifiers().add(superType);
		
		EClass subType1 = EcoreBuilder.createEClass("SubType1");
		testPackage.getEClassifiers().add(subType1);
		EcoreBuilder.addSuperType(subType1, testPackage, superType.getName());
		
		EClass subType2 = EcoreBuilder.createEClass("SubType2");
		testPackage.getEClassifiers().add(subType2);
		EcoreBuilder.addSuperType(subType2, testPackage, superType.getName());
		
		EClass subType3 = EcoreBuilder.createEClass("SubType3");
		testPackage.getEClassifiers().add(subType3);
		EcoreBuilder.addSuperType(subType3, testPackage, superType.getName());
		
		EClass subType4 = EcoreBuilder.createEClass("SubType4");
		testPackage.getEClassifiers().add(subType4);
		EcoreBuilder.addSuperType(subType4, testPackage, superType.getName());
		
		EClass subType5 = EcoreBuilder.createEClass("SubType5");
		testPackage.getEClassifiers().add(subType5);
		EcoreBuilder.addSuperType(subType5, testPackage, superType.getName());
		
		//EcoreBuilder.savePackageToFile(testPackage, "wideHierarchy.ecore");	
				
		Result result = SmellFinder.findMetricSmellWithLimit(new WideHierarchy(), 5, testPackage);
		assertNotNull(result);
		assertEquals(1,	result.getModelelements().size()); 	
		
		result = SmellFinder.findMetricSmellWithLimit(new WideHierarchy(), 6, testPackage);
		assertNotNull(result);
		assertEquals(0,	result.getModelelements().size()); 	
	}
	
	@Test
	public void validateDeepHierarchyDetection_DifferentLimits()
	{
		EcoreBuilder.initStandalone();
		
		EPackage testPackage = EcoreBuilder.createPackage("testPackage", "testPackage", "http://testPackage");
		
		EClass superType = EcoreBuilder.createEClass("SuperType");
		testPackage.getEClassifiers().add(superType);
		
		EClass subType = EcoreBuilder.createEClass("SubType");
		testPackage.getEClassifiers().add(subType);
		EcoreBuilder.addSuperType(subType, testPackage, superType.getName());
		
		EClass subSubType = EcoreBuilder.createEClass("SubSubType");
		testPackage.getEClassifiers().add(subSubType);
		EcoreBuilder.addSuperType(subSubType, testPackage, subType.getName());
		
		EClass subSubSubType = EcoreBuilder.createEClass("SubSubType");
		testPackage.getEClassifiers().add(subSubSubType);
		EcoreBuilder.addSuperType(subSubSubType, testPackage, subSubType.getName());
		
		Result result = SmellFinder.findMetricSmellWithLimit(new DeepHierarchy(), 3, testPackage);
		assertNotNull(result);
		assertEquals(1,	result.getModelelements().size()); 	
		
		result = SmellFinder.findMetricSmellWithLimit(new DeepHierarchy(), 4, testPackage);
		assertNotNull(result);
		assertEquals(0,	result.getModelelements().size()); 	
	}
}
