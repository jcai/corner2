package corner.util;

import org.testng.Assert;
import org.testng.annotations.Test;


public class CornerUtilsTest extends Assert{

	@Test
	public void teset_enquoteString(){
		assertEquals(CornerUtils.enquoteString("asdf'asdf'asdf\"asdf"),"asdf'asdf'asdf\\\"asdf");
		assertEquals(CornerUtils.enquoteString("asdf'a'\nsdf'asdf\"asdf"),"asdf'a'\\nsdf'asdf\\\"asdf");
	}
}
