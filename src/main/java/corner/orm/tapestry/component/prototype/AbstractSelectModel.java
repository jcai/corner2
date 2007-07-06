/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site: http://www.bjmaxinfo.com
 * file : $Id: AbstractSelectModel.java 6057 2007-05-29 02:26:25Z jcai $
 * created at:2007-04-27
 */
package corner.orm.tapestry.component.prototype;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * 抽象的选择model，提供一些公用的方法
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.3.7
 */
public class AbstractSelectModel {

	protected String escapeHtml(Object v){
		return v==null?"":StringEscapeUtils.escapeHtml(v.toString());
	}
}
