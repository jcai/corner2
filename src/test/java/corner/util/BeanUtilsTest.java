package corner.util;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import corner.demo.model.one.A;

public class BeanUtilsTest extends TestCase {

	public void testSetProperty() {
		A a=new A();
		Map<String,String> map=new HashMap<String,String>();
		map.put("name","test");
		BeanUtils.setProperties(a,map);
		assertEquals("test",a.getName());
	}

	public void testGetProperty() {
		A a=new A();
		assertNull(BeanUtils.getProperty(a,"name"));
		a.setName("test");
		assertEquals("test",a.getName());
	}

	

}
