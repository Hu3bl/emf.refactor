package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

import org.junit.Test;

import util.EcoreBuilder;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

public class DetectionTests {

	@Test
	public void testWideHierarchy()
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
	}
}
