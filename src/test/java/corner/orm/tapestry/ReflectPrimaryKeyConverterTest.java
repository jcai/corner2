package corner.orm.tapestry;

import org.apache.tapestry.BaseComponentTestCase;
import org.testng.annotations.Test;

import corner.demo.model.one.A;

public class ReflectPrimaryKeyConverterTest extends BaseComponentTestCase{
	@Test
	public void test_getPrimaryKey(){
		A a=new A();
		a.setId("id1");
		a.setName("name");
		
		ReflectPrimaryKeyConverter<A> converter=new ReflectPrimaryKeyConverter<A>(A.class,"id");
		assertEquals("id1",converter.getPrimaryKey(a));
		A a2=(A) converter.getValue("id1");
		assertEquals("id1",a2.getId());
		
		
	}

}
