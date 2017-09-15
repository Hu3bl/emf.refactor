package thesis.detection.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import thesis.detection.util.EcoreBuilder;
import thesis.detection.util.SmellFinder;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.refactor.smells.interfaces.IModelSmellFinder;
import org.eclipse.emf.refactor.smells.runtime.core.Result;
import org.eclipse.emf.refactor.modelsmell.*;

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
		
		EcoreBuilder.savePackageToFile(testPackage, "wideHierarchy.ecore");	
				
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
		
		EcoreBuilder.savePackageToFile(testPackage, "deepHierarchy.ecore");	
		
		Result result = SmellFinder.findMetricSmellWithLimit(new DeepHierarchy(), 3, testPackage);
		assertNotNull(result);
		assertEquals(1,	result.getModelelements().size()); 	
		
		result = SmellFinder.findMetricSmellWithLimit(new DeepHierarchy(), 4, testPackage);
		assertNotNull(result);
		assertEquals(0,	result.getModelelements().size()); 	
	}
	
	@Test
	public void validateMissingAbstraction_DataClumpsAttributesDetection() 
	{
		EcoreBuilder.initStandalone();
		
		EPackage testPackage = EcoreBuilder.createPackage("testPackage", "testPackage", "http://testPackage");
		
		EClass firstClass = EcoreBuilder.createEClass("FirstClass");
		testPackage.getEClassifiers().add(firstClass);
		EcoreBuilder.addAttribute(firstClass, "firstAttribute", EcorePackage.Literals.EDOUBLE, false, 0, 1);
		EcoreBuilder.addAttribute(firstClass, "secondAttribute", EcorePackage.Literals.EINT, false, 0, 1);
		
		EClass secondClass = EcoreBuilder.createEClass("SecondClass");
		testPackage.getEClassifiers().add(secondClass);
		EcoreBuilder.addAttribute(secondClass, "firstAttribute", EcorePackage.Literals.EDOUBLE, false, 0, 1);
		EcoreBuilder.addAttribute(secondClass, "secondAttribute", EcorePackage.Literals.EINT, false, 0, 1);
		
		EcoreBuilder.savePackageToFile(testPackage, "MissingAbstractionDataClumps.ecore");
		
		Result result = SmellFinder.findMetricSmellWithLimit(new MissingAbstraction_DataClumpsAttributes(), 2, testPackage);
		assertNotNull(result);
		assertEquals(2,	result.getModelelements().size());
		
		result = SmellFinder.findMetricSmellWithLimit(new MissingAbstraction_DataClumpsAttributes(), 3, testPackage);
		assertNotNull(result);
		assertEquals(0,	result.getModelelements().size());
	}
	
	@Test
	public void validateMissingAbstraction_PrimitiveObessionPrimitiveTypesDetection() 
	{
		EcoreBuilder.initStandalone();
		
		EPackage testPackage = EcoreBuilder.createPackage("testPackage", "testPackage", "http://testPackage");
		
		EClass firstClass = EcoreBuilder.createEClass("FirstClass");
		testPackage.getEClassifiers().add(firstClass);
		EcoreBuilder.addAttribute(firstClass, "firstAttribute", EcorePackage.Literals.EINT, false, 0, 1);
		EcoreBuilder.addAttribute(firstClass, "secondAttribute", EcorePackage.Literals.EINT, false, 0, 1);
		
		EClass secondClass = EcoreBuilder.createEClass("SecondClass");
		testPackage.getEClassifiers().add(secondClass);
		EcoreBuilder.addAttribute(secondClass, "firstAttribute", EcorePackage.Literals.EINT, false, 0, 1);
		EcoreBuilder.addAttribute(secondClass, "secondAttribute", EcorePackage.Literals.EINT, false, 0, 1);
		
		EcoreBuilder.savePackageToFile(testPackage, "MissingAbstractionPrimitiveObsessionPrimitiveTypes.ecore");
		
		Result result = SmellFinder.findMetricSmellWithLimit(new MissingAbstraction_PrimitiveObessionPrimitiveTypes(), 2, testPackage);
		assertNotNull(result);
		assertEquals(2,	result.getModelelements().size());
		
		result = SmellFinder.findMetricSmellWithLimit(new MissingAbstraction_PrimitiveObessionPrimitiveTypes(), 3, testPackage);
		assertNotNull(result);
		assertEquals(0,	result.getModelelements().size());
	}
	
	@Test
	public void validateMultipathHierarchyDetection() 
	{
		EcoreBuilder.initStandalone();
		
		EPackage testPackage = EcoreBuilder.createPackage("testPackage", "testPackage", "http://testPackage");
		
		EClass supersuperClass = EcoreBuilder.createEClass("SuperSuperClass");
		testPackage.getEClassifiers().add(supersuperClass);
		
		EClass firstSuperClass = EcoreBuilder.createEClass("FirstSuperClass");
		testPackage.getEClassifiers().add(firstSuperClass);
		EcoreBuilder.addSuperType(firstSuperClass, testPackage, "SuperSuperClass");
		
		EClass secondSuperClass = EcoreBuilder.createEClass("SecondSuperClass");
		testPackage.getEClassifiers().add(secondSuperClass);
		EcoreBuilder.addSuperType(secondSuperClass, testPackage, "SuperSuperClass");
		
		EClass derivedClass = EcoreBuilder.createEClass("DerivedClass");
		testPackage.getEClassifiers().add(derivedClass);
		EcoreBuilder.addSuperType(derivedClass, testPackage, "FirstSuperClass");
		EcoreBuilder.addSuperType(derivedClass, testPackage, "SecondSuperClass");
		
		EcoreBuilder.savePackageToFile(testPackage, "MultipathHierarchy.ecore");
				
		Result result = SmellFinder.findSmell(new MultipathHierarchy(), testPackage);
		assertNotNull(result);
		assertEquals(1, result.getModelelements().size());
		assertEquals(4, result.getModelelements().get(0).size());
		
		//assertNotNull(result);
		//assertEquals(2,	result.getModelelements().size());
		
		//result = SmellFinder.findMetricSmellWithLimit(new MissingAbstraction_PrimitiveObessionPrimitiveTypes(), 3, testPackage);
		//assertNotNull(result);
		//assertEquals(0,	result.getModelelements().size());
	}
}
