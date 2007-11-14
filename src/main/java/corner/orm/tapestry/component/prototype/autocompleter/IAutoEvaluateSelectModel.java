/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file : $Id$
 * created at:2007-04-25
 */
package corner.orm.tapestry.component.prototype.autocompleter;


/**
 * 提供自动赋值时候的一个选择model.
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.3.7
 */
public interface IAutoEvaluateSelectModel extends ISelectModel{

	
	/**
	 * 解析给定的参数,提供了查询字段，以及label字段，更新字段。
	 * @param queryFieldName 查询字段的名称。
	 * @param labelFields 显示字段的定义，该字符串是以逗号分隔的字符串。
	 * @param updateFields 更新字符串，该串是一个json的字符串。
	 * @param returnTemplates 更新时使用的模板和字段的名值对组，后将用json整理
	 * 
	 */
	public void parseParameter(String queryFieldName, String labelFields, String updateFields, String returnTemplates);
	
}
