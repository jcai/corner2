package corner.function;

import corner.test.BaseFunctionTestCase;

import org.testng.annotations.Test;
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
