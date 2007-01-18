package corner.util;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.testng.annotations.Test;

import corner.demo.model.one.A;
@Test

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
		assertEquals("test",BeanUtils.getProperty(a,"Name"));
	}

	/**
	 * 当给定的实体中不包含要查找的属性的时候，返回null
	 *
	 */
	public void testGetNoProperty(){
		A a = new A();
		assertNull(BeanUtils.getProperty(a, "test"));
	}

}
