package corner.orm.tapestry.translator;

import org.apache.tapestry.valid.ValidatorException;

import junit.framework.TestCase;

public class QuerStringDateTranslatorTest extends TestCase {

	public void testParseTextIFormComponentValidationMessagesString() throws ValidatorException {
		QuerStringDateTranslator translator=new QuerStringDateTranslator();
		String clientStr="20050607~20080901";
		String result=(String) translator.parse(null,null,clientStr);
		assertEquals("20050607 ~ 20080901",result);
		
		String returnText=(String) translator.format(null,null,clientStr);
		assertEquals(clientStr,returnText);
		
	}

}
