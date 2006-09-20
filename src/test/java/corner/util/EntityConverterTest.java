package corner.util;

import junit.framework.TestCase;
import org.testng.annotations.Test;
@Test

public class EntityConverterTest extends TestCase {

	public void testConvertName() {
		assertEquals("TestTasdf",EntityConverter.convertName("test_tasdf", false));
		assertEquals("testTasdf",EntityConverter.convertName("test_tasdf", true));
	}

	public void testGetShortClassName() {
		assertEquals("Object",EntityConverter.getShortClassName(new Object()));
	}

	public void testGetClassNameAsCollectionProperty() {
		assertEquals("objects",EntityConverter.getClassNameAsCollectionProperty(new Object()));
	}

	public void testGetClassNameAsPropertyName() {
		assertEquals("object",EntityConverter.getClassNameAsPropertyName(new Object()));
	}

}
