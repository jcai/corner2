package corner.orm.tapestry.worker;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.hivemind.ErrorLog;
import org.apache.hivemind.Location;
import org.apache.hivemind.Resource;
import org.apache.hivemind.service.MethodSignature;
import org.apache.hivemind.util.ClasspathResource;
import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.annotations.AnnotationUtils;
import org.apache.tapestry.enhance.EnhancementOperation;
import org.apache.tapestry.spec.ComponentSpecification;
import org.apache.tapestry.spec.IComponentSpecification;
import org.apache.tapestry.spec.IPropertySpecification;
import org.apache.tapestry.spec.PropertySpecification;
import org.easymock.EasyMock;
import org.hibernate.reflection.XProperty;
import org.hibernate.reflection.java.JavaXFactory;
import org.testng.annotations.Test;

public class MagicFieldWorkerTest extends BaseComponentTestCase {

	@Test
	public void test_get_properties() {
		MagicFieldWorker worker = new MagicFieldWorker();
		JavaXFactory reflectionManager = new JavaXFactory();

		List<XProperty> list = new ArrayList<XProperty>();
		worker.getElementsToProcess(reflectionManager
				.toXClass(AnnotatedModel.class), list);
		assertEquals(list.size(), 4);
	}

	@Test
	public void testCanEnhance() {
		MagicFieldWorker worker = new MagicFieldWorker();

		replay();

		Method m = findMethod(AnnotatedPage.class, "getMagicField");

		assertTrue(worker.canEnhance(m));
		m = findMethod(AnnotatedPage.class, "getEntity");

		assertFalse(worker.canEnhance(m));

		verify();
	}

	@Test
	public void test_getClassNameFromInitialValue() {
		MagicFieldWorker worker = new MagicFieldWorker();
		String className = "corner.orm.tapestry.AnnotationModel";
		String ognlExp = "new " + className + "()";
		assertEquals(worker.getClassNameFromInitialValue(ognlExp), className);

		ognlExp = "ognl:new " + className + " ()";
		assertEquals(worker.getClassNameFromInitialValue(ognlExp), className);

	}

	@Test
	public void testPerformance() {
		IComponentSpecification spec = new ComponentSpecification();
		IPropertySpecification pspec = new PropertySpecification();

		EnhancementOperation op = newOp();

		pspec.setName("entity");
		pspec
				.setInitialValue("new corner.orm.tapestry.worker.AnnotatedModel()");
		spec.addPropertySpecification(pspec);

		
		Method method = findMethod(AnnotatedPage.class, "getMagicField");
		Resource resource = newResource(AnnotatedPage.class);
		
		//FIXME 
		op
				.addMethod(
						EasyMock.anyInt(),
						EasyMock.isA(MethodSignature.class),
						EasyMock.isA(String.class),
						EasyMock.isA(Location.class));
		
		replay();
		MagicFieldWorker worker = new MagicFieldWorker();
		worker.peformEnhancement(op, spec, method, resource);
		verify();
		assertEquals(spec.getComponentIds().size(), 4);

	}

	@Test
	public void testwrongPerformance() {
		IComponentSpecification spec = new ComponentSpecification();
		IPropertySpecification pspec = new PropertySpecification();

		EnhancementOperation op = newOp();

		pspec.setName("entity");
		pspec.setInitialValue("new corner.orm.tapestry.worker.AnnotationModel");
		spec.addPropertySpecification(pspec);
		MagicFieldWorker worker = new MagicFieldWorker();
		Method method = findMethod(AnnotatedPage.class, "getMagicField");
		Resource resource = newResource(AnnotatedPage.class);

		replay();
		try {
			worker.peformEnhancement(op, spec, method, resource);
			fail("can't reacheable!");
		} catch (Exception e) {
			// don nothing
		}
		verify();
		assertEquals(spec.getComponentIds().size(), 0);

	}

	protected Method findMethod(Class clazz, String name) {
		for (Method m : clazz.getMethods()) {
			if (m.getName().equals(name))
				return m;
		}

		throw new IllegalArgumentException("No method " + name + " in " + clazz);
	}

	protected IComponentSpecification newSpec() {
		return newMock(IComponentSpecification.class);
	}

	protected EnhancementOperation newOp() {
		return newMock(EnhancementOperation.class);
	}

	protected Resource newResource(Class clazz) {
		return new ClasspathResource(getClassResolver(), clazz.getName()
				.replace('.', '/'));
	}

	protected ErrorLog newErrorLog() {
		return newMock(ErrorLog.class);
	}

	protected Location newMethodLocation(Class baseClass, Method m,
			Class annotationClass) {
		Resource classResource = newResource(baseClass);

		return AnnotationUtils.buildLocationForAnnotation(m, m
				.getAnnotation(annotationClass), classResource);
	}
}
