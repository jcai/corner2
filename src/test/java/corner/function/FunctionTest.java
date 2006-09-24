package corner.function;

import org.testng.annotations.Test;

import corner.test.BaseFunctionTestCase;
@Test
public class FunctionTest extends BaseFunctionTestCase{

	/**
	 * @see corner.test.BaseFunctionTestCase#getListenerPort()
	 */
	@Override
	protected int getListenerPort() {
		
		return 8888;
	}
	

}
