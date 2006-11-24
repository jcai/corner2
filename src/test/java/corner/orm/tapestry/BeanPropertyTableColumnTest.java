package corner.orm.tapestry;

import org.testng.Assert;
import org.testng.annotations.Test;

import corner.demo.model.one.A;

public class BeanPropertyTableColumnTest extends Assert{
	@Test
	public void testInitBeanPropertyTableColumn(){
		BeanPropertyTableColumn column=new BeanPropertyTableColumn("name");
		A a=new A();
		a.setName("Jun");
		
		assertEquals("Jun",column.getColumnValue(a));
		
	}

}
