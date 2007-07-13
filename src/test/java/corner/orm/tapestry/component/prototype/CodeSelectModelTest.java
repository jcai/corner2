/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 *	file : $Id: CodeSelectModelTest.java 6599 2007-06-14 08:09:34Z ghostbb $
 *	created at:2007-4-27
 */



package corner.orm.tapestry.component.prototype;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import corner.util.StringUtils;


/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class CodeSelectModelTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CodeSelectModelTest.class);

	@Test
	public void testCompareStr() {
		CodeSelectModel model = new CodeSelectModel();

		String[] pStr = { ABC_STR, INDEX_CODE_STR,
				CHN_STR, NUM_STR };
		String[] inStr = { "zg", "CHINA", "中国", "1" };

		for (int i = 0; i < pStr.length; i++) {
			for (int j = 0; j < inStr.length; j++) {
				if (logger.isDebugEnabled()) {
					logger
							.debug("test pattern: [" + pStr[i] + "],inStr:[" + inStr[j] + "]"); //$NON-NLS-1$
				}

				if (i == j) {
					assertTrue(StringUtils.comparedCharacter(inStr[j], pStr[i]));
				} else {
					assertFalse(StringUtils.comparedCharacter(inStr[j], pStr[i]));
				}
			}
		}

	}

	@Test
	public void testCompareStr2() {
		CodeSelectModel model = new CodeSelectModel();

		String[] pStr = { ABC_STR, INDEX_CODE_STR,
				CHN_STR, NUM_STR };
		String[] inStr = { "zg", "CHINA", "中国", "123" };

		for (int i = 0; i < pStr.length; i++) {
			for (int j = 0; j < inStr.length; j++) {
				if (logger.isDebugEnabled()) {
					logger
							.debug("test pattern: [" + pStr[i] + "],inStr:[" + inStr[j] + "]"); //$NON-NLS-1$
				}

				if (i == j) {
					assertTrue(StringUtils.comparedCharacter(inStr[j], pStr[i]));
				} else {
					assertFalse(StringUtils.comparedCharacter(inStr[j], pStr[i]));
				}
			}
		}

	}
	
	
//	 ------------------------------------------------------------------------------
	// 系统表部分 CodeModelAutocompleter 组件进行各种字段检索时使用的常量
	// ==============================================================================
	/**
	 * 用于Autocompleter的拼音检索
	 */
	public static final String ABC_STR = "[a-z]";

	/**
	 * 拼音字段名称
	 */
	public static final String ABC_Field = "abcCode";

	/**
	 * 用于Autocompleter的数字检索
	 */
	public static final String NUM_STR = "[0-9]";

	/**
	 * 数字字段名称
	 */
	public static final String NUM_Field = "numCode";

	/**
	 * 用于Autocompleter的英文检索
	 */
	public static final String ENG_STR = "[a-zA-Z]";

	/**
	 * 英文字段名称
	 */
	public static final String ENG_Field = "engName";

	/**
	 * 用于Autocompleter的中文检索
	 */
	public static final String CHN_STR = "[\u4e00-\u9fa5]";

	/**
	 * 中文字段名称
	 */
	public static final String CHN_Field = "chnName";

	/**
	 * 字典表中,各种字典实体的简写码
	 */
	public static final String INDEX_CODE_STR = "[A-Z]";

	public static final String INDEX_CODE_FIELD = "indexCode";
}
