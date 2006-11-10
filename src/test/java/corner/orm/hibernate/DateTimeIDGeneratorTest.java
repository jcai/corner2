package corner.orm.hibernate;

import java.util.Vector;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DateTimeIDGeneratorTest extends Assert{
	
	@Test(invocationCount=100,threadPoolSize=10)
	public void testGenerator(){
		AbstractDateTimeIDGenerator generator=new AbstractDateTimeIDGenerator(){};
		String id=generator.getNowTimeFormatted();
		Vector<String> v=new Vector<String>();
		assertFalse(v.contains(id));
		v.add(id);
	}
}
