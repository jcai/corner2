/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-6-11
 */

package corner.orm.tapestry.service.excel;

import corner.orm.tapestry.utils.ComponentResponseUtils;



/**
 * 实现生成Excel链接的ServiceLink
 * <p>
 * excel生成的数据为:当前查询的全部数据
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.8.5.1
 */
public abstract class ExcelServiceFullLink extends AbstractExcelServiceLink {

	/**
	 * @see corner.orm.tapestry.service.excel.AbstractExcelServiceLink#getGenerateType()
	 */
	@Override
	public String getGenerateType() {
		return ComponentResponseUtils.EXCEL_DATA_GENERATE_TYPE_FULL;
	}

}
