package corner.study.format;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;

public class DecimalFormatTest {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(DecimalFormatTest.class);

	@Test
	public void test_format_pattern() throws ParseException{
		String pattern = "#.0000";
		DecimalFormat format=new DecimalFormat(pattern, new DecimalFormatSymbols(Locale.getDefault()));
		Number obj=(Number) format.parseObject("2221.23444");

		String destStr=format.format(obj);
		logger.debug("object :"+obj);
		logger.debug("String :"+destStr);
		logger.debug("places :"+format.getMaximumFractionDigits());
		
	}

	
}
